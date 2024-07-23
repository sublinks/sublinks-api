package com.sublinks.sublinksapi.common.interfaces;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public interface ILinkingService<LLink, LEntity, LPerson, LType> {

  /***
   * Check if a link exists between an entity and a person
   * @param entity the entity
   * @param person the person
   * @param type the type of the link
   * @return true if the link exists, false otherwise
   */
  boolean hasLink(final LEntity entity, final LPerson person, final LType type);

  boolean hasAnyLink(final LEntity entity, final LPerson person, final List<LType> types);

  @Transactional
  void createLink(final LLink link);

  @Transactional
  void createLinks(final List<LLink> links);

  @Transactional
  void updateLink(final LLink link);

  @Transactional
  void updateLinks(final List<LLink> links);

  @Transactional
  void deleteLink(final LLink link);

  @Transactional
  void deleteLinks(final List<LLink> links);

  Optional<LLink> getLink(final LEntity entity, final LPerson person, final LType type);

  List<LLink> getLinks(final LPerson person);

  List<LLink> getLinks(final LPerson person, final LType type);


  List<LLink> getLinksByEntity(final LEntity entity);
}
