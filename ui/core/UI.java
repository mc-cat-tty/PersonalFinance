package ui.core;

import ui.components.containers.*;
import ui.utils.FlatConfirmDialogUtiliy;
import ui.utils.FlatConfirmDialogUtiliy.Choices;
import tunable.*;

import java.io.*;
import java.util.*;

import model.core.BalanceModelManager;
import model.events.EventsBroker;
import persistence.Persistence;

/**
 * Parent class for the GUI. Manages views, model and control logic.
 * @author Francesco Mecatti
 */
public class UI {
  private final Persistence autosave;
  private final Window window;
  private final FlatConfirmDialogUtiliy restoreDialogBuilder;

  public UI(String windowName) {
    window = new Window(windowName);

    restoreDialogBuilder = new FlatConfirmDialogUtiliy(
      window,
      "Restore Autosave",
      "An automatic backup file has been found.\nDo you want to restore it?"
    );

    final var autosaveFile = new File(
      System.getProperty("java.io.tmpdir"),
      "autosaved" + CommonExtensions.BACKUP_EXT.getExt()
    );
    
    this.autosave = new Persistence(BalanceModelManager.getInstance(), autosaveFile);

    restoreAutosave(autosaveFile);


    new Timer("Autosave", true).schedule(
      new TimerTask() {
        @Override public void run() {
          autosave.save();
        }
      },
      0,  // no dalay for first execution
      1000  // 1 second period time
    );
  }

  private void restoreAutosave(File autosaveFile) {  
    if (!autosaveFile.exists()) {
      return;
    }
    
    restoreDialogBuilder.getDialog().setVisible(true);

    // if user doesn't want to restore backup
    if (restoreDialogBuilder.getChoice() != Choices.OK_CHOICE) {
      return;
    }

    if (!autosave.load()) {
      System.err.println("Failed to load backup file");
    }
    else {  // notify add change to all listeners
      EventsBroker
        .getInstance()
        .getAddEvent()
        .notifyAllObservers(null);
    }
  }
}