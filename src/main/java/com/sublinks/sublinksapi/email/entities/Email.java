package com.sublinks.sublinksapi.email.entities;

import com.sublinks.sublinksapi.person.entities.Person;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * The Email class represents an email object.
 *
 * <p>It has the following attributes:
 * - personRecipients: a list of Person objects representing the recipients of the email
 * - emailData: a list of EmailData objects representing the data of the email
 * - id: a unique identifier for the email
 * - subject: a string representing the subject of the email
 * - htmlContent: a string representing the HTML content of the email
 * - textContent: a string representing the text content of the email
 * - lastTryAt: a Date object representing the last attempted sending time of the email
 * - createdAt: a Date object representing the creation time of the email
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "email")
public class Email {

  @ManyToMany(fetch = FetchType.EAGER)
  @Fetch(FetchMode.SUBSELECT)
  @JoinTable(name = "email_person_recipients", joinColumns = @JoinColumn(name = "email_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
  private List<Person> personRecipients;

  @OneToMany(mappedBy = "email", fetch = FetchType.EAGER)
  @Fetch(FetchMode.SUBSELECT)
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
