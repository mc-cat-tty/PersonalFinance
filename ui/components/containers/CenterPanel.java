package ui.components.containers;

import tunable.CommonColors;
import tunable.CommonDimensions;
import tunable.CommonPaddings;
import ui.components.clickable.MinimalScrollBar;
import ui.core.IComponent;

import java.awt.*;
import java.util.*;

import javax.sql.CommonDataSource;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet.ColorAttribute;

import model.core.*;
import model.events.EventsBroker;

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
    verticalBox.add(
      new Card(transaction)
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
  
  private void selectCards(Collection<Transaction> transactions) {
    final var children = verticalBox.getComponents();
    int cardCount = 0;

    for (final var component : children) {
      if (!(component instanceof Card)) {
        break;
      }

      final var card = (Card) component;

      if (transactions.stream().anyMatch(t -> t.equals(card.getTransaction()))) {
        card.setBackground(CommonColors.CARD_SELECTED.getColor());

        viewport.setViewPosition(
          new Point(
            0,
            (CommonDimensions.CARD.getHeight() + CommonPaddings.CARD_BOTTOM_PADDING.getPadding()) * cardCount - 20
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

    addTransactionCard(
      new Transaction(
        +123.45f,
        new Date(),
        "MOCK CARD: Lorem ipsum dolor sit amet, consectetur adipiscing elit."
      )
    );
    
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
          selectedTransactions -> selectCards(selectedTransactions)
        );
  }
}
