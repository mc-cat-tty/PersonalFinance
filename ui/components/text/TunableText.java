package ui.components.text;

import java.awt.*;
import ui.core.IComponent;
import javax.swing.*;

/**
 * Tunable (weight, size, opacity) JLabel for material text. Build pattern.
 */
public class TunableText extends JLabel implements IComponent {
  private Color textColor;

  public TunableText() {
    this("");
  }

  public TunableText(String text) {
    super(text);
  }
  
  public TunableText withColor(Color color) {
    textColor = color;
    setForeground(color);
    return this;
  }

  public TunableText withOpacity(float alphaPercentage) {
    final var AlphaMaxVal = 255;

    if (alphaPercentage < 0.f || alphaPercentage > 1.f) {
      throw new IllegalArgumentException("AlphaPercentage not allowed");
    }

    withColor(
      new Color(
        textColor.getRed(),
        textColor.getGreen(),
        textColor.getBlue(),
        (int) (alphaPercentage * AlphaMaxVal)
      )
    );
    return this;
  }

  public TunableText withFont(Font font) {
    setFont(font);
    return this;
  }
}
