package tunable;

import java.text.SimpleDateFormat;

import java.util.*;

public enum CommonDateFormats {
  EU_DATE_FORMAT_LONG ("dd/MM/yyyy"),
  US_DATE_FORMAT_LONG ("MM/dd/yyyy"),
  EU_DATE_FORMAT_SHORT ("dd/MM/YY"),
  US_DATE_FORMAT_SHORT ("MM/dd/YY");

  private final SimpleDateFormat formatter;

  private CommonDateFormats(String formatString) {
    formatter = new SimpleDateFormat(formatString);
  }

  public SimpleDateFormat getFormatter() {
    return formatter;
  }
}
