package com.ricks.rntcr.pub.rest;

import com.codahale.metrics.annotation.Timed;
import com.ricks.rntcr.service.ContactService;
import com.ricks.rntcr.service.dto.ContactDTO;
import com.ricks.rntcr.web.rest.errors.BadRequestAlertException;
import com.ricks.rntcr.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing Contact.
 */
@RestController
@RequestMapping("/res")
public class PublicContactResource {

    private final Logger log = LoggerFactory.getLogger(PublicContactResource.class);

    private static final String ENTITY_NAME = "contact";

    private final ContactService contactService;

    public PublicContactResource(ContactService contactService) {
        this.contactService = contactService;
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
        return ResponseEntity.created(new URI("/res/contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}
