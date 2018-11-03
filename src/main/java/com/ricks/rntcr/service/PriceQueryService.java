package com.ricks.rntcr.service;

import com.ricks.rntcr.domain.Price;
import com.ricks.rntcr.domain.Price_;
import com.ricks.rntcr.repository.PriceRepository;
import com.ricks.rntcr.service.dto.PriceCriteria;
import com.ricks.rntcr.service.dto.PriceDTO;
import com.ricks.rntcr.service.mapper.PriceMapper;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for Price entities in the database.
 * The main input is a {@link PriceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PriceDTO} or a {@link Page} of {@link PriceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PriceQueryService extends QueryService<Price> {

    private final Logger log = LoggerFactory.getLogger(PriceQueryService.class);

    private final PriceRepository priceRepository;

    private final PriceMapper priceMapper;

    public PriceQueryService(PriceRepository priceRepository, PriceMapper priceMapper) {
        this.priceRepository = priceRepository;
        this.priceMapper = priceMapper;
    }

    /**
     * Return a {@link List} of {@link PriceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PriceDTO> findByCriteria(PriceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Price> specification = createSpecification(criteria);
        return priceMapper.toDto(priceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PriceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PriceDTO> findByCriteria(PriceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Price> specification = createSpecification(criteria);
        return priceRepository.findAll(specification, page)
            .map(priceMapper::toDto);
    }

    /**
     * Function to convert PriceCriteria to a {@link Specification}
     */
    private Specification<Price> createSpecification(PriceCriteria criteria) {
        Specification<Price> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Price_.id));
            }
            if (criteria.getAdjDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAdjDate(), Price_.adjDate));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Price_.name));
            }
            if (criteria.getSerial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerial(), Price_.serial));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Price_.price));
            }
            if (criteria.getFactorOne() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFactorOne(), Price_.factorOne));
            }
            if (criteria.getFactorTwo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFactorTwo(), Price_.factorTwo));
            }
            if (criteria.getFactorThree() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFactorThree(), Price_.factorThree));
            }
            if (criteria.getTax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTax(), Price_.tax));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotal(), Price_.total));
            }
            if (criteria.getClassification() != null) {
                specification = specification.and(buildSpecification(criteria.getClassification(), Price_.classification));
            }
        }
        return specification;
    }
}
