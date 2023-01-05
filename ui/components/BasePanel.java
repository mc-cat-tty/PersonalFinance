package ui.components;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.*;
import java.awt.*;

public class BasePanel extends JPanel {
  private static final int PADDING = 10;

  public BasePanel() {
    super(
      new BorderLayout()
    );

    setBorder(
      BorderFactory.createEmptyBorder(
        PADDING,
        PADDING,
        PADDING,
        PADDING
      )
    );
  }
}
