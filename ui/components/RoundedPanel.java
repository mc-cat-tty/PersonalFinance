package ui.components;

import java.awt.*;
import javax.swing.*;

/**
 * Base class for rounded JPanels.
 */
public class RoundedPanel extends JPanel {
  private int radius;
  private int width;
  private int height;
  private int posX;
  private int posY;
  private Color backgroundColor;

  public RoundedPanel(
    int width,
    int height,
    int radius
  ) {
    this(0, 0, width, height, radius, Color.WHITE);
  }
  
  public RoundedPanel(
    int posX,
    int posY,
    int width,
    int height,
    int radius,
    Color backgroundColor
  ) {
    super();

    this.radius = radius;
    this.width = width;
    this.height = height;
    this.posX = posX;
    this.posY = posY;
    this.backgroundColor = backgroundColor;

    setOpaque(false);
  }

  @Override
  public Insets getInsets() {
    return new Insets(
      10,
      10,
      10,
      10
    );
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(
      width,
      height
    );
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g.create();

    g2d.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON
    );
    g2d.setColor(backgroundColor);
    g2d.fillRoundRect(
      posX,  // position X
      posY,  // position Y
      width,  // size X
      height,  // size Y
      radius,  // radius X
      radius  // radius Y 
    );
    g2d.dispose();
  }
}
