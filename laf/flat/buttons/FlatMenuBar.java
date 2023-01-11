package laf.flat.buttons;

import ui.core.IComponent;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.*;

import tunable.CommonColors;
import tunable.CommonFonts;

public class FlatMenuBar extends JMenuBar implements IComponent {
  public FlatMenuBar(
    java.util.List<FlatMenu> menus,
    Color backgroundColor,
    Color foregroundColor,
    Font font
  ) {
    super();

    setBackground(backgroundColor);
    setForeground(foregroundColor);
    setFont(font);
    setBorder(new EmptyBorder(0, 0, 0, 0));

    for (var menu : menus) {
      menu.setBackground(backgroundColor);
      menu.setForeground(foregroundColor);
      menu.setFont(font);
      menu.setBorder(new EmptyBorder(0, 0, 0, 0));
      add(menu);
    }
  }
}
