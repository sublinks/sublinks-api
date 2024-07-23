package com.sublinks.sublinksapi.person.scheduling;

import com.sublinks.sublinksapi.authorization.entities.Role;
import com.sublinks.sublinksapi.authorization.services.RoleService;
import com.sublinks.sublinksapi.person.repositories.LinkPersonCommunityRepository;
import com.sublinks.sublinksapi.person.repositories.PersonRepository;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import com.sublinks.sublinksapi.person.services.PersonService;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class ExpiredRoleScheduler {

  private final Logger logger = Logger.getLogger(ExpiredRoleScheduler.class.getName());
  private final PersonRepository personRepository;
  private final RoleService roleService;
  private final LinkPersonCommunityRepository linkPersonCommunityRepository;
  private final LinkPersonCommunityService linkPersonCommunityService;
  private final PersonService personService;

  @Scheduled(fixedRateString = "${sublinks.settings.check_expired_instance_roles}",
      timeUnit = TimeUnit.SECONDS)
  public void clearInstanceRoles() {

    Optional<Role> foundRegisteredRole = roleService.getDefaultRegisteredRole();

    if (foundRegisteredRole.isEmpty()) {

      logger.warning(
          "Default registered role not found. (Expected if it is your initial start of the application.)");
      return;
    }

    Role registeredRole = foundRegisteredRole.get();

    personRepository.findAllByRoleExpireAtBefore(new Date())
        .forEach(person -> {
          person.setRole(registeredRole);
          person.setRoleExpireAt(null);
          personService.updatePerson(person);
        });
  }

  @Scheduled(fixedRateString = "${sublinks.settings.check_expired_community_roles}",
      timeUnit = TimeUnit.SECONDS)
  public void clearCommunityRoles() {

    linkPersonCommunityRepository.getLinkPersonCommunitiesByExpireAtBefore(new Date())
        .forEach(linkPersonCommunity -> {
          linkPersonCommunityService.removeLink(linkPersonCommunity.getPerson(),
              linkPersonCommunity.getCommunity(), linkPersonCommunity.getLinkType());
        });
  }
}
