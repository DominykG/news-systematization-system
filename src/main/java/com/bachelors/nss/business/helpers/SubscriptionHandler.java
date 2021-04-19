package com.bachelors.nss.business.helpers;

import com.bachelors.nss.business.models.UserRequest;
import com.bachelors.nss.business.models.UserResponse;
import com.bachelors.nss.business.validation.UserRequestValidator;
import com.bachelors.nss.database.models.Client;
import com.bachelors.nss.database.models.RssFeed;
import com.bachelors.nss.database.models.Source;
import com.bachelors.nss.database.repositories.ClientRepository;
import com.bachelors.nss.database.repositories.RssFeedRepository;
import com.bachelors.nss.database.repositories.SourceRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Log4j2
@Component
public class SubscriptionHandler {

    private static ClientRepository clientRepository;
    private static SourceRepository sourceRepository;
    private static RssFeedRepository rssFeedRepository;

    @Autowired
    public SubscriptionHandler(ClientRepository clientRepository,
                               SourceRepository sourceRepository,
                               RssFeedRepository rssFeedRepository) {
        SubscriptionHandler.clientRepository = clientRepository;
        SubscriptionHandler.sourceRepository = sourceRepository;
        SubscriptionHandler.rssFeedRepository = rssFeedRepository;
    }

    public static ResponseEntity<Object> subscribe(UserRequest request) {
        var errors = UserRequestValidator.validateUserRequest(request,
                                                                                sourceRepository,
                                                                                rssFeedRepository);

        if(!errors.isEmpty())
            return ResponseEntity.badRequest().body(errors);

        UserResponse response = generateUserResponse(request);

        addUserToDb(request.getName(),
                response.getSearchQuery(),
                response.getSources(),
                response.getRssFeeds(),
                response.getKafkaTopicName());

        return ResponseEntity.ok(response);
    }

    private static UserResponse generateUserResponse(UserRequest request) {
        return UserResponse.builder()
                .name(request.getName())
                .kafkaTopicName(generateKafkaTopicName(request.getName()))
                .searchQuery(generateSearchQuery(request))
                .sources(getSources(request.getSources()))
                .rssFeeds(getRssFeeds(request.getRssFeeds()))
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

    static void addUserToDb(String name,
                            String searchQuery,
                            Set<Source> sources,
                            Set<RssFeed> rssFeeds,
                            String kafkaTopic) {
        Client client = Client.builder()
                .assignedKafkaTopic(kafkaTopic)
                .name(name)
                .query(searchQuery)
                .sources(sources)
                .rssFeeds(rssFeeds)
                .build();

        for (Source source : sources) {
            source.getClients().add(client);
        }

        for (RssFeed rssFeed : rssFeeds) {
            rssFeed.getClients().add(client);
        }

        clientRepository.save(client);
    }

    static Set<Source> getSources(Set<String> sources) {
        Set<Source> sourceSet = new HashSet<>();
        if (sources != null && !sources.isEmpty()) {
            sources.forEach( (source) -> sourceSet.add(sourceRepository.findByName(source)));
        }
        return sourceSet;
    }

    static Set<RssFeed> getRssFeeds(Set<String> rssFeeds) {
        Set<RssFeed> rssFeedSet = new HashSet<>();
        if (rssFeeds != null && !rssFeeds.isEmpty()) {
            rssFeeds.forEach( (rssFeed) -> rssFeedSet.add(rssFeedRepository.findById(rssFeed).isPresent() ? rssFeedRepository.findById(rssFeed).get() : null));
        }
        return rssFeedSet;
    }

    public static ResponseEntity<Object> unsubscribe(String topic) {
        try {
            clientRepository.deleteById(topic);
            return ResponseEntity.ok("You have been successfully unsubscribed");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.badRequest().body("No such topic exists");
        }
    }
}
