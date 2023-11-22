package com.project.carnet.repositories;

import com.project.carnet.entities.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailJpaRepo extends JpaRepository<Email, Long> {
}
