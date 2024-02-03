package com.sublinks.sublinksapi.email.listeners;

import com.sublinks.sublinksapi.email.models.CreateEmailRequest;
import com.sublinks.sublinksapi.email.services.EmailService;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.events.PersonRegistrationApplicationCreatedEvent;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component
@AllArgsConstructor
public class RegistrationCreatedListener implements
    ApplicationListener<PersonRegistrationApplicationCreatedEvent> {

  private final LocalInstanceContext localInstanceContext;
  private final EmailService emailService;

  @Override
  public void onApplicationEvent(PersonRegistrationApplicationCreatedEvent event) {

    final Person person = event.getPersonRegistrationApplication().getPerson();

    Map<String, Object> properties = emailService.getDefaultEmailParameters();
    properties.put("person", person);
    properties.putAll(emailService.getDefaultPersonEmailParameters(person));

    Context context = new Context(Locale.getDefault(), properties);

    try {
      emailService.sendEmail(CreateEmailRequest.builder().to(List.of(person.getEmail()))
          .subject(emailService.getSubjects().get("new_registration_application").getAsString())
          .body(emailService.formatTextEmailTemplate("new_registration_application", context))
          .htmlBody(emailService.formatEmailTemplate("new_registration_application", context))
          .build());
    } catch (Exception e) {
      // @todo: log error
    }
  }

}
