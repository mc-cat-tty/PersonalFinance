package ui.components.text;

import java.awt.*;
import java.awt.event.*;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;

import ui.core.IComponent;

public class RoundedTextField extends JTextField implements IComponent {
  private int radius;
  private int maxLength;
  private String innerText;
  private String currentText;
  private Color backgroundColor;
  private Color invalidBackgroundColor;
  private Color currentBackgroundColor;
  private Color foregroundColor;
  private IntPredicate inputFilter;
  private Predicate<String> inputValidator;

  public RoundedTextField(
    String innerText,
    Color backgroundColor,
    Color invalidBackgroundColor,
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
    setInvalidBackgroundColor(invalidBackgroundColor);
    setCurrentBackgroundColor(backgroundColor);
    setForegroundColor(foregroundColor);
    setCaretColor(foregroundColor);
    setRadius(radius);
    setOpaque(false);
    setHorizontalAlignment(CENTER);

    registerCallbacks();
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

  public void setInvalidBackgroundColor(Color invalidBackgroundColor) {
    this.invalidBackgroundColor = invalidBackgroundColor;
  }

  public void setForegroundColor(Color foregroundColor) {
    this.foregroundColor = foregroundColor;
  }

  public void setCurrentBackgroundColor(Color currentBackgroundColor) {
    this.currentBackgroundColor = currentBackgroundColor;
  }

  public void setInnerText(String innerText) {
    this.innerText = innerText;
  }

  /**
   * Filter input character by character.
   * @param inputFilter is tested against one character, when it is written in the field.
   */
  public RoundedTextField setInputFilterMonadic(IntPredicate inputFilter) {
    this.inputFilter = inputFilter;
    return this;
  }

  /**
   * Filter the entire input as a String. 
   * @param inputValidator is tested against the entered text, each time a character is added.
   */
  public RoundedTextField setInputValidatorMonadic(Predicate<String> inputValidator) {
    this.inputValidator = inputValidator;
    return this;
  }

  public RoundedTextField setMaxLengthMonadic(int maxLength) {
    this.maxLength = maxLength;
    return this;
  }

  public void setValid(boolean isValid) {
    if (isValid) {
      setCurrentBackgroundColor(backgroundColor);
    }
    else {
      setCurrentBackgroundColor(invalidBackgroundColor);
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create();
    
    g2d.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON
    );
    
    g2d.setColor(currentBackgroundColor);

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

  @Override
  public void registerCallbacks() {
    addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        if (getText().equals(innerText)) {
          setText("");
        }
      }

      public void focusLost(FocusEvent e) {
        if (currentText.equals("")) {
          setText(innerText);
        }
      }
    });

    getDocument().addDocumentListener(new DocumentListener() {
      public void insertUpdate(DocumentEvent e) {
        updateCurrentText();
        checkValidity();
      }

      public void removeUpdate(DocumentEvent e) {
        updateCurrentText();
        checkValidity();
      }

      public void changedUpdate(DocumentEvent e) { }

      private void updateCurrentText() {
        currentText = getText();
      }

      private void checkValidity() {
        if (inputValidator != null) {
          setValid(inputValidator.test(currentText));
        }
      }
    });

    addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (
          ( inputFilter != null && !inputFilter.test(c) ) ||
          ( maxLength != 0 && getText().length() >= maxLength )
          ) {
          e.consume();
        }
      }
    }
    );

  }
}
