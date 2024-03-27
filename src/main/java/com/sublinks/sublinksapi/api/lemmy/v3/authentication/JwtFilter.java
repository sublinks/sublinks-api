package com.sublinks.sublinksapi.api.lemmy.v3.authentication;

import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.repositories.PersonRepository;
import com.sublinks.sublinksapi.person.services.UserDataService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Order(1)
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final PersonRepository personRepository;
  private final UserDataService userDataService;

  @Override
  protected void doFilterInternal(final HttpServletRequest request,
      final HttpServletResponse response, final FilterChain filterChain)
      throws ServletException, IOException {

    String authorizingToken = request.getHeader("authorization");

    if (authorizingToken == null && request.getCookies() != null) {
      for (Cookie cookie : request.getCookies()) {
        if (cookie.getName().equals("jwt")) {
          authorizingToken = cookie.getValue();
          break;
        }
      }

    }

    String token = null;
    String userName = null;

    try {
      if (authorizingToken != null) {
        if (authorizingToken.startsWith("Bearer ")) {
          token = authorizingToken.substring(7);
        } else {
          token = authorizingToken;
        }
        userName = jwtUtil.extractUsername(token);
      }
    } catch (ExpiredJwtException | SignatureException ex) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "invalid_token");
    }

    if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      final Optional<Person> person = personRepository.findOneByName(userName);
      if (person.isEmpty()) {
        throw new UsernameNotFoundException("Invalid name");
      }

      if (jwtUtil.validateToken(token, person.get())) {
        userDataService.checkAndAddIpRelation(person.get(), request.getRemoteAddr(),
            request.getHeader("User-Agent"));
        final JwtPerson authenticationToken = new JwtPerson(person.get(),
            person.get().getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    }
    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

    return !request.getServletPath().startsWith("/api/v3");
  }
}
