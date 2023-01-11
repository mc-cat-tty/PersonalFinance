package ui.components;

import tunable.*;
import ui.core.*;

import java.util.ArrayList;

import javax.swing.*;

import laf.flat.buttons.FlatMenu;
import laf.flat.buttons.FlatMenuBar;
import laf.flat.buttons.FlatMenuItem;

public class Window extends JFrame implements IComponent {
  private static final float DEFAULT_FONT_SIZE = 36;

  public Window(String name) {
    super(name);
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    composeView();

    UIManager
      .getLookAndFeelDefaults()
      .put(
        "defaultFont",
        CommonFonts.TEXT_NORMAL.getFont().deriveFont(DEFAULT_FONT_SIZE)
      );
    
    setMinimumSize(CommonDimensions.WINDOW_MINIMUM_SIZE.getDimension());
    setLocationRelativeTo(null);
    setVisible(true);
  }
  
  public void composeView() {
    add(
      new BasePanel()
    );
    
    setJMenuBar(
      new FlatMenuBar(
        new ArrayList<>() {{
          add(
            new FlatMenu("File", CommonIcons.FOLDER.getIcon())
              .addItems(new ArrayList<>() {{
                add(new FlatMenuItem("Save"));
                add(new FlatMenuItem("Load"));
              }})
          );

          add(
            new FlatMenu("Export", CommonIcons.EXPORT.getIcon())
              .addItems(new ArrayList<>() {{
                add(new FlatMenuItem("TXT"));
                add(new FlatMenuItem("CSV"));
                add(new FlatMenuItem("OpenDocument"));
              }})
          );

          add(
            new FlatMenu("Print", CommonIcons.PRINT.getIcon())
              .addItems(new ArrayList<>() {{
                add(new FlatMenuItem("PDF"));
                add(new FlatMenuItem("Printer"));
              }})
          );
        }},
        CommonColors.TOPBAR.getColor(),
        CommonColors.TEXT.getColor(),
        CommonFonts.TEXT_NORMAL.getFont().deriveFont(18f)
      )
    );

    pack();
  }
}