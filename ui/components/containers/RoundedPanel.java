package ui.components.containers;

import java.awt.*;
import javax.swing.*;

/**
 * Base class for rounded JPanels.
 */
public class RoundedPanel extends JPanel {
  private int radius;
  private Dimension dimension;
  private Point location;
  private Color backgroundColor;
  private Insets insets;

  public RoundedPanel(
    Dimension dimension,
    int radius
  ) {
    this(new Point(0, 0), dimension, radius, Color.WHITE);
  }
  
  public RoundedPanel(
    Point location,
    Dimension dimension,
    int radius,
    Color backgroundColor
  ) {
    super();

    this.radius = radius;
    this.dimension = dimension;
    this.location = location;
    this.backgroundColor = backgroundColor;
    this.insets = new Insets(
      radius/6,
      radius/6,
      radius/6,
      radius/6
    );

    setOpaque(false);
  }

  public void setInsets(Insets insets) {
    this.insets = insets;
  }

  @Override
  public Insets getInsets() {
    return this.insets;
  }

  @Override
  public Dimension getPreferredSize() {
    int width =
      (int) dimension.getWidth()
      + getBorder().getBorderInsets(this).right
      + getBorder().getBorderInsets(this).left;
    int height = (int) dimension.getHeight()
      + getBorder().getBorderInsets(this).top
      + getBorder().getBorderInsets(this).bottom;
    
    if (location.getY() < 0) {
      height += location.getY();
    }

    if (location.getX() < 0) {
      width += location.getX();
    }

    return new Dimension(
      width,
      height
    );
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    var g2d = (Graphics2D) g.create();

    g2d.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON
    );
    g2d.setColor(backgroundColor);
    g2d.fillRoundRect(
      (int) location.getX(),
      (int) location.getY(),
      (int) dimension.getWidth(),
      (int) dimension.getHeight(),
      radius,  // radius X
      radius  // radius Y 
    );
    g2d.dispose();
  }
}
