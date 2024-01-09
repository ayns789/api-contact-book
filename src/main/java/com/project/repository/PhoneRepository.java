package com.project.repository;

import com.project.domain.entities.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    @Modifying
    void deleteByContact_ContactId(Long contactId);
}
