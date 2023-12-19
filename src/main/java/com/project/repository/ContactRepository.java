package com.project.repository;

import com.project.domain.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query(value = "SELECT distinct co.contact_id FROM contact co\n" +
            " JOIN phone ph on ph.contact_id = co.contact_id\n" +
            " WHERE co.last_name = :lastName\n" +
            " OR co.first_name = :firstName\n" +
            " OR ph.libelle = :libelle", nativeQuery = true)
    List<Long> findByVariousInfo(@Param("lastName") String lastName, @Param("firstName") String firstName, @Param("libelle") String libelle);

}
