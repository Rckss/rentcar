package com.ricks.rntcr.service.mapper;

import com.ricks.rntcr.domain.Contact;
import com.ricks.rntcr.service.dto.ContactDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Contact and its DTO ContactDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, PhotoMapper.class})
public interface ContactMapper extends EntityMapper<ContactDTO, Contact> {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "photo.id", target = "photoId")
    ContactDTO toDto(Contact contact);

    @Mapping(source = "clientId", target = "client")
    @Mapping(source = "photoId", target = "photo")
    Contact toEntity(ContactDTO contactDTO);

    default Contact fromId(Long id) {
        if (id == null) {
            return null;
        }
        Contact contact = new Contact();
        contact.setId(id);
        return contact;
    }
}
