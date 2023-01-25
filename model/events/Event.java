package model.events;

import java.util.*;

/**
 * Defines a generic event that can be observed.
 * Observer design pattern
 */
public abstract class Event<IObserver> {
  protected final List<IObserver> observers;

  public Event() {
    observers = Collections.synchronizedList(new ArrayList<>());
  }

  /**
   * Add a new observer for this event
   * @param observer observer to remove
   */
  public void attachObserver(IObserver observer) {
    synchronized (observers) {
      observers.add(observer);
    }
  }

  /**
   * Remove the observer
   * @param observer observer to remove
   */
  public void detachObserver(IObserver observer) {
    synchronized (observers) {
      observers.remove(observer);
    }
  }

}
