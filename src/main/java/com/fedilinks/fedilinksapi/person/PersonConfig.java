package com.fedilinks.fedilinksapi.person;

import com.fedilinks.fedilinksapi.community.Community;
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
    SignedInUserContext signedInUserContext() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        SignedInUserContext signedInUserContext = new SignedInUserContext();
        if (principal instanceof Person) {
            Person person = (Person)principal;
            signedInUserContext.setPerson(person);
            signedInUserContext.setPersonAggregates(aggregates(person));
            signedInUserContext.setDiscussLanguages(discussionLanguages(person));
            signedInUserContext.setFollows(follows(person));
            signedInUserContext.setModerates(moderates(person));
            signedInUserContext.setPersonBlocks(personBlocks(person));
            signedInUserContext.setCommunityBlocks(communityBlocks(person));
        }
        return signedInUserContext;
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
