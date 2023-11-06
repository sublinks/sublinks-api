package com.sublinks.sublinksapi.api.lemmy.v3.user.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.authentication.models.LoginResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.BannedPersonsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetPersonDetails;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetPersonDetailsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetPersonMentionsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetRepliesResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetUnreadCountResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonMentionResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.SaveUserSettings;
import com.sublinks.sublinksapi.api.lemmy.v3.user.services.LemmyPersonService;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.person.enums.SortType;
import com.sublinks.sublinksapi.person.repositories.PersonRepository;
import com.sublinks.sublinksapi.person.services.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/user")
public class UserController {
    private final LemmyPersonService lemmyPersonService;
    private final PersonRepository personRepository;
    private final ConversionService conversionService;
    private final PersonService personService;

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
    LoginResponse saveUserSettings(@Valid @RequestBody SaveUserSettings saveUserSettingsForm, JwtPerson principal) {
        Person person = Optional.ofNullable((Person)principal.getPrincipal())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        // @todo expand form validation to check for email formatting, etc.
        person.setShowNsfw(saveUserSettingsForm.show_nsfw() != null && saveUserSettingsForm.show_nsfw());
        // @todo add blur nfsw content
        // @todo add auto expand media
        // @todo show bot accounts
        person.setShowScores(saveUserSettingsForm.show_scores() != null && saveUserSettingsForm.show_scores());
        person.setDefaultTheme(saveUserSettingsForm.theme() != null? saveUserSettingsForm.theme(): "");
        person.setDefaultSortType(conversionService.convert(saveUserSettingsForm.default_sort_type(), SortType.class));
        person.setDefaultListingType(conversionService.convert(saveUserSettingsForm.default_listing_type(), ListingType.class));
        person.setInterfaceLanguage(saveUserSettingsForm.interface_language() != null ? saveUserSettingsForm.interface_language(): "");
        person.setAvatarImageUrl("");
        person.setBannerImageUrl("");
        person.setDisplayName(saveUserSettingsForm.display_name() != null ? saveUserSettingsForm.display_name(): "");
        person.setEmail(saveUserSettingsForm.email()); // @todo verify email again?
        person.setBiography(saveUserSettingsForm.bio() != null ? saveUserSettingsForm.bio(): "");
        // @todo matrix user
        person.setShowAvatars(saveUserSettingsForm.show_avatars() != null && saveUserSettingsForm.show_avatars());
        person.setSendNotificationsToEmail(saveUserSettingsForm.send_notifications_to_email() != null && saveUserSettingsForm.send_notifications_to_email());
        person.setBotAccount(saveUserSettingsForm.bot_account() != null && saveUserSettingsForm.bot_account());
        person.setShowReadPosts(saveUserSettingsForm.show_read_posts() != null && saveUserSettingsForm.show_read_posts());
        person.setShowNewPostNotifications(saveUserSettingsForm.show_new_post_notifs() != null && saveUserSettingsForm.show_new_post_notifs());
        // @todo generate_totp_2fa
        person.setOpenLinksInNewTab(saveUserSettingsForm.open_links_in_new_tab() != null && saveUserSettingsForm.open_links_in_new_tab());

        // @todo languages

        personService.updatePerson(person);

        return LoginResponse.builder()
                .jwt(null)
                .registration_created(false)
                .verify_email_sent(false)
                .build();
    }

    @GetMapping("unread_count")
    GetUnreadCountResponse unreadCount() {

        return GetUnreadCountResponse.builder().build();
    }
}
