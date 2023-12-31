package com.project.repository;

import com.project.domain.entities.Civility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CivilityRepository extends JpaRepository<Civility, Long> {
}
