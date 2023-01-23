package ui.components.clickable;

import ui.core.IComponent;

import javax.swing.*;

public class MenuItem extends JMenuItem implements IComponent {
  public MenuItem(String text) {
    super(text);
  }
}
