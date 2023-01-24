package ui.components.containers;

import tunable.*;
import ui.components.clickable.*;
import ui.core.*;
import persistence.*;

import java.util.*;
import java.util.concurrent.CancellationException;
import java.io.*;
import javax.swing.*;

import model.core.BalanceModel;
import model.core.BalanceModelManager;
import model.events.EventsBroker;

public class Window extends JFrame implements IComponent {
  private static final float DEFAULT_FONT_SIZE = 36;
  private static final String HOME_DIRECTORY = System.getProperty("user.home");
  private final MenuItem saveItem;
  private final MenuItem loadItem;
  private final MenuItem txtItem;
  private final MenuItem csvItem;
  private final MenuItem openDocumentItem;
  private final MenuItem pdfItem;
  private final MenuItem printerItem;
  private final SaveFileDialog saveFileDialog;
  private final LoadFileDialog loadFileDialog;

  public Window(String name) {
    super(name);
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    UIManager
    .getLookAndFeelDefaults()
    .put(
        "defaultFont",
        CommonFonts.TEXT_NORMAL.getFont().deriveFont(DEFAULT_FONT_SIZE)
      );
    
    setMinimumSize(CommonDimensions.WINDOW_MINIMUM_SIZE.getDimension());
    setLocationRelativeTo(null);
    
    saveItem = new MenuItem("Save");
    loadItem = new MenuItem("Load");
    txtItem = new MenuItem("TXT");
    csvItem = new MenuItem("CSV");
    openDocumentItem = new MenuItem("OpenDocument");
    pdfItem = new MenuItem("PDF");
    printerItem = new MenuItem("Printer");
    
    loadFileDialog = new LoadFileDialog(
      this,
      "Load .finance file",
      HOME_DIRECTORY,
      new ArrayList<>() {{
        add(".finance");
        add(".finance.bak");
      }}
    );
        
    saveFileDialog = new SaveFileDialog(
      this,
      "Save .finance file",
      HOME_DIRECTORY,
      new ArrayList<>() {{
        add(".finance");
        add(".finance.bak");
      }}
    );
    
    composeView();
    registerCallbacks();
    setVisible(true);
  }
  
  @Override public void composeView() {
    add(
      new BasePanel()
    );
    
    setJMenuBar(
      new MenuBar(
        new ArrayList<>() {{
          add(
            new Menu("File", CommonIcons.FOLDER.getIcon())
              .addItems(new ArrayList<>() {{
                add(saveItem);
                add(loadItem);
              }})
          );

          add(
            new Menu("Export", CommonIcons.EXPORT.getIcon())
              .addItems(new ArrayList<>() {{
                add(txtItem);
                add(csvItem);
                add(openDocumentItem);
              }})
          );

          add(
            new Menu("Print", CommonIcons.PRINT.getIcon())
              .addItems(new ArrayList<>() {{
                add(pdfItem);
                add(printerItem);
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

  @Override public void registerCallbacks() {
    loadItem.addActionListener(
      event -> {
        File file;

        try {
          file = loadFileDialog.open();
        }
        catch (CancellationException exception) {
          return;
        }

        boolean ok = new PersistenceBuilder()
          .withFile(file)
          .withModel(
            BalanceModelManager.getInstance()
          ).load();
        
        if (!ok) {
          JOptionPane.showMessageDialog(this, "Failed operation");
          return;
        }
        
        EventsBroker
          .getInstance()
          .getAddEvent()
          .notifyAllObservers(null);
      }
    );

    saveItem.addActionListener(
      event -> {
        File file;

        try {
          file = saveFileDialog.open();
        }
        catch (CancellationException exception) {
          return;
        }

        boolean ok = new PersistenceBuilder()
          .withFile(file)
          .withModel(
            BalanceModelManager.getInstance()
          ).save();

        if (!ok) {
          JOptionPane.showMessageDialog(this, "Failed operation");
          return;
        }
      }
    );
  }
}
