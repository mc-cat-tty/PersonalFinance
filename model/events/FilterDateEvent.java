package model.events;

import java.util.*;

/**
 * Filter event on the view.
 */
public class FilterDateEvent extends Event<IFilterDateObserver> {

  /**
   * Notify all the subscribed observer that a filter (startDate, endDate) has been applied.
   */
  public void notifyAllObservers(Date startDate, Date endDate) {
    synchronized (observers) {
      for (final var o : observers) {
        o.accept(startDate, endDate);
      }
    }
  }
}
