package com.sublinks.sublinksapi.person.dto;

import com.sublinks.sublinksapi.instance.dto.Instance;
import com.sublinks.sublinksapi.person.enums.LinkPersonInstanceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "link_person_instances")
public class LinkPersonInstance {
    /**
     * Relationships
     */
    @OneToOne
    @JoinColumn(name = "person_id")
    Person person;

    @OneToOne
    @JoinColumn(name = "instance_id")
    Instance instance;

    /**
     * Attributes
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "link_type")
    @Enumerated(EnumType.STRING)
    private LinkPersonInstanceType linkType;

    @CreationTimestamp
    @Column(updatable = false, nullable = false, name = "created_at")
    private Date createdAt;
}
