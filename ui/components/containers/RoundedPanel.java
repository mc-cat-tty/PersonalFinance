package ui.components.containers;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Base class for rounded JPanels.
 */
public class RoundedPanel extends JPanel {
  private int radius;
  private Dimension dimension;
  private Point shift;
  private Point location;
  private Color backgroundColor;
  private Insets insets;
  
  public RoundedPanel(
    Point location,
    Dimension dimension,
    Point shift,
    int radius,
    Color backgroundColor
  ) {
    super();

    this.radius = radius;
    this.dimension = dimension;
    this.shift = shift;
    this.location = location;
    this.backgroundColor = backgroundColor;
    setBorder(new EmptyBorder(0, 0, 0, 0));

    setOpaque(false);
  }

  @Override public Dimension getPreferredSize() {
    int width =
      (int) getWidth()
      + getBorder().getBorderInsets(this).right
      + getBorder().getBorderInsets(this).left
      - Math.abs((int) shift.getX());

    int height =
      (int) dimension.getHeight()
      + getBorder().getBorderInsets(this).top
      + getBorder().getBorderInsets(this).bottom
      - Math.abs((int) shift.getY());

    return new Dimension(
      width,
      height
    );
  }

  @Override protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    var g2d = (Graphics2D) g.create();

    g2d.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON
    );
    g2d.setColor(backgroundColor);

    g2d.fillRoundRect(
      (int) location.getX()
        + getBorder().getBorderInsets(this).right,
      (int) location.getY()
        + getBorder().getBorderInsets(this).top,
      (int) getWidth(),
      (int) dimension.getHeight(),
      radius,  // radius X
      radius  // radius Y 
    );
    g2d.dispose();
  }
}
