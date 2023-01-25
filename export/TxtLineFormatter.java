package export;

import model.core.*;
import tunable.*;

public class TxtLineFormatter implements ILineFormatter {
  private static final char SEPARATOR = '\t';
  
  public String transform(Transaction transaction) {
    return Float.toString(transaction.getAmount())
      + SEPARATOR
      + CommonDateFormats.EU_DATE_FORMAT_LONG.getFormatter().format(
        transaction.getDate()
      )
      + SEPARATOR
      + "\"" + transaction.getDescription().strip() + "\""
      + '\n';
  }
}
