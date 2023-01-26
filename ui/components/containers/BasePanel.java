package ui.components.containers;

import ui.core.*;
import tunable.*;

import java.awt.*;
import javax.swing.*;

/**
 * Panel that stays in the background of the window.
 */
public class BasePanel extends JPanel implements IComponent {
  public BasePanel() {
    super();
    
    composeView();
  }
  
  @Override public void composeView() {
    setLayout(
      new BorderLayout()
    );

    setBackground(CommonColors.BACKGROUND.getColor());

    add(
      new UpperPanel(),
      BorderLayout.NORTH
    );

    var centerPanel = new JPanel(
      new BorderLayout()
    );
    centerPanel.setBackground(CommonColors.BACKGROUND.getColor());

    add(
      centerPanel,
      BorderLayout.CENTER
    );

    centerPanel.add(
      new SearchPanel(),
      BorderLayout.NORTH
    );

    centerPanel.add(
      new CenterPanel()
    );

    add(
      new LowerPanel(),
      BorderLayout.SOUTH
    );
  }
}
