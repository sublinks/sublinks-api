package com.sublinks.sublinksapi.slurfilter.exceptions;

import com.sublinks.sublinksapi.slurfilter.entities.SlurFilter;
import lombok.Getter;

@Getter
public class SlurFilterBlockedException extends Exception {

  private SlurFilter slurFilter;

  public SlurFilterBlockedException(String message) {

    super(message);
  }

  public SlurFilterBlockedException(String message, SlurFilter slurFilter) {

    super(message);
    this.slurFilter = slurFilter;
  }
}
