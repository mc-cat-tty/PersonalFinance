package ui.components;

import assets.CommonColors;
import assets.CommonPaddings;
import assets.CommonSizes;
import ui.core.*;
import javax.swing.*;

/**
 * Lower panel allowing interaction.
 */
public class LowerPanel extends RoundedPanel implements IComponent {
  public LowerPanel() {
    super(
      0,
      80,
      CommonSizes.LOWER_PANEL_WIDTH.getSize(),
      CommonSizes.LOWER_PANEL_HEIGHT.getSize() + 80,
      80,
      CommonColors.CARD.toColor()
    );
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
  }
}
