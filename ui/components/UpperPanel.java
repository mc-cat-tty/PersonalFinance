package ui.components;

import assets.*;
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
  }
}
