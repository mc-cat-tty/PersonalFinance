package model.events;

import model.core.*;
import java.util.function.*;

/**
 * Expected behavior of Select event's observer. Functional iterface.
 */
public interface ISelectObserver extends Consumer<Transaction> { }
