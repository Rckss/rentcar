package com.ricks.rntcr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ricks.rntcr.domain.User;
import com.ricks.rntcr.service.AlbumService;
import com.ricks.rntcr.service.UserService;
import com.ricks.rntcr.service.dto.AlbumDTO;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static com.ricks.rntcr.security.SecurityUtils.getCurrentUserLogin;

/**
 * REST controller for managing Album.
 */
@RestController
@RequestMapping("/api")
public class AlbumResource {

    private final Logger log = LoggerFactory.getLogger(AlbumResource.class);

    private static final String ENTITY_NAME = "album";

    private final AlbumService albumService;

    private final UserService usrServ;

    public AlbumResource(AlbumService albumService, UserService usrServ) {
        this.albumService = albumService;
        this.usrServ = usrServ;
    }

    /**
     * POST  /albums : Create a new album.
     *
     * @param albumDTO the albumDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new albumDTO, or with status 400 (Bad Request) if the album has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/albums")
    @Timed
    public ResponseEntity<AlbumDTO> createAlbum(@Valid @RequestBody AlbumDTO albumDTO) throws URISyntaxException {
        log.debug("REST request to save Album : {}", albumDTO);
        if (albumDTO.getId() != null) {
            throw new BadRequestAlertException("A new album cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlbumDTO result = albumService.save(albumDTO);
        return ResponseEntity.created(new URI("/api/albums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /albums : Updates an existing album.
     *
     * @param albumDTO the albumDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated albumDTO,
     * or with status 400 (Bad Request) if the albumDTO is not valid,
     * or with status 500 (Internal Server Error) if the albumDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/albums")
    @Timed
    public ResponseEntity<AlbumDTO> updateAlbum(@Valid @RequestBody AlbumDTO albumDTO) throws URISyntaxException {
        log.debug("REST request to update Album : {}", albumDTO);
        if (albumDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlbumDTO result = albumService.save(albumDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, albumDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /albums : get all the albums.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of albums in body
     */
    @GetMapping("/albums")
    @Timed
    public ResponseEntity<List<AlbumDTO>> getAllAlbums(Pageable pageable) {
        log.debug("REST request to get a page of Albums");
        Page<AlbumDTO> page = albumService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/albums");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /albums/:id : get the "id" album.
     *
     * @param id the id of the albumDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the albumDTO, or with status 404 (Not Found)
     */
    @GetMapping("/albums/{id}")
    @Timed
    public ResponseEntity<AlbumDTO> getAlbum(@PathVariable Long id) {
        log.debug("REST request to get Album : {}", id);
        Optional<AlbumDTO> albumDTO = albumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(albumDTO);
    }

    /**
     * DELETE  /albums/:id : delete the "id" album.
     *
     * @param id the id of the albumDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/albums/{id}")
    @Timed
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        log.debug("REST request to delete Album : {}", id);
        albumService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    // ------------------------------------------------------------------------------------------------------------------
    /**
     * POST  /albums : Create a new album.
     *
     * @param albumDTO the albumDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new albumDTO, or with status 400 (Bad Request) if the album has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/albumsbyuser")
    @Timed
    public ResponseEntity<AlbumDTO> createAlbumByUser(@Valid @RequestBody AlbumDTO albumDTO) throws URISyntaxException {
        log.debug("REST request to save Album : {}", albumDTO);
        if (albumDTO.getId() != null) {
            throw new BadRequestAlertException("A new album cannot already have an ID", ENTITY_NAME, "idexists");
        }
        // added from me
        // get logged userID and associate with new photo
        //User user = userRepo.findOneByLogin(getCurrentUserLogin().get()).get();
        //photoDTO.setUserId(user.getId());
        // set from service not from repo
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        albumDTO.setUserId(user.getId());
        AlbumDTO result = albumService.save(albumDTO);
        return ResponseEntity.created(new URI("/api/albumsbyuser/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /albums : Updates an existing album.
     *
     * @param albumDTO the albumDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated albumDTO,
     * or with status 400 (Bad Request) if the albumDTO is not valid,
     * or with status 500 (Internal Server Error) if the albumDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/albumsbyuser")
    @Timed
    public ResponseEntity<AlbumDTO> updateAlbumByUser(@Valid @RequestBody AlbumDTO albumDTO) throws URISyntaxException {
        log.debug("REST request to update Album : {}", albumDTO);
        if (albumDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        // added from me
        // throws forbidden access error if try to access not owned resources
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        if ( !albumDTO.getUserId().equals( user.getId() )) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        AlbumDTO result = albumService.save(albumDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, albumDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /albums : get all the albums.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of albums in body
     */
    @GetMapping("/albumsbyuser")
    @Timed
    public ResponseEntity<List<AlbumDTO>> getAllAlbumsByUser(Pageable pageable) {
        log.debug("REST request to get a page of Albums");
        // modifiyed by me
        // changed to call on new queries
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        Long id = user.getId();
        Page<AlbumDTO> page = albumService.findAll(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/albumsbyuser");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /albums/:id : get the "id" album.
     *
     * @param id the id of the albumDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the albumDTO, or with status 404 (Not Found)
     */
    @GetMapping("/albumsbyuser/{id}")
    @Timed
    public ResponseEntity<AlbumDTO> getAlbumByUser(@PathVariable Long id) {
        log.debug("REST request to get Album : {}", id);
        Optional<AlbumDTO> albumDTO = albumService.findOne(id);
        // added from me
        // throws forbidden access error if try to access not owned resources
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        if ( !albumDTO.get().getUserId().equals( user.getId() )) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return ResponseUtil.wrapOrNotFound(albumDTO);
    }

    /**
     * DELETE  /albums/:id : delete the "id" album.
     *
     * @param id the id of the albumDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/albumsbyuser/{id}")
    @Timed
    public ResponseEntity<Void> deleteAlbumByUser(@PathVariable Long id) {
        log.debug("REST request to delete Album : {}", id);
        Optional<AlbumDTO> albumDTO = albumService.findOne(id);
        // added from me
        // throws forbidden access error if try to access not owned resources
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        if ( !albumDTO.get().getUserId().equals( user.getId() )) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        albumService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
