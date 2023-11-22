package com.project.carnet.repositories;

import com.project.carnet.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactJpaRepo extends JpaRepository<Contact, Long> {
}
