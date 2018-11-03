package com.ricks.rntcr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ricks.rntcr.domain.User;
import com.ricks.rntcr.service.ContactService;
import com.ricks.rntcr.service.UserService;
import com.ricks.rntcr.service.dto.ContactDTO;
import com.ricks.rntcr.web.rest.errors.BadRequestAlertException;
import com.ricks.rntcr.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static com.ricks.rntcr.security.SecurityUtils.getCurrentUserLogin;

/**
 * REST controller for managing Contact.
 */
@RestController
@RequestMapping("/api")
public class ContactResource {

    private final Logger log = LoggerFactory.getLogger(ContactResource.class);

    private static final String ENTITY_NAME = "contact";

    private final ContactService contactService;

    private final UserService usrServ;

    public ContactResource(ContactService contactService, UserService usrServ) {
        this.contactService = contactService;
        this.usrServ = usrServ;
    }

    /**
     * POST  /contacts : Create a new contact.
     *
     * @param contactDTO the contactDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contactDTO, or with status 400 (Bad Request) if the contact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contacts")
    @Timed
    public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contactDTO) throws URISyntaxException {
        log.debug("REST request to save Contact : {}", contactDTO);
        if (contactDTO.getId() != null) {
            throw new BadRequestAlertException("A new contact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactDTO result = contactService.save(contactDTO);
        return ResponseEntity.created(new URI("/api/contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contacts : Updates an existing contact.
     *
     * @param contactDTO the contactDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contactDTO,
     * or with status 400 (Bad Request) if the contactDTO is not valid,
     * or with status 500 (Internal Server Error) if the contactDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contacts")
    @Timed
    public ResponseEntity<ContactDTO> updateContact(@RequestBody ContactDTO contactDTO) throws URISyntaxException {
        log.debug("REST request to update Contact : {}", contactDTO);
        if (contactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContactDTO result = contactService.save(contactDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contactDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contacts : get all the contacts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contacts in body
     */
    @GetMapping("/contacts")
    @Timed
    public List<ContactDTO> getAllContacts() {
        log.debug("REST request to get all Contacts");
        return contactService.findAll();
    }

    /**
     * GET  /contacts/:id : get the "id" contact.
     *
     * @param id the id of the contactDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contactDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contacts/{id}")
    @Timed
    public ResponseEntity<ContactDTO> getContact(@PathVariable Long id) {
        log.debug("REST request to get Contact : {}", id);
        Optional<ContactDTO> contactDTO = contactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactDTO);
    }

    /**
     * DELETE  /contacts/:id : delete the "id" contact.
     *
     * @param id the id of the contactDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contacts/{id}")
    @Timed
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        log.debug("REST request to delete Contact : {}", id);
        contactService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    // -------------------------------------------------
    // added by me
    // -------------------------------------------------
    /**
     * POST  /contacts : Create a new contact.
     *
     * @param contactDTO the contactDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contactDTO, or with status 400 (Bad Request) if the contact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contactsbyuser")
    @Timed
    public ResponseEntity<ContactDTO> createContactByUser(@RequestBody ContactDTO contactDTO) throws URISyntaxException {
        log.debug("REST request to save Contact : {}", contactDTO);
        if (contactDTO.getId() != null) {
            throw new BadRequestAlertException("A new contact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        contactDTO.setClientId(user.getId());
        ContactDTO result = contactService.save(contactDTO);
        return ResponseEntity.created(new URI("/api/contactsbyuser/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contacts : Updates an existing contact.
     *
     * @param contactDTO the contactDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contactDTO,
     * or with status 400 (Bad Request) if the contactDTO is not valid,
     * or with status 500 (Internal Server Error) if the contactDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contactsbyuser")
    @Timed
    public ResponseEntity<ContactDTO> updateContactByUser(@RequestBody ContactDTO contactDTO) throws URISyntaxException {
        log.debug("REST request to update Contact : {}", contactDTO);
        if (contactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        // contactDTO.setClientId(user.getId());
        if (contactDTO != null && contactDTO.getClientId() != null &&
            !contactDTO.getClientId().equals(
                user.getId()
            )) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        ContactDTO result = contactService.save(contactDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contactDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contacts : get all the contacts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contacts in body
     */
    @GetMapping("/contactsbyuser")
    @Timed
    public List<ContactDTO> getAllContactsByUser() {
        log.debug("REST request to get all Contacts");
        // modifiyed by me
        // changed to call on new queries
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        Long id = user.getId();
        return contactService.findAll(id);
    }

    /**
     * GET  /contacts/:id : get the "id" contact.
     *
     * @param id the id of the contactDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contactDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contactsbyuser/{id}")
    @Timed
    public ResponseEntity<ContactDTO> getContactByUser(@PathVariable Long id) {
        log.debug("REST request to get Contact : {}", id);
        Optional<ContactDTO> contactDTO = contactService.findOne(id);
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        if (contactDTO.isPresent() && contactDTO.get().getClientId() != null &&
            !contactDTO.get().getClientId().equals(
                user.getId()
            )) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return ResponseUtil.wrapOrNotFound(contactDTO);
    }

    /**
     * DELETE  /contacts/:id : delete the "id" contact.
     *
     * @param id the id of the contactDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contactsbyuser/{id}")
    @Timed
    public ResponseEntity<Void> deleteContactByUser(@PathVariable Long id) {
        log.debug("REST request to delete Contact : {}", id);
        Optional<ContactDTO> contactDTO = contactService.findOne(id);
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        if (contactDTO.isPresent() && contactDTO.get().getClientId() != null &&
            !contactDTO.get().getClientId().equals(
                user.getId()
            )) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        contactService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
