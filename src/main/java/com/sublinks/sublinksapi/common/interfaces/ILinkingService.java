package com.sublinks.sublinksapi.common.interfaces;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public interface ILinkingService<LLink, LEntity, LPerson, LType> {

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

  void deleteLink(final LEntity entity, final LPerson person, final LType type);

  @Transactional
  void deleteLinks(final List<LLink> links);

  Optional<LLink> getLink(final LEntity entity, final LPerson person, final LType type);

  List<LLink> getLinks(final LPerson person);

  List<LLink> getLinks(final LPerson person, final LType type);

  List<LLink> getLinks(final LPerson person, final List<LType> types);

  List<LLink> getLinksByEntity(final LEntity entity, final LPerson person);

  List<LLink> getLinksByEntity(final LEntity entity, final List<LType> types);

  List<LLink> getLinksByEntity(final LEntity entity);
}
