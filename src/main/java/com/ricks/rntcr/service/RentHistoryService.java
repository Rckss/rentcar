package com.ricks.rntcr.service;

import com.ricks.rntcr.service.dto.RentHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing RentHistory.
 */
public interface RentHistoryService {

    /**
     * Save a rentHistory.
     *
     * @param rentHistoryDTO the entity to save
     * @return the persisted entity
     */
    RentHistoryDTO save(RentHistoryDTO rentHistoryDTO);

    /**
     * Get all the rentHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RentHistoryDTO> findAll(Pageable pageable);

    /**
     * Get all the RentHistory with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<RentHistoryDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" rentHistory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RentHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" rentHistory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    Page<RentHistoryDTO> findAllWithEagerRelationships(Pageable pageable, Long id);

    Page<RentHistoryDTO> findAll(Pageable pageable, Long id);
}
