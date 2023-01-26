package ui.components.clickable;

import ui.core.IComponent;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

import ui.components.containers.RoundedCornerBorder;

public class RoundedComboBox extends JComboBox<String> implements IComponent {
  private int radius;
  private Color backgroundColor;
  private Color foregroundColor;
  private BasicArrowButton arrowButton;

  public RoundedComboBox(
    String[] choices,
    Color backgroundColor,
    Color foregroundColor,
    Font font,
    Dimension dimension,
    int radius
  ) {
    super(choices);
    setFont(font);
    setBackgroundColor(backgroundColor);
    setForegroundColor(foregroundColor);
    setPreferredSize(dimension);
    setRadius(radius);
    setBackground(backgroundColor);
    setForeground(foregroundColor);
    
    composeView();
    registerCallbacks();
  }

  private void setRadius(int radius) {
    this.radius = radius;
  }

  private void setBackgroundColor(Color backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  private void setForegroundColor(Color foregroundColor) {
    this.foregroundColor = foregroundColor;
  }

  public void setArrowDirection(int arrowDirection) {
    if (
      arrowButton != null &&
      ( arrowDirection == SwingConstants.NORTH ||
      arrowDirection == SwingConstants.SOUTH )
    ) {
      arrowButton.setDirection(arrowDirection);
    }
  }


  @Override public void composeView() {
    UIManager.put("ComboBox.border", new RoundedCornerBorder(radius, 0));
    UIManager.put("ComboBox.foreground", foregroundColor);
    UIManager.put("ComboBox.background", backgroundColor);
    UIManager.put("ComboBox.selectionForeground", foregroundColor.darker());
    UIManager.put("ComboBox.selectionBackground", backgroundColor);
    UIManager.put("ComboBox.buttonDarkShadow", backgroundColor);
    UIManager.put("ComboBox.buttonBackground", foregroundColor);
    UIManager.put("ComboBox.buttonHighlight", foregroundColor);
    UIManager.put("ComboBox.buttonShadow", foregroundColor);

    setUI(
      new BasicComboBoxUI() {
        @Override protected JButton createArrowButton() {
          final var button = new ArrowButton(SwingConstants.SOUTH);
          button.setOpaque(false);
          return button;
        }
      }
    );
    
    var o = getAccessibleContext().getAccessibleChild(0);      
    if (o instanceof JPopupMenu) {
      var c = (JComponent) o;
      c.setBorder(new RoundedCornerBorder(radius, 0));
      c.setForeground(foregroundColor);
      c.setBackground(backgroundColor);
    }

    for (final var c : getComponents()) {
      if (c instanceof BasicArrowButton) {
        arrowButton = (BasicArrowButton) c;
      }
    }

    ((JLabel) getRenderer()).setHorizontalAlignment(JLabel.CENTER);
  }

  @Override public void registerCallbacks() {
    addPopupMenuListener(
      new PopupMenuListener() {
        @Override public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
          setArrowDirection(SwingConstants.NORTH);
          setBorder(new RoundedCornerBorder(radius, 1));
        }

        @Override public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
          setArrowDirection(SwingConstants.SOUTH);
          setBorder(new RoundedCornerBorder(radius, 0));
        }
        
        @Override public void popupMenuCanceled(PopupMenuEvent e) { }
      }
    );
  }
}