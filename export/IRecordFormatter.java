package export;

import model.core.*;

/**
 * Interface that defines the behavior of a general record formatter.
 */
public interface IRecordFormatter {
  /**
   * @param transaction input transaction
   * @return apply the transformation and return the result
   */
  public String transform(Transaction transaction);
}
