package com.bachelors.nss.rest.controllers;

import com.bachelors.nss.rest.helpers.SubscriptionHandler;
import com.bachelors.nss.rest.models.UserRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/nss/v2/")
public class SubscriptionController {

    @PostMapping("/subscribe")
    private ResponseEntity<Object> subscribe(@RequestBody UserRequest request) {
        return SubscriptionHandler.subscribe(request);
    }

    @DeleteMapping("/unsubscribe")
    private ResponseEntity<Object> unsubscribe(@RequestParam String topic) {
        return SubscriptionHandler.unsubscribe(topic);
    }

}
