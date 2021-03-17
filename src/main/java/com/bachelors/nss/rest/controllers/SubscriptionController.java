package com.bachelors.nss.rest.controllers;

import com.bachelors.nss.rest.helpers.SubscriptionHandler;
import com.bachelors.nss.rest.models.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nss/v2/")
public class SubscriptionController {

    @PostMapping("/subscribe")
    ResponseEntity<Object> subscribe(@RequestBody UserRequest request) {
        return SubscriptionHandler.subscribe(request);
    }

    @DeleteMapping("/unsubscribe")
    ResponseEntity<Object> unsubscribe(@RequestParam String topic) {
        return SubscriptionHandler.unsubscribe(topic);
    }

}
