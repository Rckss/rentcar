package com.ricks.rntcr.service.impl;

import com.ricks.rntcr.domain.RentHistory;
import com.ricks.rntcr.repository.RentHistoryRepository;
import com.ricks.rntcr.service.RentHistoryService;
import com.ricks.rntcr.service.dto.RentHistoryDTO;
import com.ricks.rntcr.service.mapper.RentHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing RentHistory.
 */
@Service
@Transactional
public class RentHistoryServiceImpl implements RentHistoryService {

    private final Logger log = LoggerFactory.getLogger(RentHistoryServiceImpl.class);

    private final RentHistoryRepository rentHistoryRepository;

    private final RentHistoryMapper rentHistoryMapper;

    public RentHistoryServiceImpl(RentHistoryRepository rentHistoryRepository, RentHistoryMapper rentHistoryMapper) {
        this.rentHistoryRepository = rentHistoryRepository;
        this.rentHistoryMapper = rentHistoryMapper;
    }

    /**
     * Save a rentHistory.
     *
     * @param rentHistoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RentHistoryDTO save(RentHistoryDTO rentHistoryDTO) {
        log.debug("Request to save RentHistory : {}", rentHistoryDTO);
        RentHistory rentHistory = rentHistoryMapper.toEntity(rentHistoryDTO);
        rentHistory = rentHistoryRepository.save(rentHistory);
        return rentHistoryMapper.toDto(rentHistory);
    }

    /**
     * Get all the rentHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RentHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RentHistories");
        return rentHistoryRepository.findAll(pageable)
            .map(rentHistoryMapper::toDto);
    }

    /**
     * Get all the RentHistory with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<RentHistoryDTO> findAllWithEagerRelationships(Pageable pageable) {
        return rentHistoryRepository.findAllWithEagerRelationships(pageable).map(rentHistoryMapper::toDto);
    }


    /**
     * Get one rentHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RentHistoryDTO> findOne(Long id) {
        log.debug("Request to get RentHistory : {}", id);
        return rentHistoryRepository.findOneWithEagerRelationships(id)
            .map(rentHistoryMapper::toDto);
    }

    /**
     * Delete the rentHistory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RentHistory : {}", id);
        rentHistoryRepository.deleteById(id);
    }

    @Override
    public Page<RentHistoryDTO> findAllWithEagerRelationships(Pageable pageable, Long id) {
        return rentHistoryRepository.findAllWithEagerRelationships(pageable, id).map(rentHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RentHistoryDTO> findAll(Pageable pageable, Long id) {
        log.debug("Request to get all RentHistories By User");
        return rentHistoryRepository.findAll(pageable, id)
            .map(rentHistoryMapper::toDto);    }
}
