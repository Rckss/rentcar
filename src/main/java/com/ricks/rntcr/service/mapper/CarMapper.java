package com.ricks.rntcr.service.mapper;

import com.ricks.rntcr.domain.Car;
import com.ricks.rntcr.service.dto.CarDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Car and its DTO CarDTO.
 */
@Mapper(componentModel = "spring", uses = {AlbumMapper.class, TagMapper.class, PhotoMapper.class})
public interface CarMapper extends EntityMapper<CarDTO, Car> {

    @Mapping(source = "album.id", target = "albumId")
    @Mapping(source = "photo.id", target = "photoId")
    CarDTO toDto(Car car);

    @Mapping(source = "albumId", target = "album")
    @Mapping(source = "photoId", target = "photo")
    Car toEntity(CarDTO carDTO);

    default Car fromId(Long id) {
        if (id == null) {
            return null;
        }
        Car car = new Car();
        car.setId(id);
        return car;
    }
}
