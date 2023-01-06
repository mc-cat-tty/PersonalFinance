package tunable;

public enum CommonSizes {
  WINDOW_WIDTH (1370),
  WINDOW_HEIGHT (962),
  UPPER_PANEL_HEIGHT (181),
  UPPER_PANEL_WIDTH (1370),
  LOWER_PANEL_HEIGHT (91),
  LOWER_PANEL_WIDTH (1370);


  private final int size;
  
  private CommonSizes(int size) {
    this.size = size;
  }

  public int getSize() {
    return this.size;
  }
}
