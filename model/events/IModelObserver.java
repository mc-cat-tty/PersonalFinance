package model.events;

import model.core.*;

import java.util.Collection;
import java.util.function.*;


/**
 * A interface to specialize Consumer<T> for ModelEvents.
 */
public interface IModelObserver extends Consumer<Collection<Transaction>> { }
