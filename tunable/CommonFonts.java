package tunable;

import java.awt.*;
import java.io.File;
import java.io.IOException;


public enum CommonFonts {
  TEXT_NORMAL ("assets" + File.separator + "InterRegular.ttf"),
  TEXT_MEDIUM_WEIGHT ("assets" + File.separator + "InterSemibold.ttf");

  private Font font;

  private CommonFonts(String fontPath) {
    try {
      this.font = Font.createFont(
        Font.TRUETYPE_FONT,
        new File(fontPath)
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
