package ui.components.clickable;

import tunable.CommonIcons;
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;

public class ArrowButton extends BasicArrowButton {
  public ArrowButton(int direction) {
    super(direction);
    setIcon(CommonIcons.FOLDER.getIcon());
  }

  @Override public void paint(Graphics g) {
    var g2d = (Graphics2D) g;

    var direction = getDirection();
    ImageIcon icon;

    if (direction == NORTH) {
      icon = CommonIcons.DROPUP.getIcon();
    }
    else {
      icon = CommonIcons.DROPDOWN.getIcon();
    }

    g2d.drawImage(
      icon.getImage(),
      0,
      0,
      null
    );

    g2d.dispose();
  }
}
