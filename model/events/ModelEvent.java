package model.events;

import model.core.*;
import java.util.*;

import javax.sql.rowset.spi.SyncResolver;

/**
 * Defines a generic event in the data model.
 * @see Observer design pattern
 */
public class ModelEvent implements IEvent<Transaction> {
  private final List<IObserver> observers;

  public ModelEvent() {
    observers = Collections.synchronizedList(new ArrayList<>());
  }

  /**
   * Add a new observer for this event
   * @param observer
   */
  public void attachObserver(IObserver observer) {
    synchronized (observers) {
      observers.add(observer);
    }
  }

  /**
   * Remove
   * @param observer
   */
  public void detachObserver(IObserver observer) {
    synchronized (observers) {
      observers.remove(observer);
    }
  }

  public void notifyAllObservers(Transaction change) {
    synchronized (observers) {
      for (final var o : observers) {
        o.accept(change);
      }
    }
  }
}
