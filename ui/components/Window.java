package ui.components;

import javax.swing.*;

public class Window extends JFrame {
  public Window(String name) {
    super(name);
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    add(
      new BasePanel()
    );
    pack();

    setLocationRelativeTo(null);
    setVisible(true);
  }
}
