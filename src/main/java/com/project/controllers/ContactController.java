package com.project.controllers;

import com.project.dto.ContactDTO;
import com.project.service.ContactService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ContactDTO create(@RequestBody ContactDTO input) {
        return service.createContact(input);
    }
}
