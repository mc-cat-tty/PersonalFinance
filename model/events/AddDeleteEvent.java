package model.events;

import model.core.*;
import java.util.*;

/**
 * Model's add or delete events (and their derivatives)
 */
public class AddDeleteEvent extends Event<IAddDeleteObserver> {

  /**
   * Notify all the subscribed observer that a list of transactions has been added/removed.
   */
  public void notifyAllObservers(Collection<Transaction> changes) {
    synchronized (this.observers) {
      for (final var o : observers) {
        o.accept(changes);
      }
    }
  }
}
