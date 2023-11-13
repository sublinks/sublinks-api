package com.sublinks.sublinksapi.private_messages.dto;

import com.sublinks.sublinksapi.authorization.AuthorizationEntityInterface;
import com.sublinks.sublinksapi.authorization.enums.AuthorizedEntityType;
import com.sublinks.sublinksapi.person.dto.Person;
import jakarta.persistence.Column;
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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "private_messages")
public class PrivateMessage implements AuthorizationEntityInterface {
    /**
     * Relationships
     */
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    Person recipient;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    Person sender;

    /**
     * Attributes
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "content")
    private String content;

    @Column(nullable = false, name = "is_local")
    private boolean isLocal;

    @Column(nullable = false, name = "is_read")
    private boolean isRead;

    @Column(nullable = false, name = "is_deleted")
    private boolean isDeleted;

    @CreationTimestamp
    @Column(updatable = false, nullable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(updatable = false, nullable = false, name = "updated_at")
    private Date updatedAt;

    @Column(nullable = false, name = "activity_pub_id")
    private String activityPubId;

    @Override
    public AuthorizedEntityType entityType() {
        return AuthorizedEntityType.message;
    }
}
