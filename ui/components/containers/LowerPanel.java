package ui.components.containers;

import tunable.*;
import ui.core.*;
import ui.components.clickable.*;
import ui.components.text.*;
import ui.utils.ColorOpaqueBuilder;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.core.BalanceModelManager;
import model.core.ModelEditFailedException;
import model.core.Transaction;
import model.events.EventsBroker;

/**
 * Lower panel allowing interaction.
 */
public class LowerPanel extends RoundedPanel implements IComponent {
  private static final int RADIUS = 80;
  private final RoundedButton plusButton;
  private final RoundedButton minusButton;
  private final RoundedButton addButton;
  private final RoundedTextField amountField;
  private final RoundedTextField dateField;
  private final RoundedTextField descriptionField;

  public LowerPanel() {
    super(
      new Point(0, 0),
      new Dimension(
        CommonDimensions.LOWER_PANEL.getWidth(),
        CommonDimensions.LOWER_PANEL.getHeight() + RADIUS
      ),
      new Point(
        0,
        RADIUS
      ),
      RADIUS,
      CommonColors.CARD.getColor()
    );

    plusButton = new RoundedButton(
      "+",
      CommonColors.TEXTBOX.getColor(),
      CommonColors.PLUS.getColor(),
      CommonFonts.TEXT_NORMAL
        .getFont()
        .deriveFont(60f),
      CommonDimensions.PLUS_MINUS_SELECTOR.getDimension(),
      20
    );

    minusButton = new RoundedButton(
      "-",
      CommonColors.TEXTBOX.getColor(),
      CommonColors.MINUS.getColor(),
      CommonFonts.TEXT_NORMAL
        .getFont()
        .deriveFont(60f),
      CommonDimensions.PLUS_MINUS_SELECTOR.getDimension(),
      20
    );

    addButton = new RoundedButton(
      "Add",
      CommonColors.BUTTON_PRIMARY.getColor(),
      CommonColors.TEXT.getColor(),
      CommonFonts.TEXT_NORMAL
        .getFont()
        .deriveFont(35f),
      CommonDimensions.ADD_BUTTON.getDimension(),
      63
    );

    amountField = new RoundedTextField(
      "000.00",
      CommonColors.TEXTBOX.getColor(),
      CommonColors.TEXTBOX_INVALID.getColor(),
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 0.5f),
      CommonFonts.TEXT_NORMAL.getFont().deriveFont(30f),
      CommonDimensions.MONEY_TEXT_FIELD.getDimension(),
      30
    ).setMaxLengthMonadic(CommonValidators.MONEY_AMOUNT.getMaxLength())
    .setInputFilterMonadic(CommonValidators.MONEY_AMOUNT.getFilter())
    .setInputValidatorMonadic(CommonValidators.MONEY_AMOUNT.getValidator());

    dateField = new RoundedTextField(
      "",
      CommonColors.TEXTBOX.getColor(),
      CommonColors.TEXTBOX_INVALID.getColor(),
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 1f),
      CommonFonts.TEXT_NORMAL.getFont().deriveFont(30f),
      CommonDimensions.DATE_TEXT_FIELD.getDimension(),
      30
    ).setMaxLengthMonadic(CommonValidators.DATE.getMaxLength())
    .setInputFilterMonadic(CommonValidators.DATE.getFilter())
    .setInputValidatorMonadic(CommonValidators.DATE.getValidator());

    // Proposing today as default transaction date
    dateField.setText(
      CommonDateFormats.EU_DATE_FORMAT_LONG
        .getFormatter()
        .format(new Date())
    );

    descriptionField = new RoundedTextField(
      "Description",
      CommonColors.TEXTBOX.getColor(),
      CommonColors.TEXTBOX_INVALID.getColor(),
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 0.5f),
      CommonFonts.TEXT_NORMAL.getFont().deriveFont(30f),
      CommonDimensions.DESCRIPTION_TEXT_FIELD.getDimension(),
      30
    ).setMaxLengthMonadic(CommonValidators.DESCRIPTION.getMaxLength());

    composeView();
    registerCallbacks();
    toDefault();
  }

  public void toDefault() {
    amountField.toDefault();
    descriptionField.toDefault();
    plusButton.activate();
    minusButton.deactivate();
  }

  @Override public void composeView() {
    setBorder(
      new EmptyBorder(
        CommonPaddings.LOWER_PANEL_UPPER_PADDING.getPadding(),
        0,
        0,
        0
      )
    );

    setLayout(
      new FlowLayout(
        FlowLayout.CENTER,
        CommonPaddings.LOWER_PANEL_HORIZONTAL_PADDING.getPadding(),
        20
      )
    );

    add(plusButton);
    add(minusButton);
    add(amountField);
    add(
      new TunableText("€ on")
        .setColorMonadic(CommonColors.TEXT.getColor())
        .setOpacityMonadic(1f)
        .setFontMonadic(CommonFonts.TEXT_NORMAL.getFont().deriveFont(36f))
    );
    add(dateField);
    add(descriptionField);
    add(addButton);
  }

  @Override public void registerCallbacks() {
    plusButton.addActionListener(
      event -> {
        plusButton.activate();
        minusButton.deactivate();
      }
    );

    minusButton.addActionListener(
      event -> {
        minusButton.activate();
        plusButton.deactivate();
      }
    );

    addButton.addActionListener(
      event -> {
        final boolean isDescriptionValid = descriptionField.isValidText() && !descriptionField.isDefaultText();
        final boolean isAmountValid = amountField.isValidText() && !amountField.isDefaultText();
        final boolean isDateValid = dateField.isValidText();

        if (!isDescriptionValid || !isAmountValid || !isDateValid) {
          return;
        }

        final Float sign = plusButton.isActive() ? +1f : -1f;
        final Float amount = Float.parseFloat(amountField.getText());
        final Date date;
        try {
          date = CommonDateFormats
            .EU_DATE_FORMAT_LONG
            .getFormatter()
            .parse(
              dateField.getText()
            );
        }
        catch (ParseException e) {
          dateField.setValidGUI(false);
          dateField.repaint();
          return;
        }

        final var transaction = new Transaction(
          sign * amount,
          date,
          descriptionField.getText()
        );

        try {
          BalanceModelManager
            .getInstance()
            .addTransaction(
              transaction
            );
        }
        catch (ModelEditFailedException e) {
          e.printStackTrace();
          return;
        }

        EventsBroker
          .getInstance()
          .getAddEvent()
          .notifyAllObservers(transaction);

        toDefault();
      }
    );
  }
}
