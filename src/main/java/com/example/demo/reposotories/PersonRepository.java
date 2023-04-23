package com.example.demo.reposotories;

import com.example.demo.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByName(String name);

    boolean existsByName(String name);

    @Query(value = "select * from PERSONS group by ID having max(AGE) limit 1", nativeQuery = true)
    Person findOldestPerson();

    @Modifying
    @Query(value = "update PERSONS set VIEWS = VIEWS + 1 where NAME = :name", nativeQuery = true)
    void increaseViews(@Param("name") String name);
}
