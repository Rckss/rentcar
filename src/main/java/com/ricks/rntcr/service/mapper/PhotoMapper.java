package com.ricks.rntcr.service.mapper;

import com.ricks.rntcr.domain.Photo;
import com.ricks.rntcr.service.dto.PhotoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Photo and its DTO PhotoDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, AlbumMapper.class, TagMapper.class})
public interface PhotoMapper extends EntityMapper<PhotoDTO, Photo> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "album.id", target = "albumId")
    @Mapping(source = "album.title", target = "albumTitle")
    PhotoDTO toDto(Photo photo);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "albumId", target = "album")
    Photo toEntity(PhotoDTO photoDTO);

    default Photo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Photo photo = new Photo();
        photo.setId(id);
        return photo;
    }
}
