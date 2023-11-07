package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonInstanceType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.lang.NonNull;

import java.util.Arrays;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AdminBooleanMapper {
    boolean mapAdmin(@NonNull Person person) {
        return Arrays
                .asList(LinkPersonInstanceType.admin, LinkPersonInstanceType.super_admin)
                .contains(person.getLinkPersonInstance().getLinkType());
    }
}
