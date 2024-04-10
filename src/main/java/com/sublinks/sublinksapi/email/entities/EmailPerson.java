package com.sublinks.sublinksapi.email.entities;

import com.sublinks.sublinksapi.person.entities.Person;
import jakarta.persistence.*;
import lombok.*;

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
