package com.sublinks.sublinksapi.person.config;

import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.dto.PersonAggregate;
import com.sublinks.sublinksapi.person.models.PersonContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Configuration
public class PersonConfig {
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public PersonContext signedInUserContext() {

        // @todo actual relationship data for the user
        final Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        PersonContext personContext = new PersonContext();
        if (principal instanceof Person person) {
            personContext.setPerson(person);
            personContext.setPersonAggregate(aggregates(person));
            personContext.setDiscussLanguages(discussionLanguages(person));
            personContext.setFollows(follows(person));
            personContext.setModerates(moderates(person));
            personContext.setPersonBlocks(personBlocks(person));
            personContext.setCommunityBlocks(communityBlocks(person));
        }
        return personContext;
    }

    private PersonAggregate aggregates(final Person person) {
        return null;
    }

    private Collection<Community> communityBlocks(final Person person) {
        final Collection<Community> communities = new HashSet<>();
        return communities;
    }

    private Collection<Person> personBlocks(final Person person) {
        final Collection<Person> people = new HashSet<>();
        return people;
    }

    private Collection<Community> follows(final Person person) {
        final Collection<Community> communities = new HashSet<>();
        return communities;
    }

    private Collection<Integer> discussionLanguages(final Person person) {
        final List<Integer> languageIds = new ArrayList<>();
        languageIds.add(1);
        languageIds.add(38);
        return languageIds;
    }

    public Collection<Community> moderates(final Person person) {
        final Collection<Community> communities = new HashSet<>();
        return communities;
    }
}
