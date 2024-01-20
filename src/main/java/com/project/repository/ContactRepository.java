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
    Optional<List<Contact>> getContactByLastname(@Param("lastName") String lastName);

    @Query("""
          select
            con
          from
            Contact con
          where
            con.firstName is null or con.firstName ilike %:firstName%
        """)
    Optional<List<Contact>> getContactByFirstname(@Param("firstName") String firstName);

    @Query("""
          select
            con
          from
            Contact con
          join
            con.phones ph
          where
            ph.libelle ilike %:phoneNumber%
        """)
    Optional<List<Contact>> getContactByPhone(@Param("phoneNumber") String phoneNumber);

    //    @Query(value = "select EXISTS(select  c from  contact c  where c.last_name = :lastName and c.first_name = :firstName) ", nativeQuery = true)
    Boolean existsByLastNameAndAndFirstName(@Param("lastName") String lastName, @Param("firstName") String firstName);
}
