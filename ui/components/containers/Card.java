package ui.components.containers;

import ui.core.*;
import tunable.*;
import balance.*;
import ui.components.buttons.IconButton;
import ui.components.text.FlatText;

import java.awt.*;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.text.Position;

import java.util.*;
import java.util.concurrent.Flow;

/**
 * Element of a scrollable list.
 */
public class Card extends RoundedPanel implements IComponent {
  private static final int RADIUS = 50;
  private Transaction transaction;
  private FlatText sign;
  private FlatText amount;
  private FlatText sentenceConnector;
  private FlatText date;
  private FlatText description;
  private JButton editButton;
  private JButton deleteButton;

  public Card(Transaction transaction) {
    super(
      new Point(0, 0),
      CommonDimensions.CARD.getDimension(),
      new Point(0, 0),
      RADIUS,
      CommonColors.CARD.getColor()
    );
    this.transaction = new Transaction();

    composeView();
    registerCallbacks();
    setTransaction(transaction);
  }

  public void setTransaction(Transaction transaction) {
    setAmount(transaction.getAmount());
    setDate(transaction.getDate());
    setDescription(transaction.getDescription());
  }

  public void setAmount(float amount) {
    this.transaction.setAmount(amount);
    
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

    this.amount.setText(
      Float.toString(
        Math.abs( amount )
      )
    );
  }

  public void setDate(Date date) {
    this.transaction.setDate(date);

    final var pattern = "dd/mm/yyyy";
    final var formatter = new SimpleDateFormat(pattern);
    
    this.date.setText(
      formatter.format( date )
    );
  }

  public void setDescription(String description) {
    this.transaction.setDescription(description);

    this.description.setText(
      transaction.getDescription()
    );
  }

  public Transaction getTransaction() {
    return transaction;
  }

  @Override public void composeView() {
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
    add(horizontalBox, BorderLayout.WEST);


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


    final var middlePanel = new JPanel(new BorderLayout());
    middlePanel.setBorder(
      new EmptyBorder(5, 5, 5, 5)
    );
    middlePanel.setBackground(CommonColors.CARD.getColor());
    horizontalBox.add(middlePanel);

    final var middleUpperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    middleUpperPanel.setBackground(CommonColors.CARD.getColor());
    middlePanel.add(
      middleUpperPanel,
      BorderLayout.NORTH
    );


    middlePanel.add(
      description = new FlatText()
        .setColorMonadic(CommonColors.TEXT.getColor())
        .setFontMonadic(CommonFonts.TEXT_NORMAL.getFont().deriveFont(33f))
        .setOpacityMonadic(0.6f),
      BorderLayout.SOUTH
    );

    middleUpperPanel.add(
      amount = new FlatText()
        .setColorMonadic(CommonColors.TEXT.getColor())
        .setFontMonadic(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(33f)),
      BorderLayout.NORTH
    );

    middleUpperPanel.add(
      sentenceConnector = new FlatText()
      .setColorMonadic(CommonColors.TEXT.getColor())
      .setFontMonadic(CommonFonts.TEXT_NORMAL.getFont().deriveFont(33f)),
      BorderLayout.NORTH
    );

    middleUpperPanel.add(
      date = new FlatText()
        .setColorMonadic(CommonColors.TEXT.getColor())
        .setFontMonadic(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(33f)),
      BorderLayout.NORTH
    );


    final var eastPanel = new RoundedPanel(
      new Point(0, 0),
      CommonDimensions.CARD_ACTIONS.getDimension(),
      new Point(0, 0),
      RADIUS,
      CommonColors.CARD.getColor()
    );

    eastPanel.setLayout(
      new BorderLayout(
        CommonPaddings.CARD_HORIZONTAL_PADDING.getPadding(),
        0
      )
    );

    eastPanel.setBorder(
      new EmptyBorder(
        26,
        0,
        0,
        CommonPaddings.CARD_HORIZONTAL_PADDING.getPadding()
      )
    );

    eastPanel.setBackground(CommonColors.CARD.getColor());
    add(
      eastPanel,
      BorderLayout.EAST
    );

    eastPanel.add(
      editButton = new IconButton(CommonIcons.EDIT.getIcon()),
      BorderLayout.WEST
    );

    eastPanel.add(
      deleteButton = new IconButton(CommonIcons.DELETE.getIcon()),
      BorderLayout.CENTER
    );
  }

  @Override public void registerCallbacks() {
    editButton.addActionListener(
      e -> System.out.println("Edit")
    );

    deleteButton.addActionListener(
      e -> System.out.println("Delete")
    );
  }
}
