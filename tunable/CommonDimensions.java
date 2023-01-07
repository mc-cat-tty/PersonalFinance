package tunable;

import java.awt.*;

public enum CommonDimensions {
  WINDOW (1370, 962),
  UPPER_PANEL (1370, 181),
  LOWER_PANEL (1370, 91),
  PLUS_MINUS_SELECTOR (63, 63),
  ADD_BUTTON (134, 63),
  MONEY_TEXT_FIELD (147, 63);


  private final Dimension dimension;
  
  private CommonDimensions(int width, int height) {
    this.dimension = new Dimension(width, height);
  }

  public Dimension getDimension() {
    return dimension;
  }

  public int getWidth() {
    return (int) dimension.getWidth();
  }

  public int getHeight() {
    return (int) dimension.getHeight();
  }
}
