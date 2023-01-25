package model.events;

import java.util.*;
import java.util.function.*;

/**
 * Expected behavior of Filter event's observer. Functional iterface.
 */
public interface IFilterDateObserver extends BiConsumer<Date, Date> { }
