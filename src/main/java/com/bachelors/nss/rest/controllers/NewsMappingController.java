package com.bachelors.nss.rest.controllers;

import com.bachelors.nss.database.models.NewsTopic;
import com.bachelors.nss.database.repositories.MessageRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/testing")
public class NewsMappingController {

    @Autowired
    MessageRepository repository;

    @GetMapping("/health")
    void getHealth() {}

    @GetMapping("/getEmployees/all")
    List<NewsTopic> getEmployees() {
        return repository.findAll();
    }
}
