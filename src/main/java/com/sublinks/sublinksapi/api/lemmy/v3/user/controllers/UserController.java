package com.sublinks.sublinksapi.api.lemmy.v3.user.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.models.LoginResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.BannedPersonsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetPersonDetails;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetPersonDetailsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetPersonMentionsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetRepliesResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetUnreadCountResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonMentionResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.services.LemmyPersonService;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.repositories.PersonRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.LinkedHashSet;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/user")
public class UserController {
    private final LemmyPersonService lemmyPersonService;
    private final PersonRepository personRepository;

    @GetMapping()
    GetPersonDetailsResponse show(@Valid final GetPersonDetails getPersonDetailsForm) {

        Long userId = null;
        Person person = null;
        if (getPersonDetailsForm.person_id() != null) {
            userId = (long) getPersonDetailsForm.person_id();
            person = personRepository.findById(userId).orElseThrow();
        } else if (getPersonDetailsForm.username() != null) {
            person = personRepository.findOneByName(getPersonDetailsForm.username());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no_id_given");
        }

        return GetPersonDetailsResponse.builder()
                .person_view(lemmyPersonService.getPersonView(person))
                .posts(lemmyPersonService.getPersonPosts(person))
                .moderates(lemmyPersonService.getPersonModerates(person))
                .comments(lemmyPersonService.getPersonComments(person))
                .build();
    }

    @GetMapping("mention")
    GetPersonMentionsResponse mention() {

        return GetPersonMentionsResponse.builder().build();
    }

    @PostMapping("mention/mark_as_read")
    PersonMentionResponse mentionMarkAsRead() {

        return PersonMentionResponse.builder().build();
    }

    @GetMapping("replies")
    GetRepliesResponse replies() {

        return GetRepliesResponse.builder().build();
    }

    @GetMapping("banned")
    BannedPersonsResponse bannedList() {

        final Collection<PersonView> bannedPersons = new LinkedHashSet<>();
        return BannedPersonsResponse.builder()
                .banned(bannedPersons)
                .build();
    }

    @PostMapping("mark_all_as_read")
    GetRepliesResponse markAllAsRead() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping("save_user_settings")
    LoginResponse saveUserSettings() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("unread_count")
    GetUnreadCountResponse unreadCount() {

        return GetUnreadCountResponse.builder().build();
    }
}
