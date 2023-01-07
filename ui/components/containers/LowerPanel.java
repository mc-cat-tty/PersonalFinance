package ui.components.containers;

import ui.components.buttons.RoundedButton;
import ui.core.*;
import tunable.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.InsetsUIResource;

/**
 * Lower panel allowing interaction.
 */
public class LowerPanel extends RoundedPanel implements IComponent {
  private static final int RADIUS = 80;
  private final RoundedButton plusButton;
  private final RoundedButton minusButton;

  public LowerPanel() {
    super(
      new Point(
        0,
        RADIUS
      ),
      new Dimension(
        CommonSizes.LOWER_PANEL_WIDTH.getSize(),
        CommonSizes.LOWER_PANEL_HEIGHT.getSize() + RADIUS
      ),
      RADIUS,
      CommonColors.CARD.toColor()
    );

    setInsets(
      new InsetsUIResource(
        RADIUS + 10,
        10,
        10,
        10
      )
    );

    plusButton = new RoundedButton(
      "+",
      CommonColors.TEXTBOX.toColor(),
      CommonColors.BUTTON_PRESSED.toColor(),
      CommonColors.PLUS.toColor(),
      CommonFonts.TEXT_NORMAL
        .getFont()
        .deriveFont(64f),
      new Dimension(63, 63),
      20
    );

    minusButton = new RoundedButton(
      "-",
      CommonColors.TEXTBOX.toColor(),
      CommonColors.BUTTON_PRESSED.toColor(),
      CommonColors.MINUS.toColor(),
      CommonFonts.TEXT_NORMAL
        .getFont()
        .deriveFont(64f),
      new Dimension(63, 63),
      20
    );
    minusButton.setEnabled(false);

    composeView();
  }

  @Override
  public void composeView() {
    setBorder(
      BorderFactory.createEmptyBorder(
        0,
        0,
        0,
        CommonPaddings.LOWER_PANEL_UPPER_PADDING.getPadding()
      )
    );

    add(plusButton);
    add(minusButton);
  }
}
