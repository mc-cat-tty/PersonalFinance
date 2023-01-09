package ui.components.buttons;

import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

import ui.core.IComponent;

import java.awt.*;
import javax.swing.*;

public class FlatScrollBar extends JScrollBar implements IComponent {
  public FlatScrollBar() {
    super();
    
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
