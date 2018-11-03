package com.ricks.rntcr.repository;

import com.ricks.rntcr.domain.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Photo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query("select photo from Photo photo where photo.user.login = ?#{principal.username}")
    List<Photo> findByUserIsCurrentUser();

    @Query(value = "select distinct photo from Photo photo left join fetch photo.tags",
        countQuery = "select count(distinct photo) from Photo photo")
    Page<Photo> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct photo from Photo photo left join fetch photo.tags")
    List<Photo> findAllWithEagerRelationships();

    @Query("select photo from Photo photo left join fetch photo.tags where photo.id =:id")
    Optional<Photo> findOneWithEagerRelationships(@Param("id") Long id);

    @Query(value = "select distinct photo from Photo photo left join fetch photo.tags where photo.user.id = :id",
        countQuery = "select count(distinct photo) from Photo photo")
    Page<Photo> findAll(Pageable pageable, @Param("id") Long id);

    @Query(value = "select distinct photo from Photo photo left join fetch photo.tags where photo.user.id = :id",
        countQuery = "select count(distinct photo) from Photo photo")
    Page<Photo> findAllWithEagerRelationships(Pageable pageable, @Param("id") Long id);

    @Query(value = "select distinct photo from Photo photo where photo.user.id = :userId and photo.album.id = :albumId",
        countQuery = "select count(distinct photo) from Photo photo")
    Page<Photo> findAll(Pageable pageable, @Param("userId") Long userId, @Param("albumId") Long albumId);

    @Query(value = "select distinct photo from Photo photo left join fetch photo.tags where photo.user.id = :userId and photo.album.id = :albumId",
        countQuery = "select count(distinct photo) from Photo photo")
    Page<Photo> findAllWithEagerRelationships(Pageable pageable, @Param("userId") Long userId, @Param("albumId") Long albumId);

    @Query(value = "select distinct photo from Photo photo where photo.album.id = :albumId",
        countQuery = "select count(distinct photo) from Photo photo")
    Page<Photo> findAllByAlbum(Pageable pageable, @Param("albumId") Long albumId);

    @Query(value = "select distinct photo from Photo photo left join fetch photo.tags where photo.album.id = :albumId",
        countQuery = "select count(distinct photo) from Photo photo")
    Page<Photo> findAllByAlbumWithEagerRelationships(Pageable pageable, @Param("albumId") Long albumId);

    @Query(value = "select distinct photo from Photo photo where photo.id = :photoId and photo.album.id = :albumId",
        countQuery = "select count(distinct photo) from Photo photo")
    Page<Photo> findAllByAlbumAndPhotoId(Pageable pageable, @Param("albumId") Long albumId, @Param("photoId") Long photoId);

    @Query(value = "select distinct photo from Photo photo left join fetch photo.tags where photo.id = :photoId and photo.album.id = :albumId",
        countQuery = "select count(distinct photo) from Photo photo")
    Page<Photo> findAllByAlbumAndPhotoIdWithEagerRelationships(Pageable pageable, @Param("albumId") Long albumId, @Param("photoId") Long photoId);

    @Query("select distinct photo from Photo photo where photo.id = :photoId and photo.album.id = :albumId")
    Optional<Photo> getOneByAlbumAndPhotoId( @Param("albumId") Long albumId, @Param("photoId") Long photoId);

    @Query("select distinct photo from Photo photo left join fetch photo.tags where photo.id = :photoId and photo.album.id = :albumId")
    Optional<Photo> getOneByAlbumAndPhotoIdWithEagerRelationships( @Param("albumId") Long albumId, @Param("photoId") Long photoId);
}
