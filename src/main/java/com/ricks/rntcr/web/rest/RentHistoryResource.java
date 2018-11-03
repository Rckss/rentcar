package com.ricks.rntcr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ricks.rntcr.domain.User;
import com.ricks.rntcr.domain.enumeration.Status;
import com.ricks.rntcr.service.CarService;
import com.ricks.rntcr.service.RentHistoryService;
import com.ricks.rntcr.service.UserService;
import com.ricks.rntcr.service.dto.CarDTO;
import com.ricks.rntcr.service.dto.RentHistoryDTO;
import com.ricks.rntcr.web.rest.errors.BadRequestAlertException;
import com.ricks.rntcr.web.rest.util.HeaderUtil;
import com.ricks.rntcr.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.ricks.rntcr.security.SecurityUtils.getCurrentUserLogin;

/**
 * REST controller for managing RentHistory.
 */
@RestController
@RequestMapping("/api")
public class RentHistoryResource {

    private final Logger log = LoggerFactory.getLogger(RentHistoryResource.class);

    private static final String ENTITY_NAME = "rentHistory";

    private final RentHistoryService rentHistoryService;

    private final UserService usrServ;

    private final CarService carService;

    public RentHistoryResource(RentHistoryService rentHistoryService, UserService usrServ, CarService carService) {
        this.rentHistoryService = rentHistoryService;
        this.usrServ = usrServ;
        this.carService = carService;
    }

    /**
     * POST  /rent-histories : Create a new rentHistory.
     *
     * @param rentHistoryDTO the rentHistoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rentHistoryDTO, or with status 400 (Bad Request) if the rentHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rent-histories")
    @Timed
    public ResponseEntity<RentHistoryDTO> createRentHistory(@RequestBody RentHistoryDTO rentHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save RentHistory : {}", rentHistoryDTO);
        if (rentHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new rentHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RentHistoryDTO result = rentHistoryService.save(rentHistoryDTO);
        return ResponseEntity.created(new URI("/api/rent-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rent-histories : Updates an existing rentHistory.
     *
     * @param rentHistoryDTO the rentHistoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rentHistoryDTO,
     * or with status 400 (Bad Request) if the rentHistoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the rentHistoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rent-histories")
    @Timed
    public ResponseEntity<RentHistoryDTO> updateRentHistory(@RequestBody RentHistoryDTO rentHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update RentHistory : {}", rentHistoryDTO);
        if (rentHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RentHistoryDTO result = rentHistoryService.save(rentHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rentHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rent-histories : get all the rentHistories.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of rentHistories in body
     */
    @GetMapping("/rent-histories")
    @Timed
    public ResponseEntity<List<RentHistoryDTO>> getAllRentHistories(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of RentHistories");
        Page<RentHistoryDTO> page;
        if (eagerload) {
            page = rentHistoryService.findAllWithEagerRelationships(pageable);
        } else {
            page = rentHistoryService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/rent-histories?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rent-histories/:id : get the "id" rentHistory.
     *
     * @param id the id of the rentHistoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rentHistoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rent-histories/{id}")
    @Timed
    public ResponseEntity<RentHistoryDTO> getRentHistory(@PathVariable Long id) {
        log.debug("REST request to get RentHistory : {}", id);
        Optional<RentHistoryDTO> rentHistoryDTO = rentHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rentHistoryDTO);
    }

    /**
     * DELETE  /rent-histories/:id : delete the "id" rentHistory.
     *
     * @param id the id of the rentHistoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rent-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteRentHistory(@PathVariable Long id) {
        log.debug("REST request to delete RentHistory : {}", id);
        rentHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * POST  /rent-histories : Create a new rentHistory.
     *
     * @param rentHistoryDTO the rentHistoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rentHistoryDTO, or with status 400 (Bad Request) if the rentHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rent-historiesbyuser")
    @Timed
    public ResponseEntity<RentHistoryDTO> createRentHistoryByUser(@RequestBody RentHistoryDTO rentHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save RentHistory : {}", rentHistoryDTO);
        if (rentHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new rentHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        rentHistoryDTO.setClientId(user.getId()); // set logged user to the new renting
        rentHistoryDTO.setStatus(Status.RUNNING); // all new renting starts running
        RentHistoryDTO result = rentHistoryService.save(rentHistoryDTO);
        return ResponseEntity.created(new URI("/api/rent-historiesbyuser/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rent-histories : Updates an existing rentHistory.
     *
     * @param rentHistoryDTO the rentHistoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rentHistoryDTO,
     * or with status 400 (Bad Request) if the rentHistoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the rentHistoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rent-historiesbyuser")
    @Timed
    public ResponseEntity<RentHistoryDTO> updateRentHistoryByUser(@RequestBody RentHistoryDTO rentHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update RentHistory : {}", rentHistoryDTO);
        if (rentHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        // contactDTO.setClientId(user.getId());
        if (rentHistoryDTO != null && rentHistoryDTO.getClientId() != null &&
            !rentHistoryDTO.getClientId().equals(
                user.getId()
            )) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        RentHistoryDTO result = rentHistoryService.save(rentHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rentHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rent-histories : get all the rentHistories.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of rentHistories in body
     */
    @GetMapping("/rent-historiesbyuser")
    @Timed
    public ResponseEntity<List<RentHistoryDTO>> getAllRentHistoriesByUser(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of RentHistories");
        Page<RentHistoryDTO> page;
        // modifiyed by me
        // changed to call on new queries
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        Long id = user.getId();
        if (eagerload) {
            page = rentHistoryService.findAllWithEagerRelationships(pageable, id);
        } else {
            page = rentHistoryService.findAll(pageable, id);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/rent-historiesbyuser?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rent-histories/:id : get the "id" rentHistory.
     *
     * @param id the id of the rentHistoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rentHistoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rent-historiesbyuser/{id}")
    @Timed
    public ResponseEntity<RentHistoryDTO> getRentHistoryByUser(@PathVariable Long id) {
        log.debug("REST request to get RentHistory : {}", id);
        Optional<RentHistoryDTO> rentHistoryDTO = rentHistoryService.findOne(id);
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        if (rentHistoryDTO.isPresent() && rentHistoryDTO.get().getClientId() != null &&
            !rentHistoryDTO.get().getClientId().equals(
                user.getId()
            )) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return ResponseUtil.wrapOrNotFound(rentHistoryDTO);
    }

    /**
     * DELETE  /rent-historiesbyuser/:id : delete the "id" rentHistory.
     *
     * @param id the id of the rentHistoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rent-historiesbyuser/{id}")
    @Timed
    public ResponseEntity<Void> deleteRentHistoryByUser(@PathVariable Long id) {
        log.debug("REST request to delete RentHistory : {}", id);
        Optional<RentHistoryDTO> rentHistoryDTO = rentHistoryService.findOne(id);
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        if (rentHistoryDTO.isPresent() && rentHistoryDTO.get().getClientId() != null &&
            !rentHistoryDTO.get().getClientId().equals(
                user.getId()
            )) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        rentHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    /**
     * POST  /rent-histories by logged user and by car id
     *
     */
    @PostMapping("/rent-historiesbyusercar/{carId}")
    @Timed
    public ResponseEntity<RentHistoryDTO> createRentHistoryByUserCar(@RequestBody RentHistoryDTO rentHistoryDTO, @PathVariable Long carId) throws URISyntaxException {
        log.debug("REST request to save RentHistory : {}", rentHistoryDTO);
        if (rentHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new rentHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        CarDTO car = carService.findOne(carId).get();
        Set carset = new HashSet<CarDTO>();
        carset.add(car);
        rentHistoryDTO.setClientId(user.getId());
        rentHistoryDTO.setCars( carset );
        RentHistoryDTO result = rentHistoryService.save(rentHistoryDTO);
        return ResponseEntity.created(new URI("/api/rent-historiesbyusercar/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/rent-historiesbyusercar")
    @Timed
    public ResponseEntity<RentHistoryDTO> updateRentHistoryByUserCar(@RequestBody RentHistoryDTO rentHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update RentHistory : {}", rentHistoryDTO);
        if (rentHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        // contactDTO.setClientId(user.getId());
        if (rentHistoryDTO != null && rentHistoryDTO.getClientId() != null &&
            !rentHistoryDTO.getClientId().equals(
                user.getId()
            )) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        RentHistoryDTO result = rentHistoryService.save(rentHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rentHistoryDTO.getId().toString()))
            .body(result);
    }
}
