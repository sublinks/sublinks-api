package com.fedilinks.fedilinksapi.authorization;

import com.fedilinks.fedilinksapi.authorization.enums.EntityType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private EntityType entityType;

    @Column(updatable = false, nullable = false, name = "entity_id")
    private Long entityId;

    @Column(updatable = false, nullable = false, name = "can_create")
    private boolean canCreate;

    @Column(updatable = false, nullable = false, name = "can_read")
    private boolean canRead;

    @Column(updatable = false, nullable = false, name = "can_update")
    private boolean canUpdate;

    @Column(updatable = false, nullable = false, name = "can_delete")
    private boolean canDelete;

    @CreationTimestamp
    @Column(updatable = false, nullable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(updatable = false, nullable = false, name = "updated_at")
    private Date updatedAt;
}
