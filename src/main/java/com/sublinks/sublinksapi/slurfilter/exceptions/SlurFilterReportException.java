package com.sublinks.sublinksapi.slurfilter.exceptions;

import com.sublinks.sublinksapi.slurfilter.entities.SlurFilter;
import lombok.Getter;

@Getter
public class SlurFilterReportException extends Exception {

  private SlurFilter slurFilter;

  public SlurFilterReportException(String message) {

    super(message);
  }

  public SlurFilterReportException(String message, SlurFilter slurFilter) {

    super(message);
    this.slurFilter = slurFilter;
  }
}
