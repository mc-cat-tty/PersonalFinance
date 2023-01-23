package ui.components.containers;

import ui.core.*;

import javax.swing.*;

import ui.components.containers.*;

import java.util.*;
import java.awt.CardLayout;

public class LayeredPanel extends JPanel implements IComponent {
  private final Map<String, JComponent> components;

  public LayeredPanel(Map<String, JComponent> components) {
    super();
    this.components = components;
    composeView();
  }

  public void setVisibleComponent(String componentKey) {
    final var layout = (CardLayout) getLayout();
    layout.show(this, componentKey);
  }

  @Override public void composeView() {
    setLayout(new CardLayout());

    for (final var entry : components.entrySet()) {
      add(entry.getValue(), entry.getKey());
    }
  }
}
