package ui.components.buttons;

import ui.core.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class IconButton extends JButton implements IComponent {
  private final ImageIcon icon;

  public IconButton(ImageIcon icon) {
    super();

    this.icon = icon;
  }

  @Override public Dimension getPreferredSize() {
    return new Dimension(
      icon.getIconWidth(),
      icon.getIconHeight()
    );
  }

  @Override public void paintComponent(Graphics g) {
    final var g2d = (Graphics2D) g;

    g2d.drawImage(
      icon.getImage(),
      0,
      0,
      null
    );

    g2d.dispose();
  }
}
