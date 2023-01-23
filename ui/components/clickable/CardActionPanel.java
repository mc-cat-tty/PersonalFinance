package ui.components.clickable;

import tunable.*;
import ui.core.*;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ui.components.containers.*;
import ui.behaviour.*;

public class CardActionPanel extends RoundedPanel implements IComponent {
  private final AbstractButton editButton;
  private final AbstractButton deleteButton;
  private final AbstractButton confirmButton;
  private JPanel cards;
  private JPanel editDeleteCard;
  private CardActions currentAction;

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
    this.currentAction = CardActions.EDIT_DELETE;

    composeView();
    setBackground(CommonColors.CARD.getColor());
  }

  public CardActions getCurrentAction() {
    return currentAction;
  }

  public void setCurrentAction(CardActions action) {
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
    add(cards);

    editDeleteCard = new JPanel();
    editDeleteCard.setLayout(
      new BorderLayout(
        CommonPaddings.CARD_HORIZONTAL_PADDING.getPadding(),
        0
      )
    );

    editDeleteCard.add(
      editButton,
      BorderLayout.WEST
    );

    editDeleteCard.add(
      deleteButton,
      BorderLayout.CENTER
    );

    cards.add(editDeleteCard, CardActions.EDIT_DELETE.name());
    cards.add(confirmButton, CardActions.CONFIRM.name());
  }

  @Override public void setBackground(Color backgroundColor) {
    super.setBackground(backgroundColor);
    if (cards != null) cards.setBackground(backgroundColor);
    if (editDeleteCard != null) editDeleteCard.setBackground(backgroundColor);
  }
}
