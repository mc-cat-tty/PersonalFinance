package ui.components.containers;

import ui.core.*;
import ui.utils.ColorOpaqueBuilder;
import tunable.*;
import model.core.*;
import model.events.EventsBroker;

import java.awt.*;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ui.components.clickable.*;
import ui.components.text.*;
import ui.behaviour.*;

import java.util.*;

/**
 * Element of a scrollable list.
 */
public class Card extends RoundedPanel implements IComponent {
  private static final int RADIUS = 50;
  private static final String VIEW_KEY = "VIEW";
  private static final String EDIT_KEY = "EDIT";

  private Transaction transaction;
  private TunableText sign;
  
  private TunableText amount;
  private RoundedTextField amountEditor;
  private LayeredPanel amountPanel;

  private TunableText sentenceConnector;
  
  private TunableText date;
  private RoundedTextField dateEditor;
  private LayeredPanel datePanel;

  private TunableText description;
  private RoundedTextField descriptionEditor;
  private LayeredPanel descriptionPanel;
  
  private JButton editButton;
  private JButton deleteButton;
  private JButton confirmButton;
  private CardActionPanel actionPanel;

  private Box horizontalBox;
  private JPanel middlePanel;
  private JPanel middleUpperPanel;

  public Card(Transaction transaction) {
    super(
      new Point(0, 0),
      CommonDimensions.CARD.getDimension(),
      new Point(0, 0),
      RADIUS,
      CommonColors.CARD.getColor()
    );

    this.transaction = transaction;
      
      
    amount = new TunableText()
      .withColor(CommonColors.TEXT.getColor())
      .withFont(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(33f));
    amount.setBorder(new EmptyBorder(0, 20, 0, 20));

    sign = new TunableText("+")
      .withColor(CommonColors.TEXT.getColor())
      .withFont(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(64f));

    description = new TunableText()
      .withColor(CommonColors.TEXT.getColor())
      .withFont(CommonFonts.TEXT_NORMAL.getFont().deriveFont(33f))
      .withOpacity(0.6f);
    description.setBorder(new EmptyBorder(0, 20, 0, 0));
    description.setPreferredSize(new Dimension(1050, 40));
    
    sentenceConnector = new TunableText()
      .withColor(CommonColors.TEXT.getColor())
      .withFont(CommonFonts.TEXT_NORMAL.getFont().deriveFont(33f));

    date = new TunableText()
      .withColor(CommonColors.TEXT.getColor())
      .withFont(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(33f));
    date.setBorder(new EmptyBorder(0, 15, 0, 15));

    amountEditor = new RoundedTextField(
      "",
      CommonColors.TEXTBOX.getColor(),
      CommonColors.TEXTBOX_INVALID.getColor(),
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 0.7f),
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 0.7f),
      CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(33f),
      new Dimension(0, 0),
      30
    ).withMaxLength(CommonValidators.MONEY_AMOUNT_EDITOR.getMaxLength())
    .withInputFilter(CommonValidators.MONEY_AMOUNT_EDITOR.getFilter())
    .withInputValidator(CommonValidators.MONEY_AMOUNT_EDITOR.getValidator());
      
    dateEditor = new RoundedTextField(
      "",
      CommonColors.TEXTBOX.getColor(),
      CommonColors.TEXTBOX_INVALID.getColor(),
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 0.7f),
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 0.7f),
      CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(33f),
      new Dimension(0, 0),
      30
    ).withMaxLength(CommonValidators.DATE.getMaxLength())
    .withInputFilter(c -> CommonValidators.DATE.getFilter().test(c))
    .withInputValidator(CommonValidators.DATE.getValidator());

    descriptionEditor = new RoundedTextField(
      "",
      CommonColors.TEXTBOX.getColor(),
      CommonColors.TEXTBOX_INVALID.getColor(),
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 0.7f),
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 0.7f),
      CommonFonts.TEXT_NORMAL.getFont().deriveFont(33f),
      new Dimension(0, 0),
      30
    ).withMaxLength(CommonValidators.DESCRIPTION.getMaxLength());
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
    amountPanel.setVisibleComponent(VIEW_KEY);

    datePanel = new LayeredPanel(new HashMap<>(){{
      put(VIEW_KEY, date);
      put(EDIT_KEY, dateEditor);
    }});
    datePanel.setVisibleComponent(VIEW_KEY);

    descriptionPanel = new LayeredPanel(new HashMap<>(){{
      put(VIEW_KEY, description);
      put(EDIT_KEY, descriptionEditor);
    }});
    descriptionPanel.setVisibleComponent(VIEW_KEY);

    middlePanel = new JPanel(new BorderLayout());
    middlePanel.setBorder(
      new EmptyBorder(2, 5, 8, 5)
    );

    middleUpperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    setBackground(CommonColors.CARD.getColor());
    composeView();
    registerCallbacks();
    setTransaction(transaction);
  }

  public void setTransaction(Transaction transaction) {
    setAmount(transaction.getAmount());
    setDate(transaction.getDate());
    setDescription(transaction.getDescription());
  }

  private void setAmount(float amount) {    
    if (amount < 0) {
      sign
        .withColor(CommonColors.MINUS.getColor())
        .setText("-");
      sentenceConnector.setText("outcome on");
    }
    else {
      sign
        .withColor(CommonColors.PLUS.getColor())
        .setText("+");
      sentenceConnector.setText("income on");
    }

    this.amount.setText(
      String.format("%.2f", Math.abs(amount))
    );
    amountEditor.setText(
      String.format("%.2f", amount)
    );
  }

  private void setDate(Date date) {
    final var dateStr =
      CommonDateFormats
      .EU_DATE_FORMAT_LONG
      .getFormatter()
      .format(date);

    this.date.setText(dateStr);
    dateEditor.setText(dateStr);
  }

  private void setDescription(String description) {
    this.description.setText(
      description
    );

    descriptionEditor.setText(description);
  }

  public Transaction getTransaction() {
    return transaction;
  }

  @Override public void setBackground(Color backgroundColor) {
    super.setBackground(backgroundColor);
    if (amountPanel != null) amountPanel.setBackground(backgroundColor); 
    if (datePanel != null) datePanel.setBackground(backgroundColor); 
    if (descriptionPanel != null) descriptionPanel.setBackground(backgroundColor);
    if (amount != null) amount.setBackground(backgroundColor);
    if (sentenceConnector != null) sentenceConnector.setBackground(backgroundColor);
    if (date != null) date.setBackground(backgroundColor);
    if (description != null) description.setBackground(backgroundColor);
    if (actionPanel != null) actionPanel.setBackground(backgroundColor);
    if (middlePanel != null) middlePanel.setBackground(backgroundColor);
    if (middleUpperPanel != null) middleUpperPanel.setBackground(backgroundColor);
    if (editButton != null) editButton.setBackground(backgroundColor);
    if (deleteButton != null) deleteButton.setBackground(backgroundColor);
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

    horizontalBox = Box.createHorizontalBox();
    add(horizontalBox, BorderLayout.WEST);


    horizontalBox.add(
      Box.createRigidArea(
        new Dimension(CommonPaddings.CARD_HORIZONTAL_PADDING.getPadding(), 0)
      )
    );

    sign.setPreferredSize(new Dimension(50, 50));
    horizontalBox.add(sign);

    horizontalBox.add(middlePanel);

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
        actionPanel.setCurrentAction(CardActions.CONFIRM);
      }
    );

    deleteButton.addActionListener(
      e -> {
        try {
          BalanceModelManager
            .getInstance()
            .deleteTransaction(this.transaction);
        }
        catch (ModelEditFailedException exception) {
          return;
        }

        EventsBroker
          .getInstance()
          .getDeleteEvent()
          .notifyAllObservers(
            new ArrayList<>() {{
              add(getTransaction());
            }}
          );

        setVisible(false);
      }
    );

    confirmButton.addActionListener(
      e -> {
        if (!amountEditor.isValidText() || !dateEditor.isValidText() || !descriptionEditor.isValidText()) {
          return;
        }

        float amount = Float.parseFloat(amountEditor.getText());
        String description = descriptionEditor.getText();
        Date date;

        try {
          date = CommonDateFormats.EU_DATE_FORMAT_LONG.getFormatter().parse(dateEditor.getText());
        }
        catch (ParseException exception) {
          return;
        }


        final Transaction editedTransaction;

        try {
          editedTransaction = BalanceModelManager
            .getInstance()
            .editTransaction(getTransaction(), amount, date, description);
        }
        catch (ModelEditFailedException exception) {
          exception.printStackTrace();
          return;
        }
        
        setVisible(false);

        EventsBroker
          .getInstance()
          .getAddEvent()
          .notifyAllObservers(
            new ArrayList<>() {{
              add(editedTransaction);
            }}
          );
      }
    );
  }
}
