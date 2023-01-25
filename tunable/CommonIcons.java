package tunable;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
    final var iconStream = getClass().getClassLoader().getResourceAsStream(iconPath);

    try {
      this.icon = new ImageIcon(ImageIO.read(iconStream));
    }
    catch (IOException e) {
      throw new IllegalArgumentException(iconPath + " is not valid");
    }
  }

  public ImageIcon getIcon() {
    return icon;
  }
}
