package ui.core;

import ui.components.containers.Window;

import java.io.*;
import java.util.*;
import java.util.logging.LogManager;

import javax.print.attribute.standard.PresentationDirection;

import model.core.BalanceModelManager;
import model.events.EventsBroker;
import persistence.Persistence;
import persistence.PersistenceBuilder;

/**
 * Parent class for the GUI. Manages views, model and control logic.
 * @author Francesco Mecatti
 */
public class UI {
  private Persistence autosave;

  public UI(String windowName) {
    new Window(windowName);

    this.autosave = new PersistenceBuilder()
      .withFile(
        new File(
          System.getProperty("user.dir"),
          "autosaved.finance.bak"
        )
      )
      .withModel(BalanceModelManager.getInstance())
      .create();

    if (!autosave.load()) {
      System.out.println("Failed to load backup file");
    }
    else {
      EventsBroker
        .getInstance()
        .getAddEvent()
        .notifyAllObservers(null);
    }

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
}