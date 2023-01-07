package ui.components.containers;

import ui.components.buttons.RoundedButton;
import ui.components.text.RoundedTextField;
import ui.core.*;
import tunable.*;

import java.awt.*;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import javax.swing.plaf.InsetsUIResource;

/**
 * Lower panel allowing interaction.
 */
public class LowerPanel extends RoundedPanel implements IComponent {
  private static final int RADIUS = 80;
  private final RoundedButton plusButton;
  private final RoundedButton minusButton;
  private final RoundedButton addButton;
  private final RoundedTextField moneyField;

  public LowerPanel() {
    super(
      new Point(
        0,
        RADIUS
      ),
      new Dimension(
        CommonDimensions.LOWER_PANEL.getWidth(),
        CommonDimensions.LOWER_PANEL.getHeight() + RADIUS
      ),
      RADIUS,
      CommonColors.CARD.getColor()
    );

    setInsets(
      new InsetsUIResource(
        RADIUS + 10,
        10,
        10,
        10
      )
    );

    plusButton = new RoundedButton(
      "+",
      CommonColors.TEXTBOX.getColor(),
      CommonColors.PLUS.getColor(),
      CommonFonts.TEXT_NORMAL
        .getFont()
        .deriveFont(64f),
      CommonDimensions.PLUS_MINUS_SELECTOR.getDimension(),
      20
    );

    minusButton = new RoundedButton(
      "-",
      CommonColors.TEXTBOX.getColor(),
      CommonColors.MINUS.getColor(),
      CommonFonts.TEXT_NORMAL
        .getFont()
        .deriveFont(64f),
      CommonDimensions.PLUS_MINUS_SELECTOR.getDimension(),
      20
    );
    minusButton.setGrayedOut();

    addButton = new RoundedButton(
      "Add",
      CommonColors.BUTTON.getColor(),
      CommonColors.TEXT.getColor(),
      CommonFonts.TEXT_NORMAL
        .getFont()
        .deriveFont(40f),
      CommonDimensions.ADD_BUTTON.getDimension(),
      63
    );

    moneyField = new RoundedTextField(
      "Azz",
      CommonColors.TEXTBOX.getColor(),
      CommonColors.TEXT.getColor(),
      CommonFonts.TEXT_NORMAL.getFont(),
      CommonDimensions.MONEY_TEXT_FIELD.getDimension(),
      30
    );

    composeView();
    registerCallbacks();
  }

  @Override
  public void composeView() {
    setBorder(
      BorderFactory.createEmptyBorder(
        0,
        0,
        0,
        CommonPaddings.LOWER_PANEL_UPPER_PADDING.getPadding()
      )
    );

    add(plusButton);
    add(minusButton);
    add(moneyField);
    add(addButton);
  }

  @Override
  public void registerCallbacks() {
    plusButton.addActionListener(
      event -> {
        plusButton.setColored();
        minusButton.setGrayedOut();
      }
    );

    minusButton.addActionListener(
      event -> {
        minusButton.setColored();
        plusButton.setGrayedOut();
      }
    );
  }
}
