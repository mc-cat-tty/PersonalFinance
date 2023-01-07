package ui.components.containers;

import assets.*;
import tunable.*;
import ui.components.text.FlatText;
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

  private final FlatText plusMinus;
  private final FlatText balance;
  private final FlatText dateStart;
  private final FlatText dateEnd;

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
      RADIUS,
      CommonColors.CARD.getColor()
    );

    setInsets(
      new InsetsUIResource(
        30,  // empiric
        5,
        5,
        5
      )
    );

    plusMinus = new FlatText("+")
      .setColorMonadic(CommonColors.PLUS.getColor())
      .setOpacityMonadic(1f)
      .setFontMonadic(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(96f));
    
    balance = new FlatText("000,00 ")
      .setColorMonadic(CommonColors.TEXT.getColor())
      .setOpacityMonadic(1f)
      .setFontMonadic(CommonFonts.TEXT_NORMAL.getFont().deriveFont(96f));
    
    dateStart = new FlatText("01/01/'22")
      .setColorMonadic(CommonColors.TEXT.getColor())
      .setOpacityMonadic(1f)
      .setFontMonadic(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(36f));

    dateEnd = new FlatText("02/02/'23")
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

  @Override
  public void composeView() {
    setBorder(
      BorderFactory.createEmptyBorder(
        0,
        0,
        0,
        CommonPaddings.UPPER_PANEL_BOTTOM_PADDING.getPadding()
      )
    );

    add(plusMinus);

    add(balance);

    add(
      new FlatText("Your balance from ")
        .setColorMonadic(CommonColors.TEXT.getColor())
        .setOpacityMonadic(1f)
        .setFontMonadic(CommonFonts.TEXT_NORMAL.getFont().deriveFont(36f))
    );

    add(dateStart);

    add(
      new FlatText(" to ")
        .setColorMonadic(CommonColors.TEXT.getColor())
        .setOpacityMonadic(1f)
        .setFontMonadic(CommonFonts.TEXT_NORMAL.getFont().deriveFont(36f))
    );
    
    add(dateEnd);
  }
}
