package com.project.carnet.repositories;

import com.project.carnet.entities.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneJpaRepo extends JpaRepository<Phone, Long> {
}
