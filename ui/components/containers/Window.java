package ui.components.containers;

import tunable.*;
import ui.components.clickable.*;
import ui.core.*;
import persistence.*;
import print.PrintableBalance;
import export.*;
import model.core.*;
import model.events.EventsBroker;

import java.util.*;
import java.util.concurrent.CancellationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.table.TableModel;

import java.awt.print.*;

import org.jopendocument.dom.spreadsheet.SpreadSheet;

/**
 * Window manages the content area and the menu bar.
 */
public class Window extends JFrame implements IComponent {
  private static final float DEFAULT_FONT_SIZE = 36;
  private static final String HOME_DIRECTORY = System.getProperty("user.home");
  private final MenuItem saveItem;
  private final MenuItem loadItem;
  private final MenuItem txtItem;
  private final MenuItem csvItem;
  private final MenuItem openDocumentItem;
  private final MenuItem printerItem;
  private final SaveFileDialog saveFileDialog;
  private final SaveFileDialog saveTxtDialog;
  private final SaveFileDialog saveCsvDialog;
  private final SaveFileDialog saveOdsDialog;
  private final LoadFileDialog loadFileDialog;
  private IRecordFormatter recordFormatter;

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
    printerItem = new MenuItem("Printer");
    
    loadFileDialog = new LoadFileDialog(
      this,
      "Load " + CommonExtensions.MAIN_EXT.getExt() + " file",
      HOME_DIRECTORY,
      new ArrayList<>() {{
        add(CommonExtensions.MAIN_EXT.getExt());
        add(CommonExtensions.BACKUP_EXT.getExt());
      }}
    );
        
    saveFileDialog = new SaveFileDialog(
      this,
      "Save " + CommonExtensions.MAIN_EXT.getExt() + " file",
      HOME_DIRECTORY,
      new ArrayList<>() {{
        add(CommonExtensions.MAIN_EXT.getExt());
        add(CommonExtensions.BACKUP_EXT.getExt());
      }}
    );

    saveTxtDialog = new SaveFileDialog(
      this,
      "Save TXT file",
      HOME_DIRECTORY,
      new ArrayList<>() {{
        add(CommonExtensions.TXT_EXT.getExt());
      }}
    );

    saveCsvDialog = new SaveFileDialog(
      this,
      "Save CSV file",
      HOME_DIRECTORY,
      new ArrayList<>() {{
        add(CommonExtensions.CSV_EXT.getExt());
      }}
    );

    saveOdsDialog = new SaveFileDialog(
      this,
      "Save ODS file",
      HOME_DIRECTORY,
      new ArrayList<>() {{
        add(CommonExtensions.ODS_EXT.getExt());
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
                add(printerItem);
              }})
          );
        }},
        CommonColors.TOPBAR.getColor(),
        CommonColors.TEXT.getColor(),
        CommonFonts.TEXT_NORMAL.getFont().deriveFont(22f)
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
          ).create()
          .load();
        
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
          ).create()
          .save();

        if (!ok) {
          JOptionPane.showMessageDialog(this, "Failed operation");
          return;
        }
      }
    );

    txtItem.addActionListener(
      event -> {
        File file;

        try {
          file = saveTxtDialog.open();
        }
        catch (CancellationException exception) {
          return;
        }

        boolean ok = true;
        recordFormatter = new TxtLineFormatter();

        try (
          final var fileWriter = new FileWriter(file);
        ) {
          ok = new export.Formatter(
            BalanceModelManager.getInstance(),
            fileWriter,
            recordFormatter
          ).format();
        }
        catch (IOException exception) {
          ok = false;
        }

        if (!ok) {
          JOptionPane.showMessageDialog(this, "Failed while writing file.");
          return;
        }
      }
    );

    csvItem.addActionListener(
      event -> {
        File file;

        try {
          file = saveCsvDialog.open();
        }
        catch (CancellationException exception) {
          return;
        }

        boolean ok = true;
        recordFormatter = new CsvLineFormatter();

        try (
          final var fileWriter = new FileWriter(file);
        ) {
          ok = new export.Formatter(
            BalanceModelManager.getInstance(),
            fileWriter,
            recordFormatter
          ).format();
        }
        catch (IOException exception) {
          ok = false;
        }

        if (!ok) {
          JOptionPane.showMessageDialog(this, "Failed while writing file.");
          return;
        }
      }
    );

    printerItem.addActionListener(
      event -> {
        final var job = PrinterJob.getPrinterJob();
        job.setPrintable(new PrintableBalance());
        
        final var ok = job.printDialog();
        
        if (!ok) {
          return;
        }

        try {
          job.print();
        }
        catch (PrinterException exception) {
          JOptionPane.showMessageDialog(this, "Print failed.");
        }
      }
    );

    openDocumentItem.addActionListener(
      event -> {
        File file;

        try {
          file = saveOdsDialog.open();
        }
        catch (CancellationException exception) {
          return;
        }

        final var transactions = BalanceModelManager.getInstance().getTransactions();
        final var model = new TableModel() {
          public int getRowCount() { return transactions.size(); }
          
          public int getColumnCount() { return 3; }

          public String getColumnName(int c) {switch (c) {
            case 0: return "Amount";
            case 1: return "Date";
            case 2: return "Description";
            default: return "";
          }}
          
          public Class<?> getColumnClass(int c) {switch (c) {
            case 0: return Float.class;
            case 1: return Date.class;
            case 2: return String.class;
            default: return Object.class;
          }}

          public boolean isCellEditable(int r, int c) { return false; }
          
          public java.lang.Object getValueAt(int r, int c) {
            final var currentTrans = (Transaction) transactions.toArray()[r];
            switch (c) {
              case 0: return currentTrans.getAmount();
              case 1: return currentTrans.getDate();
              case 2: return currentTrans.getDescription();
              default: return "";
          }}
          
          public void setValueAt(Object arg0, int arg1, int arg2) { }
          public void addTableModelListener(javax.swing.event.TableModelListener arg0) { }
          public void removeTableModelListener(javax.swing.event.TableModelListener arg0) { }
        };

        try {
          SpreadSheet.createEmpty(model).saveAs(file);
        }
        catch (IOException exception) {
          JOptionPane.showMessageDialog(this, "Operation failed.");
        }
      }
    );
  }
}
