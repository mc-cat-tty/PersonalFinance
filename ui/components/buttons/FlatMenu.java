package ui.components.buttons;

import ui.core.IComponent;
import javax.swing.JMenu;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.*;
import java.util.List;

public class FlatMenu extends JMenu implements IComponent {
  private List<JMenuItem> items;
  
  public FlatMenu(String text, Icon icon) {
    super(text);
    System.out.println(icon);
    if (icon != null) {
      setIcon(icon);
    }
    getPopupMenu().setBorder(new EmptyBorder(0, 0, 0, 0));
  }

  public FlatMenu(String text) {
    this(text, null);
  }

  public FlatMenu(Icon icon) {
    this("", icon);
  }

  @Override
  public void setBackground(Color color) {
    super.setBackground(color);
    
    if (items == null) {
      return;
    }

    for (var item : items) {
      item.setBackground(color);
    }
  }

  @Override
  public void setForeground(Color color) {
    super.setForeground(color);
    
    if (items == null) {
      return;
    }

    for (var item : items) {
      item.setForeground(color);
    }
  }

  @Override
  public void setFont(Font font) {
    super.setFont(font);
    
    if (items == null) {
      return;
    }

    for (var item : items) {
      item.setFont(font);
    }
  }

  @Override
  public void setBorder(Border border) {
    super.setBorder(border);
    
    if (items == null) {
      return;
    }

    for (var item : items) {
      item.setBorder(border);
    }
  }

  public FlatMenu addItems(List<JMenuItem> items) {
    this.items = items;
    
    for (var item : items) {
      item.setBackground(getBackground());
      item.setForeground(getForeground());
      item.setFont(getFont());
      add(item);
    }

    return this;
  }
}
