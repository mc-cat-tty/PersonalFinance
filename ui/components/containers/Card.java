package ui.components.containers;

import ui.core.*;
import tunable.*;
import model.core.*;
import ui.components.buttons.CardActionPanel;
import ui.components.buttons.IconButton;
import ui.components.buttons.CardActionPanel.CardAction;

import java.awt.*;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;
import javax.swing.text.Position;

import ui.components.buttons.*;
import ui.components.text.*;

import java.util.*;
import java.util.concurrent.Flow;

/**
 * Element of a scrollable list.
 */
public class Card extends RoundedPanel implements IComponent {
  private static final int RADIUS = 50;
  private static final String VIEW_KEY = "VIEW";
  private static final String EDIT_KEY = "EDIT";

  private Transaction transaction;
  private FlatText sign;
  
  private FlatText amount;
  private RoundedTextField amountEditor;
  private LayeredPanel amountPanel;

  private FlatText sentenceConnector;
  
  private FlatText date;
  private RoundedTextField dateEditor;
  private LayeredPanel datePanel;

  private FlatText description;
  private RoundedTextField descriptionEditor;
  private LayeredPanel descriptionPanel;
  
  private JButton editButton;
  private JButton deleteButton;
  private JButton confirmButton;
  private CardActionPanel actionPanel;


  public Card(Transaction transaction) {
    super(
      new Point(0, 0),
      CommonDimensions.CARD.getDimension(),
      new Point(0, 0),
      RADIUS,
      CommonColors.CARD.getColor()
      );
      this.transaction = new Transaction();
      
      
    amount = new FlatText()
      .setColorMonadic(CommonColors.TEXT.getColor())
      .setFontMonadic(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(33f));
    amount.setBorder(new EmptyBorder(0, 20, 0, 20));

    sign = new FlatText("+")
      .setColorMonadic(CommonColors.TEXT.getColor())
      .setFontMonadic(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(64f));

    description = new FlatText()
      .setColorMonadic(CommonColors.TEXT.getColor())
      .setFontMonadic(CommonFonts.TEXT_NORMAL.getFont().deriveFont(33f))
      .setOpacityMonadic(0.6f);
    description.setBorder(new EmptyBorder(0, 15, 0, 0));
    description.setPreferredSize(new Dimension(1050, 40));
    
    sentenceConnector = new FlatText()
      .setColorMonadic(CommonColors.TEXT.getColor())
      .setFontMonadic(CommonFonts.TEXT_NORMAL.getFont().deriveFont(33f));

    date = new FlatText()
      .setColorMonadic(CommonColors.TEXT.getColor())
      .setFontMonadic(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(33f));
    date.setBorder(new EmptyBorder(0, 15, 0, 15));

    amountEditor = new RoundedTextField(
      "",
      CommonColors.TEXTBOX.getColor(),
      CommonColors.TEXTBOX_INVALID.getColor(),
      CommonColors.TEXT.getColor(),
      CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(33f),
      new Dimension(0, 0),
      30
    ).setMaxLengthMonadic(CommonValidators.MONEY_AMOUNT_EDITOR.getMaxLength())
    .setInputFilterMonadic(CommonValidators.MONEY_AMOUNT_EDITOR.getFilter())
    .setInputValidatorMonadic(CommonValidators.MONEY_AMOUNT_EDITOR.getValidator());
      
    dateEditor = new RoundedTextField(
      "",
      CommonColors.TEXTBOX.getColor(),
      CommonColors.TEXTBOX_INVALID.getColor(),
      CommonColors.TEXT.getColor(),
      CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(33f),
      new Dimension(0, 0),
      30
    ).setMaxLengthMonadic(CommonValidators.DATE.getMaxLength())
    .setInputFilterMonadic(c -> CommonValidators.DATE.getFilter().test(c))
    .setInputValidatorMonadic(CommonValidators.DATE.getValidator());

    descriptionEditor = new RoundedTextField(
      "",
      CommonColors.TEXTBOX.getColor(),
      CommonColors.TEXTBOX_INVALID.getColor(),
      CommonColors.TEXT.getColor(),
      CommonFonts.TEXT_NORMAL.getFont().deriveFont(33f),
      new Dimension(0, 0),
      30
    ).setMaxLengthMonadic(CommonValidators.DESCRIPTION.getMaxLength());
    descriptionEditor.setHorizontalAlignment(SwingConstants.LEFT);

    actionPanel = new CardActionPanel(
      editButton = new IconButton(CommonIcons.EDIT.getIcon()),
      deleteButton = new IconButton(CommonIcons.DELETE.getIcon()),
      confirmButton = new RoundedButton(
        "Confirm",
        CommonColors.BUTTON_PRIMARY.getColor(),
        CommonColors.TEXT.getColor(),
        CommonFonts.TEXT_NORMAL.getFont().deriveFont(25f),
        new Dimension(48, 48),
        10
      ),
      RADIUS
    );

    amountPanel = new LayeredPanel(new HashMap<>(){{
      put(VIEW_KEY, amount);
      put(EDIT_KEY, amountEditor);
    }});
    amountPanel.setBackground(CommonColors.CARD.getColor());
    amountPanel.setVisibleComponent(VIEW_KEY);

    datePanel = new LayeredPanel(new HashMap<>(){{
      put(VIEW_KEY, date);
      put(EDIT_KEY, dateEditor);
    }});
    datePanel.setBackground(CommonColors.CARD.getColor());
    datePanel.setVisibleComponent(VIEW_KEY);

    descriptionPanel = new LayeredPanel(new HashMap<>(){{
      put(VIEW_KEY, description);
      put(EDIT_KEY, descriptionEditor);
    }});
    descriptionPanel.setBackground(CommonColors.CARD.getColor());
    descriptionPanel.setVisibleComponent(VIEW_KEY);

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
      sentenceConnector.setText("outcome on");
    }
    else {
      sign
        .setColorMonadic(CommonColors.PLUS.getColor())
        .setText("+");
      sentenceConnector.setText("income on");
    }

    this.amount.setText(Float.toString( Math.abs(amount) ));
    amountEditor.setText(Float.toString(amount));
  }

  public void setDate(Date date) {
    this.transaction.setDate(date);

    final var pattern = "dd/MM/yyyy";
    final var formatter = new SimpleDateFormat(pattern);
    final var dateStr = formatter.format(date);

    this.date.setText(dateStr);
    dateEditor.setText(dateStr);
  }

  public void setDescription(String description) {
    this.transaction.setDescription(description);

    this.description.setText(
      transaction.getDescription()
    );

    descriptionEditor.setText(
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

    sign.setPreferredSize(new Dimension(50, 50));
    horizontalBox.add(sign);


    final var middlePanel = new JPanel(new BorderLayout());
    middlePanel.setBorder(
      new EmptyBorder(5, 5, 5, 5)
    );
    middlePanel.setBackground(CommonColors.CARD.getColor());
    horizontalBox.add(middlePanel);

    final var middleUpperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    middleUpperPanel.setBackground(CommonColors.CARD.getColor());
    middlePanel.add(middleUpperPanel, BorderLayout.NORTH);


    middlePanel.add(descriptionPanel, BorderLayout.SOUTH);

    middleUpperPanel.add(amountPanel, BorderLayout.NORTH);

    middleUpperPanel.add(sentenceConnector, BorderLayout.NORTH);

    middleUpperPanel.add(datePanel, BorderLayout.NORTH);

    add(actionPanel, BorderLayout.EAST);
  }

  @Override public void registerCallbacks() {
    editButton.addActionListener(
      e -> {
        amountPanel.setVisibleComponent(EDIT_KEY);
        datePanel.setVisibleComponent(EDIT_KEY);
        descriptionPanel.setVisibleComponent(EDIT_KEY);
        actionPanel.setCurrentAction(CardAction.CONFIRM);
      }
    );

    deleteButton.addActionListener(
      e -> setVisible(false)
    );

    confirmButton.addActionListener(
      e -> {
        amountPanel.setVisibleComponent(VIEW_KEY);
        datePanel.setVisibleComponent(VIEW_KEY);
        descriptionPanel.setVisibleComponent(VIEW_KEY);
        actionPanel.setCurrentAction(CardAction.EDIT_DELETE);
      }
    );
  }
}
