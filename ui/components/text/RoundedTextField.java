package ui.components.text;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ui.core.IComponent;

public class RoundedTextField extends JTextField implements IComponent {
  private int radius;
  private String innerText;
  private Color backgroundColor;
  private Color foregroundColor;

  public RoundedTextField(
    String innerText,
    Color backgroundColor,
    Color foregroundColor,
    Font font,
    Dimension dimension,
    int radius
  ) {
    super(innerText);
    setInnerText(innerText);
    setFont(font);
    setPreferredSize(dimension);
    setBackgroundColor(backgroundColor);
    setForegroundColor(foregroundColor);
    setCaretColor(foregroundColor);
    setRadius(radius);
    setOpaque(false);
    setHorizontalAlignment(CENTER);

    addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        setText("");
      }

      public void focusLost(FocusEvent e) {
        setText(innerText);
      }
    });
  }

  public void setRadius(int radius) {
    if (radius < 0) {
      this.radius = 0;
      return;
    }

    this.radius = radius;
  }

  public void setBackgroundColor(Color backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public void setForegroundColor(Color foregroundColor) {
    this.foregroundColor = foregroundColor;
  }

  public void setInnerText(String innerText) {
    this.innerText = innerText;
  }

  public void composeView() { }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create();
    
    g2d.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON
    );
    
    g2d.setColor(backgroundColor);

    g2d.fillRoundRect(
      0,
      0,
      getWidth(),
      getHeight(),
      radius,
      radius
    );
    
    setForeground(foregroundColor);
    super.paintComponent(g);
    g.dispose();
  }
}
