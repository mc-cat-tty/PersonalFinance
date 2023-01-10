package ui.components.buttons;

import javax.swing.JScrollBar;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicScrollBarUI;

import tunable.CommonColors;
import ui.core.IComponent;

import java.awt.*;
import javax.swing.*;

public class FlatScrollBar extends JScrollBar implements IComponent {
  public FlatScrollBar() {
    super();
    UIManager.put("ScrollBar.background", CommonColors.BACKGROUND.getColor());
    UIManager.put("ScrollBar.thumb", CommonColors.CARD.getColor().brighter());
    UIManager.put("ScrollBar.thumbDarkShadow", CommonColors.CARD.getColor().brighter());
    UIManager.put("ScrollBar.thumbHighlight", CommonColors.CARD.getColor().brighter());
    UIManager.put("ScrollBar.thumbShadow", CommonColors.CARD.getColor().brighter());
    UIManager.put("ScrollBar.track", CommonColors.CARD.getColor());
    UIManager.put("ScrollBar.trackHighlight", CommonColors.CARD.getColor());

    setUI(new BasicScrollBarUI());

    for (var c : getComponents()) {
      if (c instanceof AbstractButton) {
        remove(c);  // removing increase and decrease button
      }
    }
  }

  // @Override public void paintComponent(Graphics g) {
  //   var g2d = (Graphics2D) g;

  //   g2d.setRenderingHint(
  //     RenderingHints.KEY_ANTIALIASING,
  //     RenderingHints.VALUE_ANTIALIAS_ON
  //   );

    

  //   g2d.dispose();
  // }
}
