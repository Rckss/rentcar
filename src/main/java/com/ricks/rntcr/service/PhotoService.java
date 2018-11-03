package com.ricks.rntcr.service;

import com.ricks.rntcr.service.dto.PhotoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Photo.
 */
public interface PhotoService {

    /**
     * Save a photo.
     *
     * @param photoDTO the entity to save
     * @return the persisted entity
     */
    PhotoDTO save(PhotoDTO photoDTO);

    /**
     * Get all the photos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PhotoDTO> findAll(Pageable pageable);

    /**
     * Get all the Photo with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<PhotoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" photo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PhotoDTO> findOne(Long id);

    /**
     * Delete the "id" photo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    Page<PhotoDTO> findAllWithEagerRelationships(Pageable pageable, Long id);

    Page<PhotoDTO> findAll(Pageable pageable, Long id);

    Page<PhotoDTO> findAll(Pageable pageable, Long userId, Long albumId);

    Page<PhotoDTO> findAllWithEagerRelationships(Pageable pageable, Long userId, Long albumId);

    Page<PhotoDTO> findAllByAlbum(Pageable pageable, Long albumId);

    Page<PhotoDTO> findAllByAlbumWithEagerRelationships(Pageable pageable, Long albumId);

    Page<PhotoDTO> findAllByAlbumAndPhotoId(Pageable pageable, Long albumId, Long photoId);

    Page<PhotoDTO> findAllByAlbumAndPhotoIdWithEagerRelationships(Pageable pageable, Long albumId, Long photoId);

    Optional<PhotoDTO> findOneByAlbumIdAndPhotoId(Long AlbumId, Long photoId);
}
