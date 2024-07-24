package com.sublinks.sublinksapi.person.entities;

import com.sublinks.sublinksapi.person.enums.LinkPersonPersonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.proxy.HibernateProxy;

/**
 * The LinkPersonPerson class represents a link between two Person objects. It stores information
 * about the link type, creation timestamp, and the two Person objects involved in the link. The
 * class provides methods for accessing and modifying the attributes, as well as methods for
 * equality comparison, hashcode calculation, and cloning.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "link_person_person")
public class LinkPersonPerson {

  @ManyToOne
  @JoinColumn(name = "from_person_id")
  Person fromPerson;

  @ManyToOne
  @JoinColumn(name = "to_person_id")
  Person toPerson;
  /**
   * Attributes.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, name = "link_type")
  @Enumerated(EnumType.STRING)
  private LinkPersonPersonType linkType;

  @CreationTimestamp(source = SourceType.DB)
  @Column(updatable = false, nullable = false, name = "created_at")
  private Date createdAt;

  @Override
  public final boolean equals(Object o) {

    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    Class<?> objectEffectiveClass =
        o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer()
            .getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass =
        this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
            .getPersistentClass() : this.getClass();
    if (thisEffectiveClass != objectEffectiveClass) {
      return false;
    }
    LinkPersonPerson post = (LinkPersonPerson) o;
    return getId() != null && Objects.equals(getId(), post.getId());
  }

  @Override
  public final int hashCode() {

    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass()
        .hashCode() : getClass().hashCode();
  }

  @Override
  public LinkPersonPerson clone() {

    try {
      LinkPersonPerson clone = (LinkPersonPerson) super.clone();
      // TODO: copy mutable state here, so the clone can't change the internals of the original
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
