package ui.components;

import assets.*;
import tunable.*;
import ui.core.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Upper panel showing balance.
 */
public class UpperPanel extends RoundedPanel implements IComponent {
  public UpperPanel() {
    super(
      0,
      -80,
      CommonSizes.UPPER_PANEL_WIDTH.getSize(),
      CommonSizes.UPPER_PANEL_HEIGHT.getSize() + 80,
      80,
      CommonColors.CARD.toColor()
    );

    composeView();
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

    add(
      new FlatText("Your balance from 01/01/'22 to 02/02/'23")
        .setColorMonadic(CommonColors.TEXT.toColor())
        .setOpacityMonadic(1f)
        .setFontMonadic(CommonFonts.TEXT_NORMAL.getFont())
    );
  }
}
