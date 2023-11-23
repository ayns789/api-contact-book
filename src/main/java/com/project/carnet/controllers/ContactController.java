package com.project.carnet.controllers;


import com.project.carnet.dto.ContactCreateDTO;
import com.project.carnet.service.ContactService;
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
