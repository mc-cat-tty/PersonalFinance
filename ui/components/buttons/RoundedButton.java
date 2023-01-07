package ui.components.buttons;

import ui.core.IComponent;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Position;

public class RoundedButton extends JButton implements IComponent {
  private int radius;
  private Color backgroundColor;
  private Color armedBackgroundColor;
  private Color foregroundColor;

  public RoundedButton(
    String innerText,
    Color backgroundColor,
    Color armedBackgroundColor,
    Color foregroundColor,
    Font font,
    Dimension dimension,
    int radius
  ) {
    super(innerText);
    setFont(font);
    setRadius(radius);
    setBackgroundColor(backgroundColor);
    setArmedBackgroundColor(armedBackgroundColor);
    setForegroundColor(foregroundColor);
    setPreferredSize(dimension);
    setContentAreaFilled(false);
    setFocusPainted(false);
  }

  private void setRadius(int radius) {
    if (radius < 0) {
      this.radius = 0;
    }

    this.radius = radius;
  }

  public void setBackgroundColor(Color color) {
    this.backgroundColor = color;
  }

  public void setArmedBackgroundColor(Color color) {
    this.armedBackgroundColor = color;
  }

  public void setForegroundColor(Color color) {
    this.foregroundColor = color;
  }

  public void setGrayedOut() {
    setForegroundColor(
      new Color(
        foregroundColor.getRed(),
        foregroundColor.getGreen(),
        foregroundColor.getBlue(),
        127
      )
    );
    repaint();
  }

  public void setColored() {
    setForegroundColor(
      new Color(
        foregroundColor.getRed(),
        foregroundColor.getGreen(),
        foregroundColor.getBlue(),
        255
      )
    );
    repaint();
  }

  @Override
  public void composeView() { }

  @Override
  public Insets getInsets() {
    return new Insets(
      0,
      0,
      0,
      0
    );
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create();

    g2d.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON
    );

    if (!getModel().isArmed()) {
      g2d.setColor(backgroundColor);
    }
    else {
      g2d.setColor(armedBackgroundColor);
    }
    
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