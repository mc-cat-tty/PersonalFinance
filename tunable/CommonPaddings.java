package tunable;

public enum CommonPaddings {
  UPPER_PANEL_BOTTOM_PADDING (10),
  LOWER_PANEL_UPPER_PADDING (3),
  LOWER_PANEL_HORIZONTAL_PADDING (10),
  SEARCH_PANEL_BOTTOM_PADDING (15),
  SEARCH_PANEL_HORIZONTAL_PADDING (20),
  CARD_BOTTOM_PADDING (10),
  CARD_HORIZONTAL_PADDING (20);


  private final int padding;

  private CommonPaddings(int padding) {
    this.padding = padding;
  }

  public int getPadding() {
    return padding;
  }
}
