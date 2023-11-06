package com.sublinks.sublinksapi.utils;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SlugUtil {
    public String stringToSlug(String title) {
        final String titleSlug = title
                .toLowerCase()
                .replace("\n", " ")
                .replace("[^a-z\\d\\s]", " ")
                .replaceAll("\\s+", "_");
        return randomStringGenerator() + "-" + titleSlug;
    }

    public String randomStringGenerator() {

        final Random random = new Random();
        return random.ints(97, 123)
                .limit(8)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
