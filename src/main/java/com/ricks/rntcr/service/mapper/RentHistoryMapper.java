package com.ricks.rntcr.service.mapper;

import com.ricks.rntcr.domain.RentHistory;
import com.ricks.rntcr.service.dto.RentHistoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity RentHistory and its DTO RentHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {CarMapper.class, UserMapper.class})
public interface RentHistoryMapper extends EntityMapper<RentHistoryDTO, RentHistory> {

    @Mapping(source = "client.id", target = "clientId")
    RentHistoryDTO toDto(RentHistory rentHistory);

    @Mapping(source = "clientId", target = "client")
    RentHistory toEntity(RentHistoryDTO rentHistoryDTO);

    default RentHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        RentHistory rentHistory = new RentHistory();
        rentHistory.setId(id);
        return rentHistory;
    }
}
