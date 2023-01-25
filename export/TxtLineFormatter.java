package export;

import model.core.*;
import tunable.*;

/**
 * Concrete formatter for TXT files.
 */
public class TxtLineFormatter implements IRecordFormatter {
  private static final char SEPARATOR = '\t';
  
  /**
   * Takes a transaction and separates fields with a tab.
   * @param transaction input transaction
   * @return a string of tab separated values
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
