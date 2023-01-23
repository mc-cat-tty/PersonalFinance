package model.events;

import model.core.*;

import java.util.function.*;


/**
 * A interface to specialize Consumer<T>.
 */
public interface IObserver extends Consumer<Transaction> { }
