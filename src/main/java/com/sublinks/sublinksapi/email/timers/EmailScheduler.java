package com.sublinks.sublinksapi.email.timers;

import com.sublinks.sublinksapi.email.config.EmailConfig;
import com.sublinks.sublinksapi.email.dto.Email;
import com.sublinks.sublinksapi.email.repositories.EmailRepository;
import com.sublinks.sublinksapi.email.services.EmailService;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.Date;
import java.util.Optional;
import jakarta.mail.MessagingException;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@EnableScheduling
@Configuration
public class EmailScheduler {

  private final EmailService emailService;
  private final EmailConfig emailConfig;
  private final EmailRepository emailRepository;
  private final TaskScheduler taskScheduler;

  public EmailScheduler(EmailService emailService, EmailConfig emailConfig,
      EmailRepository emailRepository, TaskScheduler taskScheduler) {

    this.emailService = emailService;
    this.emailConfig = emailConfig;
    this.emailRepository = emailRepository;
    this.taskScheduler = taskScheduler;

    this.taskScheduler.scheduleAtFixedRate(new Runnable() {
      @Override
      @Transactional
      public void run() {

        EmailScheduler.this.sendEmail();
      }
    }, Duration.ofSeconds(60 / this.emailConfig.getDelivery_rate_per_minute()));
  }

  @Transactional
  public void sendEmail() {

    Optional<Email> email = emailRepository.findFirstByOrderByLastTryAtAsc();
    if (email.isEmpty()) {
      return;
    }
    Email emailToSend = email.get();
    try {
      emailService.sendEmail(emailToSend);
      emailRepository.delete(emailToSend);
    } catch (MessagingException | MailException e) {
      System.out.println("Error sending email: " + e.getMessage());
      System.out.println("Email Server error, retrying...");
      emailToSend.setLastTryAt(new Date());

      emailService.saveToQueue(emailToSend);
    } catch (UnsupportedEncodingException e) {
      System.out.println("Error sending email: " + e.getMessage());
      System.out.println("Email Message malformed, check your email template, not retrying...");
    }
  }
}
