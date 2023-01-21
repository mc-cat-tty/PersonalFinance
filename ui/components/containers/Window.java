package ui.components.containers;

import tunable.*;
import ui.core.*;

import java.util.ArrayList;

import javax.swing.*;

import ui.components.buttons.*;

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
      new MenuBar(
        new ArrayList<>() {{
          add(
            new Menu("File", CommonIcons.FOLDER.getIcon())
              .addItems(new ArrayList<>() {{
                add(new MenuItem("Save"));
                add(new MenuItem("Load"));
              }})
          );

          add(
            new Menu("Export", CommonIcons.EXPORT.getIcon())
              .addItems(new ArrayList<>() {{
                add(new MenuItem("TXT"));
                add(new MenuItem("CSV"));
                add(new MenuItem("OpenDocument"));
              }})
          );

          add(
            new Menu("Print", CommonIcons.PRINT.getIcon())
              .addItems(new ArrayList<>() {{
                add(new MenuItem("PDF"));
                add(new MenuItem("Printer"));
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
