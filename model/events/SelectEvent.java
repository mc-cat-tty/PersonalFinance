package model.events;

import model.core.*;

/**
 * Select event on the view.
 */
public class SelectEvent extends Event<ISelectObserver> {

  /**
   * Notify all the subscribed observer a Transaction has been selected from the search bar.
   * @param selection selected transaction
   */
  public void notifyAllObservers(Transaction selection) {
    synchronized (observers) {
      for (final var o : observers) {
        o.accept(selection);
      }
    }
  }
}
