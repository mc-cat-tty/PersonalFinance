package tunable;

import java.io.File;
import javax.swing.*;

public enum CommonIcons {
  FOLDER ("assets" + File.separator + "folder.png"),
  EXPORT ("assets" + File.separator + "export.png"),
  PRINT ("assets" + File.separator + "print.png"),
  EDIT ("assets" + File.separator + "edit.png"),
  DELETE ("assets" + File.separator + "delete.png"),
  DROPDOWN ("assets" + File.separator + "dropdown.png"),
  DROPUP ("assets" + File.separator + "dropup.png");

  private final ImageIcon icon;

  private CommonIcons(String iconPath) {
    final var url = getClass().getClassLoader().getResource(iconPath);

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
