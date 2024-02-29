package com.sublinks.sublinksapi.api.lemmy.v3.authentication;

import com.sublinks.sublinksapi.person.dto.Person;
import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtPerson extends AbstractAuthenticationToken {

  private final Person person;

  public JwtPerson(final Person person, final Collection<? extends GrantedAuthority> authorities) {

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
