package com.sublinksapp.sublinksappapi.authorization;

import com.sublinksapp.sublinksappapi.authorization.enums.AuthorizeAction;
import com.sublinksapp.sublinksappapi.authorization.enums.AuthorizedEntityType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "acl")
public class Acl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, nullable = false, name = "person_id")
    private Long personId;

    @Column(updatable = false, nullable = false, name = "entity_type")
    @Enumerated(EnumType.STRING)
    private AuthorizedEntityType entityType;

    @Column(updatable = true, nullable = false, name = "entity_id")
    private Long entityId;

    @Column(updatable = true, nullable = false, name = "authorized_action")
    @Enumerated(EnumType.STRING)
    private AuthorizeAction authorizedAction;

    @Column(updatable = true, nullable = false, name = "is_permitted")
    private boolean permitted;

    @CreationTimestamp
    @Column(updatable = false, nullable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(updatable = false, nullable = false, name = "updated_at")
    private Date updatedAt;
}
