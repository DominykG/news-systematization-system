package com.bachelors.nss.rest.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.bachelors.nss.rest.helpers.DatabaseHandler.getClientInfo;

@Log4j2
@RestController
@RequestMapping("/nss/v2/")
public class DatabaseController {

    @GetMapping("/getInfo")
    private ResponseEntity<Object> getUserInfo(@RequestParam String name) {
        return getClientInfo(name);
    }

}
