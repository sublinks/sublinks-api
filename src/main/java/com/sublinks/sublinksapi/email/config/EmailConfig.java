package com.sublinks.sublinksapi.email.config;

import java.util.Properties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@Getter
@NoArgsConstructor
public class EmailConfig {

  @Value("${sublinks.settings.email.enabled}")
  private boolean enabled;

  @Value("${sublinks.settings.email.sender}")
  private String sender;

  @Value("${sublinks.settings.email.sender_name}")
  private String senderName;

  @Value("${sublinks.settings.email.host}")
  private String host;

  @Value("${sublinks.settings.email.port}")
  private int port;

  @Value("${sublinks.settings.email.smtp.username}")
  private String smtpUser;

  @Value("${sublinks.settings.email.smtp.password}")
  private String smtpPassword;

  @Value("${sublinks.settings.email.tls}")
  private boolean smtpTlsEnable;

  @Value("${sublinks.settings.email.ssl}")
  private boolean smtpSslEnable;

  @Value("${sublinks.settings.email.trusted}")
  private String smtpSslTrust;

  @Value("${sublinks.settings.email.starttls}")
  private boolean smtpStartTls;

  @Value("${sublinks.settings.email.starttls.required}")
  private boolean smtpStartTlsRequired;

  @Value("${sublinks.settings.email.debug}")
  private boolean debug;


  @Bean
  public JavaMailSender getJavaMailSender() {

    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(host);
    mailSender.setPort(port);

    mailSender.setUsername(smtpUser);
    mailSender.setPassword(smtpPassword);

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", smtpStartTls);
    props.put("mail.smtp.starttls.required", smtpStartTlsRequired);
    props.put("mail.smtp.ssl.enable", smtpSslEnable);
    props.put("mail.smtp.ssl.trust", smtpSslTrust);
    props.put("mail.debug", debug);

    return mailSender;
  }

  @Primary
  @Bean
  public ITemplateResolver templateResolver() {

    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setPrefix("email-templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode("HTML");
    templateResolver.setCharacterEncoding("UTF-8");
    return templateResolver;
  }

  @Primary
  @Bean
  public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {

    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);
    return templateEngine;
  }

  @Bean(name = "textTemplateResolver", autowireCandidate = false)
  public ITemplateResolver textTemplateResolver() {

    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setPrefix("email-templates/");
    templateResolver.setSuffix(".txt");
    templateResolver.setTemplateMode("TEXT");
    templateResolver.setCharacterEncoding("UTF-8");
    return templateResolver;
  }

  @Bean(name = "textTemplateEngine", autowireCandidate = false)
  public SpringTemplateEngine textTemplateEngine(ITemplateResolver textTemplateResolver) {

    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(textTemplateResolver);
    return templateEngine;
  }
}
