package ui.components.containers;

import ui.components.buttons.RoundedButton;
import ui.components.text.FlatText;
import ui.components.text.RoundedTextField;
import ui.core.*;
import tunable.*;

import java.awt.*;

import javax.swing.*;

/**
 * Lower panel allowing interaction.
 */
public class LowerPanel extends RoundedPanel implements IComponent {
  private static final int RADIUS = 80;
  private final RoundedButton plusButton;
  private final RoundedButton minusButton;
  private final RoundedButton addButton;
  private final RoundedTextField moneyField;
  private final RoundedTextField dateField;
  private final RoundedTextField descriptionField;

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
      new Insets(
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
      "000,00",
      CommonColors.TEXTBOX.getColor(),
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 0.5f),
      CommonFonts.TEXT_NORMAL.getFont().deriveFont(36f),
      CommonDimensions.MONEY_TEXT_FIELD.getDimension(),
      30
    );

    dateField = new RoundedTextField(
      "01/01/2022",
      CommonColors.TEXTBOX.getColor(),
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 0.5f),
      CommonFonts.TEXT_NORMAL.getFont().deriveFont(36f),
      CommonDimensions.DATE_TEXT_FIELD.getDimension(),
      30
    );

    descriptionField = new RoundedTextField(
      "Description",
      CommonColors.TEXTBOX.getColor(),
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 0.5f),
      CommonFonts.TEXT_NORMAL.getFont().deriveFont(36f),
      CommonDimensions.DESCRIPTION_TEXT_FIELD.getDimension(),
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
    add(
      new FlatText("€ on")
        .setColorMonadic(CommonColors.TEXT.getColor())
        .setOpacityMonadic(1f)
        .setFontMonadic(CommonFonts.TEXT_NORMAL.getFont().deriveFont(36f))
    );
    add(dateField);
    add(descriptionField);
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
