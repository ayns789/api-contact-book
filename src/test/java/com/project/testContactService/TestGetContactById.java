package com.project.testContactService;

import com.project.exceptions.IdNotFoundException;
import com.project.repository.ContactRepository;
import com.project.service.implementation.ContactServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TestGetContactById {

    @Autowired
    @Mock
    private ContactRepository contactRepository;

    @Autowired
    @InjectMocks
    ContactServiceImpl contactService;

    @Test
    public void testGetContact_IdNotFound() {

        // data test
        Long contactId = 0L;

        // configure ContactRepository response as empty
        when(contactRepository.findById(contactId)).thenReturn(Optional.empty());

        // verify method launch IdNotFoundException
        assertThrows(IdNotFoundException.class, () -> contactService.getContact(contactId));

        // reset the mock to clear the interactions
        Mockito.reset(contactRepository);
        Mockito.verifyNoMoreInteractions(contactRepository);
    }
}
