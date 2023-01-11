package tunable;

import java.util.function.*;

public enum CommonValidators {
  MONEY_AMOUNT (
    c -> Character.isDigit(c) || c == '.',
    text -> text.matches("[0-9]{1,4}?.[0-9]{2}?") || text.equals(""),
    7
  ),
  DATE (
    c -> Character.isDigit(c) || c == '/',
    text -> text.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}") || text.equals(""),
    10
  ),
  DESCRIPTION (
    c -> true,
    c -> true,
    55
  );

  private IntPredicate filter;
  private Predicate<String> validator;
  private int maxLength;

  private CommonValidators(
    IntPredicate filter,
    Predicate<String> validator,
    int maxLength
  ) {
    this.filter = filter;
    this.validator = validator;
    this.maxLength = maxLength;
  }

  public IntPredicate getFilter() {
     return filter;
  }

  public Predicate<String> getValidator() {
    return validator;
  }

  public int getMaxLength() {
    return maxLength;
  }
}
