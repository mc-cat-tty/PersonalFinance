package model.events;

/**
 * Dispatches events to the subscribed observers.
 * @see \Singleton, Observer design patterns
 */
public class EventsBroker {
  private static EventsBroker instance;
  private final ModelEvent addEvent;
  private final ModelEvent deleteEvent;
  private final ModelEvent editEvent;
  private final ModelEvent filterEvent;
  private final SelectEvent selectionEvent;
  private final FilterDateEvent filterDateEvent;

  private EventsBroker() {
    addEvent = new ModelEvent();
    deleteEvent = new ModelEvent();
    editEvent = new ModelEvent();
    filterEvent = new ModelEvent();
    selectionEvent = new SelectEvent();
    filterDateEvent = new FilterDateEvent();
  }

  public static EventsBroker getInstance() {
    if (instance == null) {
      instance = new EventsBroker();
    }

    return instance;
  }

  public ModelEvent getAddEvent() {
    return addEvent;
  }

  public ModelEvent getDeleteEvent() {
    return deleteEvent;
  }

  public ModelEvent getEditEvent() {
    return editEvent;
  }

  public ModelEvent getFilterEvent() {
    return filterEvent;
  }

  public SelectEvent getSelectionEvent() {
    return selectionEvent;
  }

  public FilterDateEvent getFilterDateEvent() {
    return filterDateEvent;
  }
}
