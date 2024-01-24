package com.sublinks.sublinksapi.interceptors;

import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.services.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimiter implements HandlerInterceptor {
  final private PersonService personService;

  @Autowired
  public RateLimiter(PersonService personService) {
    this.personService = personService;
  }

  @Override
  public boolean preHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler
  ) throws Exception {
    String ipAddress = request.getRemoteAddr();
    System.out.println("IP: " + ipAddress);
    return true;
  }
}
