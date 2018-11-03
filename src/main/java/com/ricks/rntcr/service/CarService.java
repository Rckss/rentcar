package com.ricks.rntcr.service;

import com.ricks.rntcr.service.dto.CarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Car.
 */
public interface CarService {

    /**
     * Save a car.
     *
     * @param carDTO the entity to save
     * @return the persisted entity
     */
    CarDTO save(CarDTO carDTO);

    /**
     * Get all the cars.
     *
     * @return the list of entities
     */
    List<CarDTO> findAll();

    /**
     * Get all the Car with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<CarDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" car.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CarDTO> findOne(Long id);

    /**
     * Delete the "id" car.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    Optional<CarDTO> findOneCompleted(Long id);
}
