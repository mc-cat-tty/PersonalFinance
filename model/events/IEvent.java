package model.events;

public interface IEvent<ChangeT> {
  public void attachObserver(IObserver observer);
  public void detachObserver(IObserver observer);
  public void notifyAllObservers(ChangeT change);
}
