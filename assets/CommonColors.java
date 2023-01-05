package assets;

import java.awt.*;

public enum CommonColors {
  BACKGROUND ("#1F1F1F"),
  CARD ("#505050"),
  TEXTBOX ("#404040"),
  TEXT ("#FFFFFF"),
  PLUS ("#00C04B"),
  MINUS ("#F44336"),
  BUTTON ("#757DE8");

  private final String hexVal;

  private CommonColors(String hexVal) {
    this.hexVal = hexVal;
  }

  public String getHex() {
    return hexVal;
  }

  public Color toColor() {
    return Color.decode(hexVal);
  }
}
