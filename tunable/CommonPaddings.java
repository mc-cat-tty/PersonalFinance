package tunable;

public enum CommonPaddings {
  UPPER_PANEL_BOTTOM_PADDING (25),
  LOWER_PANEL_UPPER_PADDING (34);


  private final int padding;

  private CommonPaddings(int padding) {
    this.padding = padding;
  }

  public int getPadding() {
    return padding;
  }
}
