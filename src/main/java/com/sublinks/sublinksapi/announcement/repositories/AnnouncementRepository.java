package com.sublinks.sublinksapi.announcement.repositories;

import com.sublinks.sublinksapi.announcement.entities.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

}
