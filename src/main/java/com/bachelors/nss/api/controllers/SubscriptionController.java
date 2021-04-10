package com.bachelors.nss.api.controllers;

import com.bachelors.nss.business.helpers.SubscriptionHandler;
import com.bachelors.nss.business.models.UserRequest;
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
