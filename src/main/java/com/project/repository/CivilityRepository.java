package com.project.repository;

import com.project.domain.entities.Civility;
import com.project.domain.enums.CivilityEnumType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CivilityRepository extends JpaRepository<Civility, Long> {


    @Query("""
              select
                civ
              from
                Civility civ
              where
                civ.libelle ilike :civilityEnumType
            """)
    Civility findByLibelle(@Param("civilityEnumType") CivilityEnumType civilityEnumType);
}
