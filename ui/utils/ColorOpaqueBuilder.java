package ui.utils;

import java.awt.*;

/**
 * Utility class for building semi-tansparent colors from an existing Color.
 */
public class ColorOpaqueBuilder {
  /**
   * Static function that build a new color from RBC components of color and alpha value.
   * @param color Base color, RGB components will be copied in the new color.
   * @param alpha Alpha percentage value. Must be between 0.0 fand 1.0.
   */
  public static Color build(Color color, float alpha) {
    return new Color(
      color.getRed(),
      color.getGreen(),
      color.getBlue(),
      (int) (alpha * 255)
    );
  }
}
