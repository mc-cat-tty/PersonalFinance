package ui.components;

import ui.core.*;
import javax.swing.*;


public class Window extends JFrame implements IComponent {
  public Window(String name) {
    super(name);
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    composeView();
    
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
