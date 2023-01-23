package model.events;

import model.core.*;
import java.util.*;

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
    observers.add(observer);
  }

  /**
   * Remve
   * @param observer
   */
  public void detachObserver(IObserver observer) {
    observers.remove(observer);
  }

  public void notifyAllObservers(Transaction change) {
    for (var o : observers) {
      o.accept(change);
    }
  }
}
