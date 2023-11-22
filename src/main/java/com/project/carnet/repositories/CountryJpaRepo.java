package com.project.carnet.repositories;

import com.project.carnet.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryJpaRepo extends JpaRepository<Country, Long> {
}
