package com.ricks.rntcr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.ricks.rntcr.domain.User;
import com.ricks.rntcr.service.PhotoService;
import com.ricks.rntcr.service.UserService;
import com.ricks.rntcr.service.dto.PhotoDTO;
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
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.ricks.rntcr.security.SecurityUtils.getCurrentUserLogin;

/**
 * REST controller for managing Photo.
 */
@RestController
@RequestMapping("/api")
public class PhotoResource {

    private final Logger log = LoggerFactory.getLogger(PhotoResource.class);

    private static final String ENTITY_NAME = "photo";

    private final PhotoService photoService;

    private final UserService usrServ;

    public PhotoResource(PhotoService photoService, UserService usrServ) {
        this.photoService = photoService;
        this.usrServ = usrServ;
    }

    // Don't forget to restrict all crud operations for admin
    // forbid everyone that isn't admin to access private data!
    /**
     * POST  /photos : Create a new photo.
     *
     * @param photoDTO the photoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new photoDTO, or with status 400 (Bad Request) if the photo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/photos")
    @Timed
    public ResponseEntity<PhotoDTO> createPhoto(@Valid @RequestBody PhotoDTO photoDTO) throws Exception {
        log.debug("REST request to save Photo : {}", photoDTO);
        if (photoDTO.getId() != null) {
            throw new BadRequestAlertException("A new photo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        // added from me
        try {
            photoDTO = setMetadata(photoDTO);
        } catch (ImageProcessingException ipe) {
            log.error(ipe.getMessage());
        }
        // end
        PhotoDTO result = photoService.save(photoDTO);
        return ResponseEntity.created(new URI("/api/photos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /photos : Updates an existing photo.
     *
     * @param photoDTO the photoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated photoDTO,
     * or with status 400 (Bad Request) if the photoDTO is not valid,
     * or with status 500 (Internal Server Error) if the photoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/photos")
    @Timed
    public ResponseEntity<PhotoDTO> updatePhoto(@Valid @RequestBody PhotoDTO photoDTO) throws Exception {
        log.debug("REST request to update Photo : {}", photoDTO);
        if (photoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        // added from me
        try {
            photoDTO = setMetadata(photoDTO);
        } catch (ImageProcessingException ipe) {
            log.error(ipe.getMessage());
        }
        // end
        PhotoDTO result = photoService.save(photoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, photoDTO.getId().toString()))
            .body(result);
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
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/photos?eagerload=%b", eagerload));
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

    /**
     * DELETE  /photos/:id : delete the "id" photo.
     *
     * @param id the id of the photoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/photos/{id}")
    @Timed
    public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {
        log.debug("REST request to delete Photo : {}", id);
        photoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    // Helper Method
    // added from me
    private PhotoDTO setMetadata(PhotoDTO photo) throws ImageProcessingException, IOException, MetadataException {
        String str = DatatypeConverter.printBase64Binary(photo.getImage());
        byte[] data2 = DatatypeConverter.parseBase64Binary(str);
        InputStream inputStream = new ByteArrayInputStream(data2);
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        Metadata metadata = ImageMetadataReader.readMetadata(bis);
        ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

        if (directory != null) {
            Date date = directory.getDateDigitized();
            if (date != null) {
                photo.setTaken(date.toInstant());
            }
        }

        if (photo.getTaken() == null) {
            log.debug("Photo EXIF date digitized not available, setting taken on date to now...");
            photo.setTaken(Instant.now());
        }

        photo.setUploaded(Instant.now());

        JpegDirectory jpgDirectory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
        if (jpgDirectory != null) {
            photo.setHeight(jpgDirectory.getImageHeight());
            photo.setWidth(jpgDirectory.getImageWidth());
        }

        return photo;
    }
    // -------------------------------------------------
    // added by me
    // -------------------------------------------------
    /**
     * POST  /photos : Create a new photo.
     *
     * @param photoDTO the photoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new photoDTO, or with status 400 (Bad Request) if the photo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/photosbyuser")
    @Timed
    public ResponseEntity<PhotoDTO> createPhotoByUser(@Valid @RequestBody PhotoDTO photoDTO) throws Exception {
        log.debug("REST request to save Photo : {}", photoDTO);
        if (photoDTO.getId() != null) {
            throw new BadRequestAlertException("A new photo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        // added from me
        // get logged userID and associate with new photo
        //User user = userRepo.findOneByLogin(getCurrentUserLogin().get()).get();
        //photoDTO.setUserId(user.getId());
        // set from service not from repo
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        photoDTO.setUserId(user.getId());
        try {
            photoDTO = setMetadata(photoDTO);
        } catch (ImageProcessingException ipe) {
            log.error(ipe.getMessage());
        }
        // end
        PhotoDTO result = photoService.save(photoDTO);
        return ResponseEntity.created(new URI("/api/photosbyuser/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /photos : Updates an existing photo.
     *
     * @param photoDTO the photoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated photoDTO,
     * or with status 400 (Bad Request) if the photoDTO is not valid,
     * or with status 500 (Internal Server Error) if the photoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/photosbyuser")
    @Timed
    public ResponseEntity<PhotoDTO> updatePhotoByUser(@Valid @RequestBody PhotoDTO photoDTO) throws Exception {
        log.debug("REST request to update Photo : {}", photoDTO);
        if (photoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        // added from me
        // throws forbidden access error if try to access not owned resources
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        if ( !photoDTO.getUserId().equals( user.getId() )) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            photoDTO = setMetadata(photoDTO);
        } catch (ImageProcessingException ipe) {
            log.error(ipe.getMessage());
        }
        // end
        PhotoDTO result = photoService.save(photoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, photoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /photos : get all the photos.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of photos in body
     */
    @GetMapping("/photosbyuser")
    @Timed
    public ResponseEntity<List<PhotoDTO>> getAllPhotosByUser(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Photos");
        Page<PhotoDTO> page;
        // modifiyed by me
        // changed to call on new queries
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        Long id = user.getId();

        if (eagerload) {
            page = photoService.findAllWithEagerRelationships(pageable, id);
        } else {
            page = photoService.findAll(pageable, id );
        }
        // -------------------------
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/photosbyuser?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     * GET  /photos/:id : get the "id" photo.
     *
     * @param id the id of the photoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the photoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/photosbyuser/{id}")
    @Timed
    public ResponseEntity<PhotoDTO> getPhotoByUser(@PathVariable Long id) {
        log.debug("REST request to get Photo : {}", id);
        Optional<PhotoDTO> photoDTO = photoService.findOne(id);
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        if (photoDTO.isPresent() && photoDTO.get().getUserId() != null &&
            !photoDTO.get().getUserId().equals(
                user.getId()
            )) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return ResponseUtil.wrapOrNotFound(photoDTO);
    }

    /**
     * DELETE  /photos/:id : delete the "id" photo.
     *
     * @param id the id of the photoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/photosbyuser/{id}")
    @Timed
    public ResponseEntity<Void> deletePhotoByUser(@PathVariable Long id) {
        log.debug("REST request to delete Photo : {}", id);
        Optional<PhotoDTO> photoDTO = photoService.findOne(id);
        User user = usrServ.getUserWithAuthoritiesByLogin(getCurrentUserLogin().get()).get();
        if (photoDTO.isPresent() && photoDTO.get().getUserId() != null &&
            !photoDTO.get().getUserId().equals(
                user.getId()
            )) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        photoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
/*
    @GetMapping("/photosbyuserandalbum/{userId}/{albumId}")
    @Timed
    public ResponseEntity<List<PhotoDTO>> getAllPhotosByUserAndAlbumId(Pageable pageable,@PathVariable Long userId, @PathVariable Long albumId, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Photos");
        Page<PhotoDTO> page;
        // *** restrict for users other than admin
        // added by me
        // get current logged user and save contact with associated logged user
        if(getCurrentUserLogin().get() != "admin") {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        // look for photos on albums related from respective user
        if (eagerload) {
            page = photoService.findAllWithEagerRelationships(pageable, userId, albumId);
        } else {
            page = photoService.findAll(pageable, userId, albumId );
        }
        // -------------------------
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/photosbyuserandalbum/%d/%d?eagerload=%b",userId, albumId, eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
*/
    @GetMapping("/photosbyalbum/{albumId}")
    @Timed
    public ResponseEntity<List<PhotoDTO>> getAllPhotosByAlbumId(Pageable pageable, @PathVariable Long albumId, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Photos");
        Page<PhotoDTO> page;
        if (eagerload) {
            page = photoService.findAllByAlbumWithEagerRelationships(pageable, albumId);
        } else {
            page = photoService.findAllByAlbum(pageable, albumId );
        }
        // -------------------------
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/photosbyalbum/%d?eagerload=%b", albumId, eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
/*
    @GetMapping("/photobyalbum/{albumId}/{photoId}")
    @Timed
    public ResponseEntity<List<PhotoDTO>> getAllPhotosByAlbumIdPhotoId(Pageable pageable,@PathVariable Long albumId, @PathVariable Long photoId, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Photos");
        Page<PhotoDTO> page;
        // *** restrict for users other than admin
        // added by me
        // get current logged user and save contact with associated logged user
        if(getCurrentUserLogin().get() != "admin") {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        // look for photos on albums related from respective user
        if (eagerload) {
            page = photoService.findAllByAlbumAndPhotoIdWithEagerRelationships(pageable, albumId, photoId);
        } else {
            page = photoService.findAllByAlbumAndPhotoId(pageable, albumId, photoId );
        }
        // -------------------------
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/photosbyuserandalbum/%d/%d?eagerload=%b",albumId, photoId, eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
*/
    @GetMapping("/photobyalbum/{albumId}/{photoId}")
    @Timed
    public ResponseEntity<PhotoDTO> getPhotoByAlbumIdAndPhotoId(@PathVariable Long albumId, @PathVariable Long photoId) {
        log.debug("REST request to get a page of Photos");
        // Page<PhotoDTO> page;
        // *** restrict for users other than admin
        // added by me
        // get current logged user and save contact with associated logged user
        // if(getCurrentUserLogin().get() != "admin") {
        //    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        // }
        // look for photos on albums related from respective user
        Optional<PhotoDTO> photoDTO = photoService.findOneByAlbumIdAndPhotoId(albumId, photoId);

        // -------------------------
        // HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/photosbyuserandalbum/%d/%d?eagerload=%b",albumId, photoId, eagerload));
        // return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        return ResponseUtil.wrapOrNotFound(photoDTO);
    }
}
