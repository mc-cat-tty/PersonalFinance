package ui.utils;

import java.util.*;
import java.util.concurrent.Future;

public class FutureDateBuilder {
  private Date futureDate = new Date();

  public Date createFutureDate() {
    return futureDate;
  }

  public FutureDateBuilder setBaseDate(Date baseDate) {
    futureDate = baseDate;
    return this;
  }

  public FutureDateBuilder addDays(int days) {
    final var calendar = Calendar.getInstance();
    calendar.setTime(futureDate);
    calendar.add(Calendar.DATE, days);
    futureDate = calendar.getTime();
    return this;
  }

  public FutureDateBuilder addWeeks(int weeks) {
    return addDays(weeks*7);
  }

  public FutureDateBuilder addMonths(int months) {
    final var calendar = Calendar.getInstance();
    calendar.setTime(futureDate);
    calendar.add(Calendar.MONTH, months);
    futureDate = calendar.getTime();
    return this;
  }

  public FutureDateBuilder addYears(int years) {
    final var calendar = Calendar.getInstance();
    calendar.setTime(futureDate);
    calendar.add(Calendar.YEAR, years);
    futureDate = calendar.getTime();
    return this;
  }
}
