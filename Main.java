import java.util.Locale;

import ui.core.*;

/**
 * Project entry-point.
 */
public class Main {

  public Main() {
    Locale.setDefault(new Locale("en", "US"));
    new UI("PersonalFinance");
  }

  public static void main(String[] args) {
    new Main();
  }
}