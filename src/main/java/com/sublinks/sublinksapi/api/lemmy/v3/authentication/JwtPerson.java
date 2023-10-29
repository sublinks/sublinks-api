package com.sublinks.sublinksapi.api.lemmy.v3.authentication;

import com.sublinks.sublinksapi.person.Person;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtPerson extends AbstractAuthenticationToken {
    private final Person person;

    public JwtPerson(final Person person, final Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.person = person;
        setAuthenticated(false);
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
