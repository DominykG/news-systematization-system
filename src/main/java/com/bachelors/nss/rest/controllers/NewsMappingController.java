package com.bachelors.nss.rest.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/nss/v2")
public final class NewsMappingController {

    @GetMapping("/health")
    private void getHealth() {}

}
