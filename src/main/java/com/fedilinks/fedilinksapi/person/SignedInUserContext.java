package com.fedilinks.fedilinksapi.person;

import com.fedilinks.fedilinksapi.community.Community;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SignedInUserContext {
    private Person person;
    private PersonAggregates personAggregates;
    private Collection<Integer> discussLanguages;
    private Collection<Community> moderates;
    private Collection<Community> follows;
    private Collection<Community> communityBlocks;
    private Collection<Person> personBlocks;
}
