package com.sublinks.sublinksapi.utils.models;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class LemmyException extends Exception{
  protected HttpStatusCode status = HttpStatusCode.valueOf(500);

  public LemmyException(String message) {
    super(message);
  }

  public LemmyException(String message, HttpStatusCode status) {
    super(message);
    this.status = status;
  }

  public LemmyException(String message, Throwable cause) {
    super(message, cause);
  }

  public Object getException() {
    String message = super.getMessage();

    if (message == null) {
      message = "An error occurred";
    }

    Map<String, String> error = new HashMap<>();

    error.put("error", message);

    return error;
  }
}