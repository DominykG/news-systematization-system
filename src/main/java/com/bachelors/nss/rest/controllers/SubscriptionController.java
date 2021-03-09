package com.bachelors.nss.rest.controllers;

import com.bachelors.nss.rest.models.UserRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bachelors.nss.rest.helpers.SubscriptionHandler.Subscribe;

@Log4j2
@RestController
@RequestMapping("/nss/v2/")
public class SubscriptionController {

    @PostMapping("/test")
    private ResponseEntity<Object> test(@RequestBody UserRequest request) {
        return Subscribe(request);
    }

}
