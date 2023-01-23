package ui.utils;

import java.util.*;

public class TimeTravelDateBuilder {
  private Date futureDate = new Date();

  public Date build() {
    return futureDate;
  }

  public TimeTravelDateBuilder setBaseDate(Date baseDate) {
    futureDate = baseDate;
    return this;
  }

  public TimeTravelDateBuilder addDays(int days) {
    final var calendar = Calendar.getInstance();
    calendar.setTime(futureDate);
    calendar.add(Calendar.DATE, days);
    futureDate = calendar.getTime();
    return this;
  }

  public TimeTravelDateBuilder addWeeks(int weeks) {
    return addDays(weeks*7);
  }

  public TimeTravelDateBuilder addMonths(int months) {
    final var calendar = Calendar.getInstance();
    calendar.setTime(futureDate);
    calendar.add(Calendar.MONTH, months);
    futureDate = calendar.getTime();
    return this;
  }

  public TimeTravelDateBuilder addYears(int years) {
    final var calendar = Calendar.getInstance();
    calendar.setTime(futureDate);
    calendar.add(Calendar.YEAR, years);
    futureDate = calendar.getTime();
    return this;
  }

  public TimeTravelDateBuilder toDayOfWeek(int day) {
    final var calendar = Calendar.getInstance();
    calendar.setTime(futureDate);
    calendar.set(Calendar.DAY_OF_WEEK, day);
    futureDate = calendar.getTime();
    return this;
  }

  public TimeTravelDateBuilder toDayOfMonth(int day) {
    final var calendar = Calendar.getInstance();
    calendar.setTime(futureDate);
    calendar.set(Calendar.DAY_OF_MONTH, day);
    futureDate = calendar.getTime();
    return this;
  }

  public TimeTravelDateBuilder toDayOfYear(int day) {
    final var calendar = Calendar.getInstance();
    calendar.setTime(futureDate);
    calendar.set(Calendar.DAY_OF_YEAR, day);
    futureDate = calendar.getTime();
    return this;
  }
}
