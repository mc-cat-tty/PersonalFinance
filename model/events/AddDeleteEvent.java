package model.events;

import model.core.*;
import java.util.*;


public class AddDeleteEvent extends Event<IAddDeleteObserver> {
  public void notifyAllObservers(Collection<Transaction> changes) {
    synchronized (this.observers) {
      for (final var o : observers) {
        o.accept(changes);
      }
    }
  }
}
