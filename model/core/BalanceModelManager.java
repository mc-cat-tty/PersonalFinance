package model.core;

/**
 * Turns BalanceModel into a singleton.
 * @see Singleton design pattern
 */
public class BalanceModelManager extends BalanceModel {
  private static BalanceModel instance;
  
  private BalanceModelManager() { }

  /**
   * @return the unique instance of this object
   */
  public synchronized static BalanceModel getInstance() {
    if (instance == null) {
      instance = new BalanceModel();
    }

    return instance;
  }
}
