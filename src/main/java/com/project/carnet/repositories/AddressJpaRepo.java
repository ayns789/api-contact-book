package com.project.carnet.repositories;

import com.project.carnet.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressJpaRepo extends JpaRepository<Address, Long> {
}
