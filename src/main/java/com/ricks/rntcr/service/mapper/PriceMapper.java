package com.ricks.rntcr.service.mapper;

import com.ricks.rntcr.domain.Price;
import com.ricks.rntcr.service.dto.PriceDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Price and its DTO PriceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PriceMapper extends EntityMapper<PriceDTO, Price> {



    default Price fromId(Long id) {
        if (id == null) {
            return null;
        }
        Price price = new Price();
        price.setId(id);
        return price;
    }
}
