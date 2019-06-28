package com.ricks.rntcr.service.impl;

import com.ricks.rntcr.domain.Album;
import com.ricks.rntcr.repository.AlbumRepository;
import com.ricks.rntcr.service.AlbumService;
import com.ricks.rntcr.service.dto.AlbumDTO;
import com.ricks.rntcr.service.mapper.AlbumMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Album.
 */
@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    private final Logger log = LoggerFactory.getLogger(AlbumServiceImpl.class);

    private final AlbumRepository albumRepository;

    private final AlbumMapper albumMapper;

    public AlbumServiceImpl(AlbumRepository albumRepository, AlbumMapper albumMapper) {
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
    }

    /**
     * Save a album.
     *
     * @param albumDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AlbumDTO save(AlbumDTO albumDTO) {
        log.debug("Request to save Album : {}", albumDTO);
        Album album = albumMapper.toEntity(albumDTO);
        album = albumRepository.save(album);
        return albumMapper.toDto(album);
    }

    /**
     * Get all the albums.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AlbumDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Albums");
        return albumRepository.findAll(pageable)
            .map(albumMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlbumDTO> findAll(Pageable pageable, Long id) {
        log.debug("Request to get all Albums");
        return albumRepository.findAll(pageable, id)
            .map(albumMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlbumDTO> findAllWithNullId(Pageable pageable) {
        log.debug("Request to get all Albums");
        return albumRepository.findAllWithNullId(pageable)
            .map(albumMapper::toDto);
    }

    /**
     * Get one album by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AlbumDTO> findOne(Long id) {
        log.debug("Request to get Album : {}", id);
        return albumRepository.findById(id)
            .map(albumMapper::toDto);
    }

    /**
     * Delete the album by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Album : {}", id);
        albumRepository.deleteById(id);
    }
}
