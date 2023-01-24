package model.events;

import java.util.*;

public class FilterDateEvent extends Event<IFilterDateObserver> {
  public void notifyAllObservers(Date startDate, Date endDate) {
    synchronized (observers) {
      for (final var o : observers) {
        o.accept(startDate, endDate);
      }
    }
  }
}
