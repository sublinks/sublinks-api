package com.sublinks.sublinksapi.media.respositories;

import com.sublinks.sublinksapi.media.entities.Media;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * MediaRepository is an interface that extends the JpaRepository interface for the Media entity. It provides methods to perform basic CRUD operations on Media objects.
 *
 * @see JpaRepository
 * @see Media
 */
public interface MediaRepository extends JpaRepository<Media, Long> {

}
