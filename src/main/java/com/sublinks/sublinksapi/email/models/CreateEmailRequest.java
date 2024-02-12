package com.sublinks.sublinksapi.email.models;


import jakarta.mail.internet.MimeBodyPart;
import lombok.Builder;
import org.springframework.lang.NonNull;
import org.thymeleaf.context.Context;
import java.io.File;
import java.util.List;
import java.util.Map;

@Builder
public record CreateEmailRequest(
    @NonNull
    List<String> to,
    List<String> cc,
    @NonNull
    String subject,
    String htmlBody,
    String body,
    File[] attachments
) {

}
