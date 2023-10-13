package com.fedilinks.fedilinksapi.person;

import com.fedilinks.fedilinksapi.util.KeyService;
import com.fedilinks.fedilinksapi.util.KeyStore;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
public class PersonService {
    private final KeyService keyService;

    public PersonService(KeyService keyService) {
        this.keyService = keyService;
    }

    public Person create(
            String name
    ) throws NoSuchAlgorithmException {
        KeyStore keys = keyService.generate();
        return Person.builder()
                .name(name)
                .activityPubId("")
                .avatarImageUrl("")
                .bannerImageUrl("")
                .biography("")
                .publicKey(keys.publicKey())
                .privateKey(keys.privateKey())
                .isLocal(true)
                .isBanned(false)
                .build();
    }
}
