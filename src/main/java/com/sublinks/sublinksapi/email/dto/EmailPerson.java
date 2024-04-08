package com.sublinks.sublinksapi.email.dto;

import com.sublinks.sublinksapi.person.dto.Person;
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
@Table(name = "email_person_recipients")
public class EmailPerson {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne()
  @JoinColumn(name = "person_id")
  Person person;

  @ManyToOne()
  @JoinColumn(name = "email_id")
  Email email;
}
