package ui.behaviour;

import tunable.*;
import ui.utils.*;

import java.util.*;
import java.util.stream.*;
import java.text.*;

public enum SearchPeriods {
  DAY (
    new Date(),
    new Date(),
    CommonDateFormats.EU_DATE_FORMAT_LONG.getFormatter()
  ),
  WEEK (
    new FutureDateBuilder().setBaseDate(new Date()).addWeeks(-1).createFutureDate(),
    new Date(),
    CommonDateFormats.EU_DATE_FORMAT_LONG.getFormatter()
  ),
  MONTH (
    new FutureDateBuilder().setBaseDate(new Date()).addMonths(-1).createFutureDate(),
    new Date(),
    CommonDateFormats.MONTH_FORMAT_LONG.getFormatter()
  ),
  YEAR (
    new Date(),
    new Date(),
    CommonDateFormats.YEAR_FORMAT_LONG.getFormatter()
  ),
  CUSTOM (
    new FutureDateBuilder().setBaseDate(new Date()).addWeeks(-3).createFutureDate(),
    new Date(),
    CommonDateFormats.EU_DATE_FORMAT_LONG.getFormatter()
  );

  private final String mockStartText;
  private final String mockEndText;

  private SearchPeriods(
    Date mockStartDate,
    Date mockEndDate,
    SimpleDateFormat formatter
  ) {
    mockStartText = formatter.format(mockStartDate);
    mockEndText = formatter.format(mockEndDate);
  }

  public String getMockStartText() {
    return mockStartText;
  }

  public String getMockEndText() {
    return mockEndText;
  }

  public String toString() {
    final var name = name();
    return
      name.substring(0, 1).toUpperCase() +
      name.substring(1).toLowerCase();
  }

  public static String[] getStrings() {
    return
      Stream.of(SearchPeriods.values())
      .map(SearchPeriods::toString)
      .toArray(String[]::new);
  }
}