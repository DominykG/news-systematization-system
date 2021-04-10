package com.bachelors.nss.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.bachelors.nss.business.helpers.DatabaseHandler.getClientInfo;
import static com.bachelors.nss.business.helpers.DatabaseHandler.getSourceList;

@RestController
@RequestMapping("/nss/v2/")
public class DatabaseController {

    @GetMapping("/getInfo")
    ResponseEntity<Object> getUserInfo(@RequestParam String name) {
        return getClientInfo(name);
    }

    @GetMapping("/getSources")
    ResponseEntity<Object> getSources() {
        return getSourceList();
    }

}
