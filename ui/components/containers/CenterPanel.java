package ui.components.containers;

import tunable.CommonColors;
import tunable.CommonDimensions;
import tunable.CommonPaddings;
import ui.components.clickable.MinimalScrollBar;
import ui.core.IComponent;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.core.*;
import model.events.EventsBroker;

/**
 * Displays a list of cards. It manages the viewport and the search bar. 
 */
public class CenterPanel extends JScrollPane implements IComponent {
  private final JScrollBar scrollBar;
  private final JPanel viewportPanel;
  private final Box verticalBox;

  public CenterPanel() {
    super();

    scrollBar = new MinimalScrollBar();
    
    viewportPanel = new JPanel();
    viewportPanel.setLayout(
      new FlowLayout()
    );
    viewportPanel.setBackground(CommonColors.BACKGROUND.getColor());
    verticalBox = Box.createVerticalBox();
    viewportPanel.add(verticalBox);

    composeView();
    registerCallbacks();
  }

  private void addTransactionCard(Transaction transaction) {
    final var card = new Card(transaction);

    verticalBox.add(card);

    // Make card editable in mutual exclusion
    card.getEditButton().addActionListener(
      event -> {
        for (final var o : verticalBox.getComponents()) {
          if (o instanceof Card &&  (Card) o != event.getSource()) {
            ((Card) o).setView(Card.VIEW_KEY);
          }
        }
      }
    );
  }

  private void removeAllCards() {
    verticalBox.removeAll();
  }

  private void reloadModel(Collection<Transaction> transactions) {
    removeAllCards();

    for (final var t : transactions) {
      addTransactionCard(t);
    }

    repaint();
    revalidate();
  }
  
  private void selectCard(Transaction transaction) {
    final var children = verticalBox.getComponents();
    int cardCount = 0;

    for (final var component : children) {
      if (!(component instanceof Card)) {
        break;
      }

      final var card = (Card) component;

      if (transaction.equals(card.getTransaction())) {
        card.setBackground(CommonColors.CARD_SELECTED.getColor());

        viewport.setViewPosition(
          new Point(
            0,
            (CommonDimensions.CARD.getHeight() + CommonPaddings.CARD_BOTTOM_PADDING.getPadding()) * cardCount - 15
          )
        );
      }

      cardCount++;
    }
  }

  @Override public void composeView() {
    setBorder(
      new EmptyBorder(0, 0, 0, 0)
    );
    
    getViewport().setBackground(CommonColors.BACKGROUND.getColor());
    getViewport().add(viewportPanel);
    setVerticalScrollBar(scrollBar);
    setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollBar.setUnitIncrement(30);
  }

  @Override public void registerCallbacks() {
    EventsBroker
      .getInstance()
      .getFilterEvent()
      .attachObserver(
        filteredTransactions -> reloadModel(filteredTransactions)
      );
    
    EventsBroker
      .getInstance()
      .getSelectionEvent()
      .attachObserver(
        selectedTransactions -> selectCard(selectedTransactions)
      );
  }
}
