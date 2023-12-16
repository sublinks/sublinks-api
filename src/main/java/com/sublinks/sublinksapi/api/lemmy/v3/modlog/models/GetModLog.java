package com.sublinks.sublinksapi.api.lemmy.v3.modlog.models;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.ModlogActionType;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record GetModLog(
    Long mod_person_id,
    Long community_id,
    int page,
    int limit,
    ModlogActionType type_,
    Long other_person_id
) {

}