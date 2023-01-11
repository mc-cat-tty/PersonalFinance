package laf.flat.containers;

import tunable.CommonColors;
import tunable.CommonDimensions;
import ui.core.IComponent;

import java.awt.*;
import java.util.Calendar;

import javax.sql.CommonDataSource;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import laf.flat.buttons.FlatScrollBar;
import model.core.*;

public class CenterPanel extends JScrollPane implements IComponent {
  private final JScrollBar scrollBar;
  private final JPanel viewportPanel;
  private final Box verticalBox;

  public CenterPanel() {
    super();

    scrollBar = new FlatScrollBar();
    
    viewportPanel = new JPanel();
    viewportPanel.setLayout(
      new FlowLayout()
    );
    viewportPanel.setBackground(CommonColors.BACKGROUND.getColor());
    verticalBox = Box.createVerticalBox();
    viewportPanel.add(verticalBox);

    composeView();
  }

  @Override public void composeView() {
    setBorder(
      new EmptyBorder(0, 0, 0, 0)
    );
    
    getViewport().setBackground(CommonColors.BACKGROUND.getColor());
    getViewport().add(viewportPanel);
    setVerticalScrollBar(scrollBar);
    setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollBar.setUnitIncrement(30);

    verticalBox.add(new Card(new Transaction(
      1234.45f,
      Calendar.getInstance().getTime(),
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
    )));
  
    verticalBox.add(new Card(new Transaction(
      1234.45f,
      Calendar.getInstance().getTime(),
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
    )));

    verticalBox.add(new Card(new Transaction(
      1234.45f,
      Calendar.getInstance().getTime(),
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
    )));

    verticalBox.add(new Card(new Transaction(
      1234.45f,
      Calendar.getInstance().getTime(),
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
    )));

    verticalBox.add(new Card(new Transaction(
      1234.45f,
      Calendar.getInstance().getTime(),
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
    )));

    verticalBox.add(new Card(new Transaction(
      1234.45f,
      Calendar.getInstance().getTime(),
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
    )));

    verticalBox.add(new Card(new Transaction(
      1234.45f,
      Calendar.getInstance().getTime(),
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
    )));

    verticalBox.add(new Card(new Transaction(
      1234.45f,
      Calendar.getInstance().getTime(),
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
    )));
  }
}
