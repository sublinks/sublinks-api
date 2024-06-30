package com.sublinks.sublinksapi.api.sublinks.v1.authentication;

import com.sublinks.sublinksapi.person.entities.Person;
import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class SublinksJwtPerson extends AbstractAuthenticationToken {

  private final Person person;

  public SublinksJwtPerson(final Person person,
      final Collection<? extends GrantedAuthority> authorities)
  {

    super(authorities);
    this.person = person;
    setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {

    return null;
  }

  @Override
  public Object getPrincipal() {

    return this.person;
  }
}
