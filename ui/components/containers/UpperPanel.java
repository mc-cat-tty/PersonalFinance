package ui.components.containers;

import ui.components.text.TunableText;
import tunable.*;
import ui.core.*;

import java.util.Date;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import model.core.Transaction;
import model.events.Event;
import model.events.EventsBroker;

/**
 * Upper panel showing balance.
 */
public class UpperPanel extends RoundedPanel implements IComponent {
  private final static int RADIUS = 80;

  private final TunableText plusMinus;
  private final TunableText amount;
  private final TunableText startDate;
  private final TunableText endDate;
  private float balance;

  public UpperPanel() {
    super(
      new Point(
        0,
        -RADIUS
      ),
      new Dimension(
        CommonDimensions.UPPER_PANEL.getWidth(),
        CommonDimensions.UPPER_PANEL.getHeight() + RADIUS
      ),
      new Point(
        0,
        -RADIUS
      ),
      RADIUS,
      CommonColors.CARD.getColor()
    );

    plusMinus = new TunableText("+")
      .withColor(CommonColors.PLUS.getColor())
      .withOpacity(1f)
      .withFont(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(96f));
    
    amount = new TunableText("000.00 ")
      .withColor(CommonColors.TEXT.getColor())
      .withOpacity(1f)
      .withFont(CommonFonts.TEXT_NORMAL.getFont().deriveFont(96f));
    
    startDate = new TunableText("01/01/'22")
      .withColor(CommonColors.TEXT.getColor())
      .withOpacity(1f)
      .withFont(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(36f));

    endDate = new TunableText("02/02/'23")
      .withColor(CommonColors.TEXT.getColor())
      .withOpacity(1f)
      .withFont(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(36f));

    composeView();
    registerCallbacks();
  }

  public void setAmount(float amount) {
    if (amount < 0f) {
      plusMinus
        .withColor(CommonColors.MINUS.getColor())
        .setText("-");
      
      amount *= -1;  // remove sign
    }
    else {
      plusMinus
        .withColor(CommonColors.PLUS.getColor())
        .setText("+");
    }

    this.amount.setText(
      String.format("%06.02f ", amount)
    );
  }

  public void setStartDate(Date startDate) {
    this.startDate.setText(
      CommonDateFormats.US_DATE_FORMAT_SHORT.getFormatter().format(startDate)
    );
  }

  public void setEndDate(Date endDate) {
    this.endDate.setText(
      CommonDateFormats.US_DATE_FORMAT_SHORT.getFormatter().format(endDate)
    );
  }

  @Override public void composeView() {
    setBorder(
      new EmptyBorder(
        0,
        0,
        CommonPaddings.UPPER_PANEL_BOTTOM_PADDING.getPadding(),
        0
      )
    );

    final var internalPanel = new JPanel();
    internalPanel.setBackground(CommonColors.CARD.getColor());
    internalPanel.setBorder(
      new EmptyBorder(
        30,
        5,
        5,
        5
      )
    );
    add(internalPanel);

    
    internalPanel.add(plusMinus);

    internalPanel.add(amount);

    internalPanel.add(
      new TunableText("Your balance from ")
        .withColor(CommonColors.TEXT.getColor())
        .withOpacity(1f)
        .withFont(CommonFonts.TEXT_NORMAL.getFont().deriveFont(36f))
    );

    internalPanel.add(startDate);

    internalPanel.add(
      new TunableText(" to ")
        .withColor(CommonColors.TEXT.getColor())
        .withOpacity(1f)
        .withFont(CommonFonts.TEXT_NORMAL.getFont().deriveFont(36f))
    );
    
    internalPanel.add(endDate);
  }

  @Override public void registerCallbacks() {
    EventsBroker
      .getInstance()
      .getFilterEvent()
      .attachObserver(
        transactions -> {
          balance = 0;

          for (final Transaction t : transactions) {
            balance += t.getAmount();
          }

          setAmount(balance);
        }
      );

    EventsBroker
      .getInstance()
      .getFilterDateEvent()
      .attachObserver(
        (startDate, endDate) -> {setStartDate(startDate); setEndDate(endDate);}
      );
    
    EventsBroker
      .getInstance()
      .getDeleteEvent()
      .attachObserver(
        transactions -> {
          for (final var t : transactions) {
            balance -= t.getAmount();
          }

          setAmount(balance);
        }
      );
  }
}
