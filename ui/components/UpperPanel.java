package ui.components;

import assets.*;
import tunable.*;
import ui.core.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.InsetsUIResource;

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

    setInsets(
      new InsetsUIResource(
        30,  // empiric
        5,
        5,
        5
      )
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
      new FlatText("+")
        .setColorMonadic(CommonColors.PLUS.toColor())
        .setOpacityMonadic(1f)
        .setFontMonadic(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(96f))
    );

    add(
      new FlatText("123,45 € ")
        .setColorMonadic(CommonColors.TEXT.toColor())
        .setOpacityMonadic(1f)
        .setFontMonadic(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(96f))
    );

    add(
      new FlatText("Your balance from ")
        .setColorMonadic(CommonColors.TEXT.toColor())
        .setOpacityMonadic(1f)
        .setFontMonadic(CommonFonts.TEXT_NORMAL.getFont().deriveFont(36f))
    );

    add(
      new FlatText("01/01/'22")
        .setColorMonadic(CommonColors.TEXT.toColor())
        .setOpacityMonadic(1f)
        .setFontMonadic(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(36f))
    );

    add(
      new FlatText(" to ")
        .setColorMonadic(CommonColors.TEXT.toColor())
        .setOpacityMonadic(1f)
        .setFontMonadic(CommonFonts.TEXT_NORMAL.getFont().deriveFont(36f))
    );
    
    add(
      new FlatText("02/02/'23")
        .setColorMonadic(CommonColors.TEXT.toColor())
        .setOpacityMonadic(1f)
        .setFontMonadic(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(36f))
    );
  }
}
