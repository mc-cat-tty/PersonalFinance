package ui.core;

import ui.components.containers.Window;

/**
 * Parent class for the GUI. Manages views, model and control logic.
 * @author Francesco Mecatti
 */
public class UI {
  private Window mainWindow;

  public UI(String windowName) {
    mainWindow = new Window(windowName);
  }
}