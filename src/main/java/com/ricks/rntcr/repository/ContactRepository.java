package com.ricks.rntcr.repository;

import com.ricks.rntcr.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Contact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("select distinct contact from Contact contact where contact.client.id = :id")
    List<Contact> findAll(@Param("id") Long id);

}
