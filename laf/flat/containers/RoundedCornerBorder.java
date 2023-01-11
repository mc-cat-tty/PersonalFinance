package laf.flat.containers;

import java.awt.*;
import java.awt.geom.*;
import java.util.Objects;

import javax.swing.*;
import javax.swing.border.AbstractBorder;

public class RoundedCornerBorder extends AbstractBorder {
  private static final int SELECTOR_POPUP_DISTANCE = 6;
  private final int radius;
  private final int borderWidth;

  public RoundedCornerBorder(int radius, int borderWidth) {
    this.radius = radius;
    this.borderWidth = borderWidth;
  }
  
  @Override public void paintBorder(
    Component c,
    Graphics g,
    int x,
    int y,
    int width,
    int height
  ) {
    var g2d = (Graphics2D) g.create();

    g2d.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON
    );
    
    if (c instanceof JPopupMenu) {
      var round = new Area(new RoundRectangle2D.Float(
        x,
        y + SELECTOR_POPUP_DISTANCE,
        width - 1,
        height - SELECTOR_POPUP_DISTANCE - 1,
        radius,
        radius
      ));
      
      g2d.setPaint(c.getBackground());
      g2d.fill(round);
    }
    else {
      var parent = c.getParent();

      if (Objects.nonNull(parent)) {
        var round = new Area(new RoundRectangle2D.Float(
          x + borderWidth,
          y + borderWidth,
          width - 2 * borderWidth,
          height - 2 * borderWidth,
          radius,
          radius
        ));

        g2d.setPaint(parent.getBackground());
        var corner = new Area(new Rectangle2D.Float(x, y, width, height));
        corner.subtract(round);
        g2d.fill(corner);

        var roundExtern = new Area(new RoundRectangle2D.Float(
          x,
          y,
          width,
          height,
          radius,
          radius
        ));

        roundExtern.subtract(round);
        g2d.setPaint(c.getForeground());
        g2d.fill(roundExtern);
      }
    }
    
    g2d.dispose();
  }

  @Override public Insets getBorderInsets(Component c, Insets insets) {
    if (c instanceof JPopupMenu) {
      insets.set(SELECTOR_POPUP_DISTANCE + 4, 8, SELECTOR_POPUP_DISTANCE + 4, 5);
    }
    else {
      insets.set(4, 8, 4, 5);
    }

    return insets;
  }
}
