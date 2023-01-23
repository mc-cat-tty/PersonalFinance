package ui.components.clickable;

import ui.core.*;
import java.awt.*;
import javax.swing.*;

public class IconButton extends JButton implements IComponent {
  private static final int REDUCE_PIXELS = 4;
  private final ImageIcon icon;
  private final ImageIcon iconSmaller;

  public IconButton(ImageIcon icon) {
    super();
    this.icon = icon;
    this.iconSmaller = new ImageIcon(
      icon.getImage().getScaledInstance(
        icon.getIconWidth() - REDUCE_PIXELS,
        icon.getIconHeight() - REDUCE_PIXELS,
        Image.SCALE_SMOOTH
      )
    );
    setContentAreaFilled(false);
    setFocusPainted(false);
    setBorderPainted(false);
  }

  @Override public Dimension getPreferredSize() {
    return new Dimension(
      icon.getIconWidth(),
      icon.getIconHeight()
    );
  }

  @Override public void paintComponent(Graphics g) {
    final var g2d = (Graphics2D) g.create();

    g2d.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON
    );

    g2d.setRenderingHint(
      RenderingHints.KEY_INTERPOLATION,
      RenderingHints.VALUE_INTERPOLATION_BILINEAR
    );

    if (!getModel().isRollover()) {
      g2d.drawImage(
        iconSmaller.getImage(),
        REDUCE_PIXELS / 2,
        REDUCE_PIXELS / 2,
        null
      );
    }
    else {
      g2d.drawImage(
        icon.getImage(),
        0,
        0,
        null
      ); 
    }

    g2d.dispose();
  }
}
