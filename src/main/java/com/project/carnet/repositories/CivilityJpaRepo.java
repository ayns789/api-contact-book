package com.project.carnet.repositories;

import com.project.carnet.entities.Civility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CivilityJpaRepo extends JpaRepository<Civility, Long> {
}
