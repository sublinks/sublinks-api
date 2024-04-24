package com.sublinks.sublinksapi.post.sorts;

import com.sublinks.sublinksapi.person.enums.SortType;
import org.springframework.stereotype.Service;

@Service
public class SortFactory {

  public SortingTypeInterface createSort(SortType sortType) {

    return switch (sortType) {
      case Active -> new Active();
      case Hot -> new Hot();
      case New -> new New();
      case Old -> new Old();
      case TopDay -> new TopDay();
      case TopWeek -> new TopWeek();
      case TopMonth -> new TopMonth();
      case TopYear -> new TopYear();
      case TopAll -> new TopAll();
      case MostComments -> new MostComments();
      case NewComments -> new NewComments();
      case TopHour -> new TopHour();
      case TopSixHour -> new TopSixHour();
      case TopTwelveHour -> new TopTwelveHour();
      case TopThreeMonths -> new TopThreeMonths();
      case TopSixMonths -> new TopSixMonths();
      case TopNineMonths -> new TopNineMonths();
      case Controversial -> new Controversial();
      case Scaled -> new Scaled();
    };
  }
}
