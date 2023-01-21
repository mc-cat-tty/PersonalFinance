package ui.components.clickable;

import tunable.*;
import ui.core.*;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ui.components.containers.*;


public class CardActionPanel extends RoundedPanel implements IComponent {
  public enum CardAction {
    EDIT_DELETE,
    CONFIRM;
  }

  private final AbstractButton editButton;
  private final AbstractButton deleteButton;
  private final AbstractButton confirmButton;
  private JPanel cards;
  private CardAction currentAction;

  public CardActionPanel(
    AbstractButton editButton,
    AbstractButton deleteButton,
    AbstractButton confirmButton,
    int radius
  ) {
    super(
      new Point(0, 0),
      CommonDimensions.CARD_ACTIONS.getDimension(),
      new Point(0, 0),
      radius,
      CommonColors.CARD.getColor()
    );
    this.editButton = editButton;
    this.deleteButton = deleteButton;
    this.confirmButton = confirmButton;
    this.currentAction = CardAction.EDIT_DELETE;
    composeView();
  }

  public CardAction getCurrentAction() {
    return currentAction;
  }

  public void setCurrentAction(CardAction action) {
    this.currentAction = action;
    final var layout = (CardLayout) cards.getLayout();
    layout.show(cards, action.name());
  }

  @Override public void composeView() {
    setBorder(
      new EmptyBorder(
        26,
        0,
        0,
        CommonPaddings.CARD_HORIZONTAL_PADDING.getPadding()
      )
    );
    
    cards = new JPanel(new CardLayout());
    cards.setBackground(CommonColors.CARD.getColor());
    add(cards);

    final var editDeleteCard = new JPanel();
    editDeleteCard.setLayout(
      new BorderLayout(
        CommonPaddings.CARD_HORIZONTAL_PADDING.getPadding(),
        0
      )
    );

    editDeleteCard.setBackground(CommonColors.CARD.getColor());

    editDeleteCard.add(
      editButton,
      BorderLayout.WEST
    );

    editDeleteCard.add(
      deleteButton,
      BorderLayout.CENTER
    );

    cards.add(editDeleteCard, CardAction.EDIT_DELETE.name());
    cards.add(confirmButton, CardAction.CONFIRM.name());
  }
}
