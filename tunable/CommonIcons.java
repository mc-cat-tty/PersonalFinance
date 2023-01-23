package tunable;

import java.io.File;
import javax.swing.*;

public enum CommonIcons {
  FOLDER (".." + File.separator + "assets" + File.separator + "folder.png"),
  EXPORT (".." + File.separator + "assets" + File.separator + "export.png"),
  PRINT (".." + File.separator + "assets" + File.separator + "print.png"),
  EDIT (".." + File.separator + "assets" + File.separator + "edit.png"),
  DELETE (".." + File.separator + "assets" + File.separator + "delete.png"),
  DROPDOWN (".." + File.separator + "assets" + File.separator + "dropdown.png"),
  DROPUP (".." + File.separator + "assets" + File.separator + "dropup.png");

  private final ImageIcon icon;

  private CommonIcons(String iconPath) {
    final var url = getClass().getResource(iconPath);
    if (url == null) {
      throw new IllegalArgumentException(iconPath + " is not valid");
    }
    else {
      this.icon = new ImageIcon(url);
    }
  }

  public ImageIcon getIcon() {
    return icon;
  }
}
