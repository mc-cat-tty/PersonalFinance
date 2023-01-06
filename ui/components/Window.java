package ui.components;

import tunable.*;
import ui.core.*;
import javax.swing.*;

public class Window extends JFrame implements IComponent {
  public Window(String name) {
    super(name);
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    composeView();

    UIManager
      .getLookAndFeelDefaults()
      .put(
        "defaultFont",
        CommonFonts.TEXT_NORMAL.getFont().deriveFont(10f)
      );
    
    setResizable(false);
    setLocationRelativeTo(null);
    setVisible(true);
  }
  
  public void composeView() {
    add(
      new BasePanel()
    );
    
    pack();
  }
}
