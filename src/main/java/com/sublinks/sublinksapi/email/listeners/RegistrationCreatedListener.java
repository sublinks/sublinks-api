package com.sublinks.sublinksapi.email.listeners;

import com.sublinks.sublinksapi.email.enums.EmailTemplatesEnum;
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

    if (localInstanceContext.instance().getInstanceConfig() != null
        && !localInstanceContext.instance().getInstanceConfig().isApplicationEmailAdmins()) {
      return;
    }
    final Person person = event.getPersonRegistrationApplication().getPerson();

    Map<String, Object> properties = emailService.getDefaultEmailParameters();
    properties.put("person", person);
    properties.putAll(emailService.getDefaultPersonEmailParameters(person));

    Context context = new Context(Locale.getDefault(), properties);

    try {
      final String template_name = EmailTemplatesEnum.NEW_REGISTRATION_APPLICATION.toString();
      emailService.sendEmail(CreateEmailRequest.builder().to(List.of(person.getEmail()))
          .subject(emailService.getSubjects().get(template_name).getAsString())
          .body(emailService.formatTextEmailTemplate(template_name, context))
          .htmlBody(emailService.formatEmailTemplate(template_name, context))
          .build());
    } catch (Exception e) {
      // @todo: log error
    }
  }

}
