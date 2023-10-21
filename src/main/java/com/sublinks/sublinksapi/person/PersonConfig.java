package com.sublinks.sublinksapi.person;

import com.sublinks.sublinksapi.community.Community;
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
    PersonContext signedInUserContext() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        PersonContext personContext = new PersonContext();
        if (principal instanceof Person person) {
            personContext.setPerson(person);
            personContext.setPersonAggregates(aggregates(person));
            personContext.setDiscussLanguages(discussionLanguages(person));
            personContext.setFollows(follows(person));
            personContext.setModerates(moderates(person));
            personContext.setPersonBlocks(personBlocks(person));
            personContext.setCommunityBlocks(communityBlocks(person));
        }
        return personContext;
    }

    private PersonAggregates aggregates(Person person) {
        return null;
    }

    private Collection<Community> communityBlocks(Person person) {
        Collection<Community> communities = new HashSet<>();
        return communities;
    }

    private Collection<Person> personBlocks(Person person) {
        Collection<Person> people = new HashSet<>();
        return people;
    }

    private Collection<Community> follows(Person person) {
        Collection<Community> communities = new HashSet<>();
        return communities;
    }

    private Collection<Integer> discussionLanguages(Person person) {
        List<Integer> languageIds = new ArrayList<>();
        languageIds.add(1);
        languageIds.add(38);
        return languageIds;
    }

    public Collection<Community> moderates(Person person) {
        Collection<Community> communities = new HashSet<>();
        return communities;
    }
}
