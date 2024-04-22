package com.sublinks.sublinksapi.email.entities;

import com.sublinks.sublinksapi.person.entities.Person;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "email")
public class Email {

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "email_person_recipients", joinColumns = @JoinColumn(name = "email_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
  private List<Person> personRecipients;

  @OneToMany(mappedBy = "email", fetch = FetchType.LAZY)
  private List<EmailData> emailData;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String subject;

  @Column(nullable = false, name = "html_content")
  private String htmlContent;

  @Column(nullable = false, name = "text_content")
  private String textContent;

  @Column(name = "last_try_at")
  private Date lastTryAt;

  @CreationTimestamp
  @Column(updatable = false, nullable = false, name = "created_at")
  private Date createdAt;
}
