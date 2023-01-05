package ui.components;

import ui.core.*;
import assets.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.*;

/**
 * Panel that stays in the background of the window.
 */
public class BasePanel extends JPanel implements IComponent {
  private static final int PADDING = 10;

  public BasePanel() {
    super();
    
    composeView();
  }
  
  @Override
  public void composeView() {
    setPreferredSize(
      new Dimension(
        CommonSizes.WINDOW_WIDTH.getSize(),
        CommonSizes.WINDOW_HEIGHT.getSize()
      )
    );

    setLayout(
      new BorderLayout()
    );

    // setBorder(
    //   BorderFactory.createEmptyBorder(
    //     PADDING,
    //     PADDING,
    //     PADDING,
    //     PADDING
    //   )
    // );

    setBackground(CommonColors.BACKGROUND.toColor());

    add(
      new UpperPanel(),
      BorderLayout.NORTH
    );

    add(
      new LowerPanel(),
      BorderLayout.SOUTH
    );
  }
}
