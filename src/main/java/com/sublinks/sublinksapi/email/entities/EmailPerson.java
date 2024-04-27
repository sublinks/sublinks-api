package com.sublinks.sublinksapi.email.entities;

import com.sublinks.sublinksapi.person.entities.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "email_person_recipients")
public class EmailPerson {


  @ManyToOne()
  @JoinColumn(name = "person_id")
  Person person;
  @ManyToOne()
  @JoinColumn(name = "email_id")
  Email email;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
}
