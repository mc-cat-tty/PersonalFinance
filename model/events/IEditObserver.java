package model.events;

import model.core.*;
import java.util.function.*;

/**
 * Expected behavior of Edit event's observer. Functional iterface.
 */
public interface IEditObserver extends UnaryOperator<Transaction> { }
