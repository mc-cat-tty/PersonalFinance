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
  private Color foregroundColor;
  private boolean isActive;

  public RoundedButton(
    String innerText,
    Color backgroundColor,
    Color foregroundColor,
    Font font,
    Dimension dimension,
    int radius
  ) {
    super(innerText);
    setFont(font);
    setRadius(radius);
    setBackgroundColor(backgroundColor);
    setForegroundColor(foregroundColor);
    setPreferredSize(dimension);
    setContentAreaFilled(false);
    setFocusPainted(false);
    this.isActive = true;
  }

  private void setRadius(int radius) {
    if (radius < 0) {
      this.radius = 0;
      return;
    }

    this.radius = radius;
  }

  public void setBackgroundColor(Color color) {
    this.backgroundColor = color;
  }

  public void setForegroundColor(Color color) {
    this.foregroundColor = color;
  }

  public void deactivate() {
    isActive = false;

    setForegroundColor(
      new Color(
        foregroundColor.getRed(),
        foregroundColor.getGreen(),
        foregroundColor.getBlue(),
        80
      )
    );
    repaint();
  }

  public void activate() {
    isActive = true;

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

  public boolean isActive() {
    return isActive;
  }

  @Override
  public Insets getInsets() {
    return new Insets(0, 0, 0, 0);
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
      g2d.setColor(backgroundColor.darker());
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
