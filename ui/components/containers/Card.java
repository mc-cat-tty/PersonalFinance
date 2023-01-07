package ui.components.containers;

import ui.core.*;
import tunable.*;

import java.awt.*;
import javax.swing.*;

/**
 * Element of a scrollable list.
 */
public class Card extends RoundedPanel implements IComponent {
  public Card() {
    super(
      new Dimension(0, 0),
      10
    );
  }
}
