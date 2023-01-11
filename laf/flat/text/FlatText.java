package laf.flat.text;

import java.awt.*;
import ui.core.IComponent;
import javax.swing.*;

/**
 * Tunable (weight, size, opacity) JLabel for material text. Build pattern.
 */
public class FlatText extends JLabel implements IComponent {
  private Color textColor;

  public FlatText() {
    this("");
  }

  public FlatText(String text) {
    super(text);
  }
  
  public FlatText setColorMonadic(Color color) {
    textColor = color;
    setForeground(color);
    return this;
  }

  public FlatText setOpacityMonadic(float alphaPercentage) {
    final var AlphaMaxVal = 255;

    if (alphaPercentage < 0.f || alphaPercentage > 1.f) {
      throw new IllegalArgumentException("AlphaPercentage not allowed");
    }

    setColorMonadic(
      new Color(
        textColor.getRed(),
        textColor.getGreen(),
        textColor.getBlue(),
        (int) (alphaPercentage * AlphaMaxVal)
      )
    );
    return this;
  }

  public FlatText setFontMonadic(Font font) {
    setFont(font);
    return this;
  }
}
