package com.project.controllers;


import com.project.dto.ContactCreateDTO;
import com.project.service.ContactService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
public class ContactController {

    private final ContactService service;

    protected ContactController(ContactService service) {
        this.service = service;
    }

    @CrossOrigin
    @PostMapping("/add")
    protected void create(@RequestBody ContactCreateDTO dto) {
        service.createContact(dto);
    }
}
