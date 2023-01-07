package tunable;

import java.awt.*;

public enum CommonColors {
  BACKGROUND ("#1F1F1F"),
  CARD ("#505050"),
  TEXTBOX ("#404040"),
  TEXTBOX_INVALID ("#592D2D"),
  BUTTON_PRESSED ("#353535"),
  TEXT ("#FFFFFF"),
  PLUS ("#00C04B"),
  MINUS ("#F44336"),
  BUTTON ("#757DE8"),
  TOPBAR ("#303030");

  private final String hexVal;

  private CommonColors(String hexVal) {
    this.hexVal = hexVal;
  }

  public String getHex() {
    return hexVal;
  }

  public Color getColor() {
    return Color.decode(hexVal);
  }
}
