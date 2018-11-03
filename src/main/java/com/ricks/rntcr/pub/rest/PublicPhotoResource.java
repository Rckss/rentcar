package com.ricks.rntcr.pub.rest;

import com.codahale.metrics.annotation.Timed;
import com.ricks.rntcr.pub.rest.util.PaginationUtil;
import com.ricks.rntcr.service.PhotoService;
import com.ricks.rntcr.service.dto.PhotoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Photo.
 */
@RestController
@RequestMapping("/res")
public class PublicPhotoResource {

    private final Logger log = LoggerFactory.getLogger(PublicPhotoResource.class);

    private static final String ENTITY_NAME = "photo";

    private final PhotoService photoService;

    public PublicPhotoResource(PhotoService photoService) {
        this.photoService = photoService;
    }

    /**
     * GET  /photos : get all the photos.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of photos in body
     */
    @GetMapping("/photos")
    @Timed
    public ResponseEntity<List<PhotoDTO>> getAllPhotos(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Photos");
        Page<PhotoDTO> page;
        if (eagerload) {
            page = photoService.findAllWithEagerRelationships(pageable);
        } else {
            page = photoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/res/photos?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /photos/:id : get the "id" photo.
     *
     * @param id the id of the photoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the photoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/photos/{id}")
    @Timed
    public ResponseEntity<PhotoDTO> getPhoto(@PathVariable Long id) {
        log.debug("REST request to get Photo : {}", id);
        Optional<PhotoDTO> photoDTO = photoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(photoDTO);
    }
}
