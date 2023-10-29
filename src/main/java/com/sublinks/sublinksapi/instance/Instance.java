package com.sublinks.sublinksapi.instance;

import com.sublinks.sublinksapi.community.Community;
import com.sublinks.sublinksapi.language.Language;
import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.post.Post;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "instances")
public class Instance {
    /**
     * Relationships
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instance")
    @PrimaryKeyJoinColumn
    private List<Community> communities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instance")
    @PrimaryKeyJoinColumn
    private List<Person> people;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instance")
    @PrimaryKeyJoinColumn
    private List<Post> posts;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private InstanceAggregate instanceAggregate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "instance_languages",
            joinColumns = @JoinColumn(name = "instance_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private List<Language> languages;

    /**
     * Attributes
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "activity_pub_id")
    private String activityPubId;

    @Column(nullable = false)
    private String domain;

    @Column(nullable = false)
    private String software;

    @Column(nullable = false)
    private String version;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String sidebar;

    @Column(nullable = false, name = "icon_url")
    private String iconUrl;

    @Column(nullable = false, name = "banner_url")
    private String bannerUrl;

    @Column(nullable = false, name = "public_key")
    private String publicKey;

    @Column(nullable = true, name = "private_key")
    private String privateKey;

    @CreationTimestamp
    @Column(nullable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(nullable = false, name = "updated_at")
    private Date updatedAt;
}
