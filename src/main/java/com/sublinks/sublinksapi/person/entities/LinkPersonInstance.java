package com.sublinks.sublinksapi.person.entities;

import com.sublinks.sublinksapi.instance.entities.Instance;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

/**
 * The `LinkPersonInstance` class represents a link between a `Person` and an `Instance`.
 *
 * <p>Relationships:</p>
 * <ul>
 *   <li>{@link Person} - Represents the person related to this link.</li>
 *   <li>{@link Instance} - Represents the instance related to this link.</li>
 * </ul>
 *
 * <p>Attributes:</p>
 * <ul>
 *   <li>{@code id} - The unique identifier for this link.</li>
 *   <li>{@code createdAt} - The date when this link was created.</li>
 * </ul>
 *
 * <p>Usage:</p>
 * <pre>{@code
 *    // Creating a new link person instance
 *    LinkPersonInstance link = new LinkPersonInstance();
 *    link.setPerson(person);
 *    link.setInstance(instance);
 * }</pre>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "link_person_instances")
public class LinkPersonInstance {

  /**
   * Relationships.
   */
  @OneToOne
  @JoinColumn(name = "person_id")
  Person person;

  @OneToOne
  @JoinColumn(name = "instance_id")
  Instance instance;

  /**
   * Attributes.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreationTimestamp
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
    LinkPersonInstance that = (LinkPersonInstance) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {

    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass().hashCode() : getClass().hashCode();
  }
}
