package ui.components.containers;

import ui.core.*;
import assets.*;
import tunable.*;

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
      CommonDimensions.WINDOW.getDimension()
    );

    setLayout(
      new BorderLayout()
    );

    setBackground(CommonColors.BACKGROUND.getColor());

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
