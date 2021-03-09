package com.bachelors.nss.rest.helpers;

import com.bachelors.nss.rest.models.UserRequest;
import org.springframework.http.ResponseEntity;

import static com.bachelors.nss.rest.validation.UserRequestValidator.validateUserRequest;

public class SubscriptionHandler {

    public static ResponseEntity<Object> Subscribe(UserRequest request) {

        var errors = validateUserRequest(request);

        //TODO: Generate new kafka topic name
        //TODO: Generate new kafka topic creation script
        //TODO: Execute new kafka topic creation script
        //TODO: Delete new kafka topic creation script
        //TODO: Generate search query from user request
        //TODO: Generate UserResponse
        //TODO: Add user to DB
        //TODO: Return UserResponse

        if(!errors.isEmpty())
            return ResponseEntity.badRequest().body(errors);

        return ResponseEntity.ok(request);
    }


}
