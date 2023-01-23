package model.events;

import model.core.*;

public class SelectEvent extends Event<ISelectObserver> {
  public void notifyAllObservers(Transaction selection) {
    synchronized (observers) {
      for (final var o : observers) {
        o.accept(selection);
      }
    }
  }
}
