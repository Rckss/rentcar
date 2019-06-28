package com.ricks.rntcr.repository;

import com.ricks.rntcr.domain.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Album entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query("select album from Album album where album.user.login = ?#{principal.username}")
    List<Album> findByUserIsCurrentUser();

    @Query(value = "select distinct album from Album album where album.user.id = :id",
        countQuery = "select count(distinct album) from Album album")
    Page<Album> findAll(Pageable pageable, @Param("id") Long id);

    @Query(value = "select distinct album from Album album where album.user.id = null",
        countQuery = "select count(distinct album) from Album album")
    Page<Album> findAllWithNullId(Pageable pageable);
}
