package com.project.repository;

import com.project.domain.entities.Country;
import com.project.domain.enums.CountryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {


    //    @Query("""
//              select
//                cou
//              from
//                Country cou
//              where
//                cou.libelle = cast(:countryEnum as String)
//            """)
//    Country findByLibelle(@Param("countryEnum") CountryEnum countryEnum);
    @Query("""
              select 
                cou 
              from 
                Country cou 
              where 
                cou.libelle ilike :countryEnum
            """)
    Country findByLibelle(@Param("countryEnum") CountryEnum countryEnum);
}
