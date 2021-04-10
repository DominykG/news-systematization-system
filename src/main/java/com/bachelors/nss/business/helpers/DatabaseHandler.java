package com.bachelors.nss.business.helpers;

import com.bachelors.nss.database.repositories.ClientRepository;
import com.bachelors.nss.database.repositories.SourceRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class DatabaseHandler {

    private static ClientRepository clientRepository;
    private static SourceRepository sourceRepository;

    @Autowired
    public DatabaseHandler(ClientRepository clientRepository, SourceRepository sourceRepository) {
        DatabaseHandler.clientRepository = clientRepository;
        DatabaseHandler.sourceRepository = sourceRepository;
    }

    public static ResponseEntity<Object> getClientInfo(String name) {
        var response = ResponseEntity.ok(clientRepository.findByName(name));
        if (response.getBody().isEmpty()) {
            return ResponseEntity.badRequest().body("No client with name " + name);
        }
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Object> getSourceList() {
        return ResponseEntity.ok(sourceRepository.findAll());
    }

}
