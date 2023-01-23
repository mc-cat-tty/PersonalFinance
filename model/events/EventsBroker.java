package model.events;

import org.w3c.dom.events.Event;

import model.core.*;

/**
 * Dispatches events to the subscribed observers.
 * @see Singleton, Observer design patterns
 */
public class EventsBroker {
  private static EventsBroker instance;
  private final ModelEvent addEvent;
  private final ModelEvent deleteEvent;
  private final ModelEvent editEvent;

  private EventsBroker() {
    addEvent = new ModelEvent();
    deleteEvent = new ModelEvent();
    editEvent = new ModelEvent();
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
}
