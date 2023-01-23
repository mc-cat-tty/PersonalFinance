package ui.behaviour;

import tunable.*;
import ui.utils.*;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.*;

import javax.xml.stream.util.EventReaderDelegate;

import java.text.*;

public enum SearchPeriods {
  DAY (
    new Date(),
    d -> d,
    d -> d,
    CommonDateFormats.EU_DATE_FORMAT_LONG.getFormatter()
  ),
  WEEK (
    new Date(),
    d -> new TimeTravelDateBuilder().setBaseDate(d).addWeeks(-1).addDays(1).build(),
    d -> d,
    CommonDateFormats.EU_DATE_FORMAT_LONG.getFormatter()
  ),
  MONTH (
    new Date(),
    d -> new TimeTravelDateBuilder().setBaseDate(d).toDayOfMonth(1).build(),
    d -> new TimeTravelDateBuilder().setBaseDate(d).toDayOfMonth(1).addMonths(1).addDays(-1).build(),
    CommonDateFormats.MONTH_FORMAT_LONG.getFormatter()
  ),
  YEAR (
    new Date(),
    d -> new TimeTravelDateBuilder().setBaseDate(d).toDayOfYear(1).build(),
    d -> new TimeTravelDateBuilder().setBaseDate(d).toDayOfYear(1).addDays(364).build(),
    CommonDateFormats.YEAR_FORMAT_LONG.getFormatter()
  ),
  CUSTOM (
    new Date(),
    d -> new TimeTravelDateBuilder().setBaseDate(d).addWeeks(-3).build(),
    d -> d,
    CommonDateFormats.EU_DATE_FORMAT_LONG.getFormatter()
  );

  private final String mockStartText;
  private final String mockEndText;
  private final SimpleDateFormat formatter;
  private final UnaryOperator<Date> toStartDate;
  private final UnaryOperator<Date> toEndDate;

  private SearchPeriods(
    Date baseDate,
    UnaryOperator<Date> toStartDate,
    UnaryOperator<Date> toEndDate,
    SimpleDateFormat formatter
  ) {
    final var startDate = toStartDate.apply(baseDate);
    final var endDate = toEndDate.apply(baseDate);

    this.mockEndText = formatter.format(endDate);
    this.mockStartText = formatter.format(startDate);
    this.toStartDate = toStartDate;
    this.toEndDate = toEndDate;
    this.formatter = formatter;
  }

  public String getMockStartText() {
    return mockStartText;
  }

  public String getMockEndText() {
    return mockEndText;
  }

  public SimpleDateFormat getFormatter() {
    return formatter;
  }

  public Date toEndDate(Date baseDate) {
    return toEndDate.apply(baseDate);
  }

  public Date toStartDate(Date baseDate) {
    return toStartDate.apply(baseDate);
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