package com.sublinks.sublinksapi.utils;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SlugUtil {
    public String stringToSlug(String title) {

        return title
                .toLowerCase()
                .replace("\n", " ")
                .replace("[^a-z\\d\\s]", " ")
                .replaceAll("\\s+", "_");
    }

    public String uniqueSlug(String title) {

        return randomStringGenerator() + "-" + stringToSlug(title);
    }

    public String randomStringGenerator() {

        final Random random = new Random();
        return random.ints(97, 123)
                .limit(8)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
