package com.bachelors.nss.rest.helpers;

import com.bachelors.nss.db.repositories.ClientRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class DatabaseHandler {
    static ClientRepository clientRepository;

    @Autowired
    public DatabaseHandler(ClientRepository clientRepository) {
        DatabaseHandler.clientRepository = clientRepository;
    }

    public static ResponseEntity<Object> getClientInfo(String name) {
        var response = ResponseEntity.ok(clientRepository.findByName(name));
        if (response.getBody().isEmpty()) {
            return ResponseEntity.badRequest().body("No client with name " + name);
        }
        return ResponseEntity.ok(response);

    }
}
