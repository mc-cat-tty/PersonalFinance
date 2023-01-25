package tunable;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public enum CommonFonts {
  TEXT_NORMAL (File.separator + "assets" + File.separator + "InterRegular.ttf"),
  TEXT_MEDIUM_WEIGHT (File.separator + "assets" + File.separator + "InterSemibold.ttf");

  private Font font;

  private CommonFonts(String fontPath) {
    try {
      this.font = Font.createFont(
        Font.TRUETYPE_FONT,
        getClass().getResourceAsStream(fontPath)
      )
      .deriveFont(14f);
    }
    catch (IOException | FontFormatException e) {
      System.err.println(e.getMessage());

      this.font = new Font(
        "helvetica",
        Font.PLAIN,
        14
      );
    }
  }

  public Font getFont() {
    return font;
  }
}
