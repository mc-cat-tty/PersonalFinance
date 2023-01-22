package ui.components.text;

import java.awt.*;
import java.awt.event.*;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;

import ui.core.IComponent;
import ui.utils.ColorOpaqueBuilder;

public class RoundedTextField extends JTextField implements IComponent {
  private int radius;
  private int maxLength;
  private String defaultText;
  private String currentText;
  private Color backgroundColor;
  private Color invalidBackgroundColor;
  private Color currentBackgroundColor;
  private Color foregroundColor;
  private IntPredicate inputFilter;
  private Predicate<String> inputValidator;

  public RoundedTextField(
    String defaultText,
    Color backgroundColor,
    Color invalidBackgroundColor,
    Color foregroundColor,
    Font font,
    Dimension dimension,
    int radius
  ) {
    super(defaultText);
    setDefaultText(defaultText);
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
    setBorder(new EmptyBorder(0, 10, 0, 10));

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

  public void setDefaultText(String defaultText) {
    this.defaultText = defaultText;
    setText(defaultText);
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

  @Override protected void paintComponent(Graphics g) {
    var g2d = (Graphics2D) g.create();
    
    g2d.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON
    );
    
    if (isEditable()) {
      g2d.setColor(currentBackgroundColor);
    }
    else {
      g2d.setColor(currentBackgroundColor.darker());
    }

    g2d.fillRoundRect(
      0,
      0,
      getWidth(),
      getHeight(),
      radius,
      radius
    );
    
    if (isEditable()) {
      setForeground(foregroundColor);
    }
    else {
      setForeground(ColorOpaqueBuilder.build(foregroundColor, 0.2f));
    }

    super.paintComponent(g);
    g.dispose();
  }

  @Override public void registerCallbacks() {
    addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        if (!isEditable()) {
          return;
        }

        if (getText().equals(defaultText)) {
          setText("");
          foregroundColor = ColorOpaqueBuilder.build(foregroundColor, 1f);
        }
      }

      public void focusLost(FocusEvent e) {
        if (!isEditable()) {
          return;
        }
        
        if (currentText.equals("")) {
          setText(defaultText);
          foregroundColor = ColorOpaqueBuilder.build(foregroundColor, 0.5f);
        }
        else {
          foregroundColor = ColorOpaqueBuilder.build(foregroundColor, 1f);
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

    addKeyListener(
      new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
          final char insertedChar = e.getKeyChar();

          final boolean isTextNotSelected = getSelectedText() == null;

          final boolean isCharNotAllowed =
            inputFilter != null &&
            !inputFilter.test(insertedChar);

          final boolean isLengthNotOk =
            maxLength != 0 &&
            getText().length() >= maxLength;

          if (isTextNotSelected && (isCharNotAllowed || isLengthNotOk)) {
            e.consume();
          }
        }
      }
    );

  }
}