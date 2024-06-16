package com.sublinks.sublinksapi.api.sublinks.v1.annoucement.services;

import com.sublinks.sublinksapi.announcement.entities.Announcement;
import com.sublinks.sublinksapi.announcement.repositories.AnnouncementRepository;
import com.sublinks.sublinksapi.api.sublinks.v1.annoucement.models.AnnouncementResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.annoucement.models.IndexAnnouncement;
import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SortOrder;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionInstanceTypes;
import com.sublinks.sublinksapi.authorization.services.RolePermissionService;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.person.entities.Person;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class SublinksAnnouncementService {

  private final LocalInstanceContext localInstanceContext;
  private final ConversionService conversionService;
  private final RolePermissionService rolePermissionService;
  private final AnnouncementRepository announcementRepository;

  public List<AnnouncementResponse> index(final IndexAnnouncement indexAnnouncementForm,
      final Person person)
  {

    rolePermissionService.isPermitted(person,
        RolePermissionInstanceTypes.INSTANCE_READ_ANNOUNCEMENTS,
        () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "forbidden_to_read_announcements"));

    final List<Announcement> announcements = this.announcementRepository.findAll(
            PageRequest.of(indexAnnouncementForm.page(), indexAnnouncementForm.perPage(), Sort.by(
                indexAnnouncementForm.sortOrder() == SortOrder.Desc ? Direction.DESC : Direction.ASC,
                "createdAt")))
        .toList();

    return announcements.stream()
        .map((announcement) -> this.conversionService.convert(announcement,
            AnnouncementResponse.class))
        .toList();
  }

  public AnnouncementResponse show(final Long key, final Person person) {

    rolePermissionService.isPermitted(person,
        RolePermissionInstanceTypes.INSTANCE_READ_ANNOUNCEMENT,
        () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "forbidden_to_read_announcement"));

    try {
      final Announcement announcement = this.announcementRepository.getReferenceById(key);

      return this.conversionService.convert(announcement, AnnouncementResponse.class);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "announcement_not_found");
    }
  }


}
