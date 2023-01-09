package tunable;

import java.awt.*;

public enum CommonDimensions {
  WINDOW (1370, 962),
  UPPER_PANEL (1370, 181),
  LOWER_PANEL (1370, 91),
  PLUS_MINUS_SELECTOR (55, 55),
  ADD_BUTTON (110, 55),
  MONEY_TEXT_FIELD (147, 55),
  DESCRIPTION_TEXT_FIELD (497, 55),
  DATE_TEXT_FIELD (261, 55),
  SEARCH_TEXT_FIELD (480, 49),
  DATE_START_TEXT_FIELD(180, 49),
  DATE_END_TEXT_FIELD (180, 49),
  SEARCH_BUTTON (150, 49),
  PERIOD_SELECTOR (150, 49),
  CARD (1296, 100),
  CARD_ACTIONS (130, 50);


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
