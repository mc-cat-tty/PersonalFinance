package model.events;

/**
 * Dispatches events to the subscribed observers.
 * Singleton, Observer design patterns.
 */
public class EventsBroker {
  private static EventsBroker instance;
  private final AddDeleteEvent addEvent;
  private final AddDeleteEvent deleteEvent;
  private final AddDeleteEvent editEvent;
  private final AddDeleteEvent filterEvent;
  private final SelectEvent selectionEvent;
  private final FilterDateEvent filterDateEvent;

  private EventsBroker() {
    addEvent = new AddDeleteEvent();
    deleteEvent = new AddDeleteEvent();
    editEvent = new AddDeleteEvent();
    filterEvent = new AddDeleteEvent();
    selectionEvent = new SelectEvent();
    filterDateEvent = new FilterDateEvent();
  }

  /**
   * @return the unique instance of this object.
   */
  public static EventsBroker getInstance() {
    if (instance == null) {
      instance = new EventsBroker();
    }

    return instance;
  }

  public AddDeleteEvent getAddEvent() {
    return addEvent;
  }

  public AddDeleteEvent getDeleteEvent() {
    return deleteEvent;
  }

  public AddDeleteEvent getEditEvent() {
    return editEvent;
  }

  public AddDeleteEvent getFilterEvent() {
    return filterEvent;
  }

  public SelectEvent getSelectionEvent() {
    return selectionEvent;
  }

  public FilterDateEvent getFilterDateEvent() {
    return filterDateEvent;
  }
}
