package com.ricks.rntcr.service.impl;

import com.ricks.rntcr.domain.Photo;
import com.ricks.rntcr.repository.PhotoRepository;
import com.ricks.rntcr.service.PhotoService;
import com.ricks.rntcr.service.dto.PhotoDTO;
import com.ricks.rntcr.service.mapper.PhotoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Photo.
 */
@Service
@Transactional
public class PhotoServiceImpl implements PhotoService {

    private final Logger log = LoggerFactory.getLogger(PhotoServiceImpl.class);

    private final PhotoRepository photoRepository;

    private final PhotoMapper photoMapper;

    public PhotoServiceImpl(PhotoRepository photoRepository, PhotoMapper photoMapper) {
        this.photoRepository = photoRepository;
        this.photoMapper = photoMapper;
    }

    /**
     * Save a photo.
     *
     * @param photoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PhotoDTO save(PhotoDTO photoDTO) {
        log.debug("Request to save Photo : {}", photoDTO);
        Photo photo = photoMapper.toEntity(photoDTO);
        photo = photoRepository.save(photo);
        return photoMapper.toDto(photo);
    }

    /**
     * Get all the photos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PhotoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Photos");
        return photoRepository.findAll(pageable)
            .map(photoMapper::toDto);
    }

    // added by me
    @Override
    @Transactional(readOnly = true)
    public Page<PhotoDTO> findAll(Pageable pageable, Long id) {
        log.debug("Request to get all Photos");
        return photoRepository.findAll(pageable, id)
            .map(photoMapper::toDto);
    }

    // added by me
    @Override
    @Transactional(readOnly = true)
    public Page<PhotoDTO> findAll(Pageable pageable, Long userId, Long albumId) {
        log.debug("Request to get all Photos");
        return photoRepository.findAll(pageable, userId, albumId)
            .map(photoMapper::toDto);
    }

    // added by me
    @Override
    @Transactional(readOnly = true)
    public Page<PhotoDTO> findAllByAlbumAndPhotoId(Pageable pageable, Long albumId, Long photoId) {
        log.debug("Request to get all Photos");
        return photoRepository.findAllByAlbumAndPhotoId(pageable, albumId, photoId)
            .map(photoMapper::toDto);
    }

    // added by me
    @Override
    @Transactional(readOnly = true)
    public Page<PhotoDTO> findAllByAlbum(Pageable pageable, Long albumId) {
        log.debug("Request to get all Photos");
        return photoRepository.findAllByAlbum(pageable, albumId)
            .map(photoMapper::toDto);
    }
    /**
     * Get all the Photo with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<PhotoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return photoRepository.findAllWithEagerRelationships(pageable).map(photoMapper::toDto);
    }

    // new method
    public Page<PhotoDTO> findAllWithEagerRelationships(Pageable pageable, Long id) {
        return photoRepository.findAllWithEagerRelationships(pageable, id).map(photoMapper::toDto);
    }

    // new method
    public Page<PhotoDTO> findAllWithEagerRelationships(Pageable pageable, Long userId, Long albumId) {
        return photoRepository.findAllWithEagerRelationships(pageable, userId, albumId).map(photoMapper::toDto);
    }

    // new method
    public Page<PhotoDTO> findAllByAlbumAndPhotoIdWithEagerRelationships(Pageable pageable, Long albumId, Long photoId) {
        return photoRepository.findAllByAlbumAndPhotoIdWithEagerRelationships(pageable, albumId, photoId).map(photoMapper::toDto);
    }

    // new method
    public Page<PhotoDTO> findAllByAlbumWithEagerRelationships(Pageable pageable, Long albumId) {
        return photoRepository.findAllWithEagerRelationships(pageable, albumId).map(photoMapper::toDto);
    }
    /**
     * Get one photo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PhotoDTO> findOne(Long id) {
        log.debug("Request to get Photo : {}", id);
        return photoRepository.findOneWithEagerRelationships(id)
            .map(photoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PhotoDTO> findOneByAlbumIdAndPhotoId(Long AlbumId, Long photoId) {
        log.debug("Request to get Photo : {}", AlbumId);
        return photoRepository.getOneByAlbumAndPhotoIdWithEagerRelationships(AlbumId, photoId)
            .map(photoMapper::toDto);
    }
    /**
     * Delete the photo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Photo : {}", id);
        photoRepository.deleteById(id);
    }
}
