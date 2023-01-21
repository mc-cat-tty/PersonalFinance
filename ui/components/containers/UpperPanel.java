package ui.components.containers;

import assets.*;
import ui.components.text.TunableText;
import tunable.*;
import ui.core.*;

import java.text.DateFormat;
import java.util.Date;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.text.Position;

/**
 * Upper panel showing balance.
 */
public class UpperPanel extends RoundedPanel implements IComponent {
  private final static int RADIUS = 80;

  private final TunableText plusMinus;
  private final TunableText balance;
  private final TunableText dateStart;
  private final TunableText dateEnd;

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
      .setColorMonadic(CommonColors.PLUS.getColor())
      .setOpacityMonadic(1f)
      .setFontMonadic(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(96f));
    
    balance = new TunableText("000,00 ")
      .setColorMonadic(CommonColors.TEXT.getColor())
      .setOpacityMonadic(1f)
      .setFontMonadic(CommonFonts.TEXT_NORMAL.getFont().deriveFont(96f));
    
    dateStart = new TunableText("01/01/'22")
      .setColorMonadic(CommonColors.TEXT.getColor())
      .setOpacityMonadic(1f)
      .setFontMonadic(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(36f));

    dateEnd = new TunableText("02/02/'23")
      .setColorMonadic(CommonColors.TEXT.getColor())
      .setOpacityMonadic(1f)
      .setFontMonadic(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(36f));

    composeView();
  }

  public void setBalance(float balance) {
    if (balance < 0f) {
      plusMinus
        .setColorMonadic(CommonColors.MINUS.getColor())
        .setText("-");
      
      balance *= -1;  // remove sign
    }
    else {
      plusMinus
        .setColorMonadic(CommonColors.PLUS.getColor())
        .setText("+");
    }

    this.balance.setText(Float.toString(balance));
  }

  public void setDateStart(Date dateStart) {
    this.dateStart.setText(dateStart.toString());
  }

  public void setDateEnd(Date dateEnd) {
    this.dateEnd.setText(dateEnd.toString());
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

    internalPanel.add(balance);

    internalPanel.add(
      new TunableText("Your balance from ")
        .setColorMonadic(CommonColors.TEXT.getColor())
        .setOpacityMonadic(1f)
        .setFontMonadic(CommonFonts.TEXT_NORMAL.getFont().deriveFont(36f))
    );

    internalPanel.add(dateStart);

    internalPanel.add(
      new TunableText(" to ")
        .setColorMonadic(CommonColors.TEXT.getColor())
        .setOpacityMonadic(1f)
        .setFontMonadic(CommonFonts.TEXT_NORMAL.getFont().deriveFont(36f))
    );
    
    internalPanel.add(dateEnd);
  }
}
