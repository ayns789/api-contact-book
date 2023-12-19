package com.project.repository;

import com.project.domain.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("""
          select
            con
          from
            Contact con
          where
            con.lastName is null or con.lastName ilike %:lastName%
        """)
    Optional<List<Contact>> getContact(@Param("lastName") String lastName);

}
