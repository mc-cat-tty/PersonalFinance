package export;

import model.core.*;
import tunable.*;

/**
 * Concrete formatter for CSV specification.
 */
public class CsvLineFormatter implements IRecordFormatter {
  private static final char SEPARATOR = ',';
  
  /**
   * Apply a transformation to the input transaction.
   * @param transaction input transaction
   * @return a string of comma separated fields and newline-terminated
   */
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
