package com.project.controllers;

import com.project.dto.ContactDTO;
import com.project.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ContactDTO create(@Valid @RequestBody ContactDTO input) {
      return service.createContact(input);
    }
}
