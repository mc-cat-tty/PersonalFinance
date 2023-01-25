package model.events;

import model.core.*;

import java.util.Collection;
import java.util.function.*;


/**
 * Expected behavior of AddDelete event's observer. Functional iterface.
 */
public interface IAddDeleteObserver extends Consumer<Collection<Transaction>> { }
