package ui.components.containers;

import tunable.*;
import ui.components.buttons.FlatMenuBar;
import ui.core.*;
import javax.swing.*;

public class Window extends JFrame implements IComponent {
  private static final float DEFAULT_FONT_SIZE = 36;

  public Window(String name) {
    super(name);
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    composeView();

    UIManager
      .getLookAndFeelDefaults()
      .put(
        "defaultFont",
        CommonFonts.TEXT_NORMAL.getFont().deriveFont(DEFAULT_FONT_SIZE)
      );
    
    setResizable(false);
    setLocationRelativeTo(null);
    setVisible(true);
  }
  
  public void composeView() {
    add(
      new BasePanel()
    );
    
    setJMenuBar(new FlatMenuBar());

    pack();
  }
}
