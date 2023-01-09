package ui.components.containers;

import ui.core.*;
import tunable.*;
import balance.*;
import ui.components.text.FlatText;

import java.awt.*;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.text.Position;

import java.util.*;

/**
 * Element of a scrollable list.
 */
public class Card extends RoundedPanel implements IComponent {
  private Transaction transaction;
  private FlatText sign;
  private FlatText amount;
  private FlatText sentenceConnector;
  private FlatText date;
  private FlatText description;

  public Card(Transaction transaction) {
    super(
      new Point(0, 0),
      CommonDimensions.CARD.getDimension(),
      new Point(0, 0),
      50,
      CommonColors.CARD.getColor()
    );

    setInsets(
      new Insets(
        0, 0, 5, 0
      )
    );

    composeView();
    setTransaction(transaction);
  }

  public void setTransaction(Transaction transaction) {
    this.transaction = transaction;

    if (transaction.getAmount() < 0) {
      sign
        .setColorMonadic(CommonColors.MINUS.getColor())
        .setText("-");
      sentenceConnector.setText(" outcome on");
    }
    else {
      sign
        .setColorMonadic(CommonColors.PLUS.getColor())
        .setText("+");
      sentenceConnector.setText(" income on ");
    }

    amount.setText(
      Float.toString(
        Math.abs( transaction.getAmount() )
      )
    );

    final var pattern = "dd/mm/yyyy";
    final var formatter = new SimpleDateFormat(pattern);
    date.setText(
      formatter.format(
        transaction.getDate()
      )
    );

    description.setText(
      transaction.getDescription()
    );
  }

  public Transaction getTransaction() {
    return transaction;
  }

  @Override public void composeView() {
    setAlignmentX(CENTER_ALIGNMENT);
    setAlignmentY(TOP_ALIGNMENT);

    setBorder(
      new EmptyBorder(
        0,
        0,
        CommonPaddings.CARD_BOTTOM_PADDING.getPadding(),
        0
      )
    );

    setLayout(
      new BorderLayout()
    );

    final var horizontalBox = Box.createHorizontalBox();
    add(horizontalBox, BorderLayout.PAGE_START);


    horizontalBox.add(
      Box.createRigidArea(
        new Dimension(CommonPaddings.CARD_HORIZONTAL_PADDING.getPadding(), 0)
      )
    );

    horizontalBox.add(
      sign = new FlatText("+")
        .setColorMonadic(CommonColors.TEXT.getColor())
        .setFontMonadic(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(64f))
    );

    horizontalBox.add(
      Box.createRigidArea(
        new Dimension(CommonPaddings.CARD_HORIZONTAL_PADDING.getPadding(), 0)
      )
    );


    final var middlePanelSize = new Dimension(
      CommonDimensions.CARD.getWidth() - 200,
      CommonDimensions.CARD.getHeight()
    );
    final var middlePanel = new JPanel(new BorderLayout());
    middlePanel.setMaximumSize(middlePanelSize);
    middlePanel.setBackground(CommonColors.CARD.getColor());
    horizontalBox.add(middlePanel);

    final var middleUpperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    middleUpperPanel.setBackground(CommonColors.CARD.getColor());
    middleUpperPanel.setMaximumSize(middlePanelSize);
    middleUpperPanel.setAlignmentX(LEFT_ALIGNMENT);
    middlePanel.add(
      middleUpperPanel,
      BorderLayout.NORTH
    );


    middlePanel.add(
      description = new FlatText()
        .setColorMonadic(CommonColors.TEXT.getColor())
        .setFontMonadic(CommonFonts.TEXT_NORMAL.getFont().deriveFont(36f))
        .setOpacityMonadic(0.6f),
      BorderLayout.SOUTH
    );

    middleUpperPanel.add(
      amount = new FlatText()
        .setColorMonadic(CommonColors.TEXT.getColor())
        .setFontMonadic(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(36f)),
      BorderLayout.NORTH
    );

    middleUpperPanel.add(
      sentenceConnector = new FlatText()
      .setColorMonadic(CommonColors.TEXT.getColor())
      .setFontMonadic(CommonFonts.TEXT_NORMAL.getFont().deriveFont(36f)),
      BorderLayout.NORTH
    );

    middleUpperPanel.add(
      date = new FlatText()
        .setColorMonadic(CommonColors.TEXT.getColor())
        .setFontMonadic(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(36f)),
      BorderLayout.NORTH
    );

  }
}
