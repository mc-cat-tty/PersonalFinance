package ui.components.clickable;

import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

import tunable.CommonColors;
import ui.core.IComponent;

import java.awt.*;
import javax.swing.*;

public class MinimalScrollBar extends JScrollBar implements IComponent {
  public MinimalScrollBar() {
    super();
    UIManager.put("ScrollBar.background", CommonColors.BACKGROUND.getColor());
    UIManager.put("ScrollBar.thumb", CommonColors.CARD.getColor().brighter());
    UIManager.put("ScrollBar.thumbDarkShadow", CommonColors.CARD.getColor().brighter());
    UIManager.put("ScrollBar.thumbHighlight", CommonColors.CARD.getColor().brighter());
    UIManager.put("ScrollBar.thumbShadow", CommonColors.CARD.getColor().brighter());
    UIManager.put("ScrollBar.track", CommonColors.CARD.getColor());
    UIManager.put("ScrollBar.trackHighlight", CommonColors.CARD.getColor());

    setUI(new BasicScrollBarUI());

    for (final Component c : getComponents()) {
      if (c instanceof AbstractButton) {
        remove(c);  // removing increase and decrease button
      }
    }
  }
}
