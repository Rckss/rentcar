package com.ricks.rntcr.service.mapper;

import com.ricks.rntcr.domain.Tag;
import com.ricks.rntcr.service.dto.TagDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Tag and its DTO TagDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TagMapper extends EntityMapper<TagDTO, Tag> {



    default Tag fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tag tag = new Tag();
        tag.setId(id);
        return tag;
    }
}
