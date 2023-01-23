package model.core;

public class BalanceModelManager extends BalanceModel {
  private static BalanceModel instance;
  
  private BalanceModelManager() { }

  public synchronized static BalanceModel getInstance() {
    if (instance == null) {
      instance = new BalanceModel();
    }

    return instance;
  }
}
