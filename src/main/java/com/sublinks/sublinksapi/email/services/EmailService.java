package com.sublinks.sublinksapi.email.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sublinks.sublinksapi.email.config.EmailConfig;
import com.sublinks.sublinksapi.email.dto.Email;
import com.sublinks.sublinksapi.email.events.EmailCreatedPublisher;
import com.sublinks.sublinksapi.email.models.CreateEmailEvent;
import com.sublinks.sublinksapi.email.repositories.EmailRepository;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.utils.FileUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
@RequiredArgsConstructor
public class EmailService {

  private final EmailConfig emailConfig;
  private final LocalInstanceContext localInstanceContext;
  private final SpringTemplateEngine templateEngine;
  private final SpringTemplateEngine textTemplateEngine;
  private final EmailCreatedPublisher emailCreatedPublisher;
  private final EmailRepository emailRepository;

  private JavaMailSender getMailSender() {

    return emailConfig.getJavaMailSender();
  }

  public void saveToQueue(Email request) {

    emailRepository.save(request);
  }

  public void sendEmail(@NonNull Email request)
      throws MessagingException, UnsupportedEncodingException {

    if (!emailConfig.isEnabled()) {
      return;
    }
    if (request.getPersonRecipients().isEmpty() && request.getEmailData().isEmpty()) {
      return;
    }

    JavaMailSender sender = getMailSender();

    final ArrayList<String> recipients = new ArrayList<>();
    request.getPersonRecipients().forEach(person -> {
      if (person.getEmail() != null && !person.getEmail().isEmpty()) {
        recipients.add(person.getEmail());
      }
    });

    request.getEmailData().forEach(emailData -> {
      if (emailData.getRecipient() != null && !emailData.getRecipient().isEmpty()) {
        recipients.add(emailData.getRecipient());
      }
    });

    final MimeMessage msg = sender.createMimeMessage();

    MimeMessageHelper helper = new MimeMessageHelper(msg, true);

    helper.setFrom(emailConfig.getSender(), emailConfig.getSenderName());
    helper.setTo(recipients.toArray(new String[0]));
    helper.setSubject(request.getSubject());
    helper.setText(request.getTextContent(), request.getHtmlContent());

    getMailSender().send(msg);

    emailCreatedPublisher.publish(CreateEmailEvent.builder().email(request).build());

  }

  public Map<String, Object> getDefaultEmailParameters() {

    Map<String, Object> params = new HashMap<>();

    params.put("instance", localInstanceContext.instance());
    params.put("instanceName", localInstanceContext.instance().getName());
    params.put("instanceDomain", localInstanceContext.instance().getDomain());
    params.put("instanceIconUrl", localInstanceContext.instance().getIconUrl());
    params.put("instanceBannerUrl", localInstanceContext.instance().getBannerUrl());
    params.put("instanceDescription", localInstanceContext.instance().getDescription());
    params.put("instanceSidebar", localInstanceContext.instance().getSidebar());
    return params;
  }

  public String formatEmailTemplate(final String template, @Nullable IContext params) {

    if (params == null) {
      params = new Context(Locale.getDefault(), getDefaultEmailParameters());
    }

    return templateEngine.process(template, params);
  }

  public String formatTextEmailTemplate(final String template, @Nullable IContext params) {

    if (params == null) {
      params = new Context(Locale.getDefault(), getDefaultEmailParameters());
    }

    return textTemplateEngine.process(template, params);
  }

  public JsonObject getSubjects() throws IOException {

    File subjectsJson = ResourceUtils.getFile("classpath:email-templates/subjects.json");

    String data = FileUtils.readFromInputStream(new FileInputStream(subjectsJson));
    return new Gson().fromJson(data, JsonObject.class);
  }

  public Map<String, Object> getDefaultPersonEmailParameters(final Person person) {

    Map<String, Object> params = getDefaultEmailParameters();

    params.put("person", person);
    params.put("userName", person.getName());
    params.put("userId", person.getId());
    params.put("userEmail", person.getEmail());
    params.put("userAvatar", person.getAvatarImageUrl());
    params.put("userBanner", person.getBannerImageUrl());
    params.put("userBio", person.getBiography());

    return params;
  }
}
