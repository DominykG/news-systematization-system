package com.bachelors.nss.rest.helpers;

import com.bachelors.nss.db.models.Client;
import com.bachelors.nss.db.models.Source;
import com.bachelors.nss.db.repositories.ClientRepository;
import com.bachelors.nss.db.repositories.SourceRepository;
import com.bachelors.nss.rest.models.UserRequest;
import com.bachelors.nss.rest.models.UserResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.bachelors.nss.rest.validation.UserRequestValidator.validateUserRequest;

@Log4j2
@Component
public class SubscriptionHandler {

    //TODO: Unsubscribe by user topic name

    private static ClientRepository clientRepository;
    private static SourceRepository sourceRepository;

    @Autowired
    public SubscriptionHandler(ClientRepository clientRepository, SourceRepository sourceRepository) {
        SubscriptionHandler.clientRepository = clientRepository;
        SubscriptionHandler.sourceRepository = sourceRepository;
    }

    public static ResponseEntity<Object> Subscribe(UserRequest request) {
        var errors = validateUserRequest(request, sourceRepository);

        if(!errors.isEmpty())
            return ResponseEntity.badRequest().body(errors);

        UserResponse response = generateUserResponse(request);

        String filename = generateKafkaTopicScript(response.getKafkaTopicName());
        executeKafkaTopicScript(filename);
        deleteKafkaTopicScript(filename);

        addUserToDb(request.getName(),
                request.getFrom(),
                response.getSearchQuery(),
                response.getSources(),
                response.getKafkaTopicName());

        return ResponseEntity.ok(response);
    }

    private static UserResponse generateUserResponse(UserRequest request) {
        return UserResponse.builder()
                .name(request.getName())
                .kafkaTopicName(generateKafkaTopicName(request.getName()))
                .searchQuery(generateSearchQuery(request))
                .sources(getSources(request.getSources()))
                .build();
    }

    static String generateKafkaTopicName(String name) {
        return name + "-" + UUID.randomUUID();
    }

    static String generateSearchQuery(UserRequest request) {
        StringBuilder query = new StringBuilder();
        for (String searchTerm : request.getSearchTerms()) {
            query.append(searchTerm).append(" AND ");
        }
        if (request.getExcludedTerms() != null && !request.getExcludedTerms().isEmpty())
            for (String excludedTerm : request.getExcludedTerms()) {
                query.append(" NOT ").append(excludedTerm);
            }

        if (query.toString().endsWith(" AND "))
            query.delete(query.lastIndexOf(" AND "), query.length());

        return query.toString().replace("AND  NOT", "NOT");
    }

    static String generateKafkaTopicScript(String topic) {
        try {
            String filename = topic + ".bat";
            File kafkaTopicScript = new File(filename);
            if (kafkaTopicScript.createNewFile()) {
                log.info("File created: " + kafkaTopicScript.getName());
            } else {
                log.error("File already exists.");
            }
            FileWriter writer = new FileWriter(filename);
            writer.write("D:\\_Bachelors\\kafka_2.13-2.6.0\\bin\\windows\\kafka-topics.bat --create --topic "
                    + topic + " --bootstrap-server localhost:9092");
            writer.close();

            return filename;
        } catch (IOException e) {
            log.error(e);
        }
        return null;
    }

    static void executeKafkaTopicScript(String filename) {
        try {
            Runtime.getRuntime().exec("cmd /c " + filename);
            log.info("File executed: " + filename);
        } catch (IOException e) {
            log.error(e);
        }
    }

    static void deleteKafkaTopicScript(String filename) {
        File kafkaTopicScript = new File(filename);
        if (kafkaTopicScript.delete()) {
            log.info("File deleted: " + kafkaTopicScript.getName());
        } else {
            log.error("Failed to delete the file.");
        }
    }

    static void addUserToDb(String name,
                            LocalDateTime from,
                            String searchQuery,
                            Set<Source> sources,
                            String kafkaTopic) {
        Client client = Client.builder()
                .dateFrom(from)
                .name(name)
                .query(searchQuery)
                .sources(sources)
                .assignedKafkaTopic(kafkaTopic)
                .build();

        for (Source source : sources) {
            source.getClients().add(client);
        }

        clientRepository.save(client);
    }

    static Set<Source> getSources(Set<String> sources) {
        Set<Source> s = new HashSet<>();
        for (String source : sources)
            s.add(sourceRepository.findByName(source));
        return s;
    }

}
