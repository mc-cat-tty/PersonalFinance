package model.events;

import java.util.*;

public interface Event {
  private final List<IObserver> observers;

  public Event() {
    observers = new 
  }

  public void attachObserver(IObserver observer) {

  }

  public void detachObserver(IObserver observer);
  public void notifyAllObservers();
}
