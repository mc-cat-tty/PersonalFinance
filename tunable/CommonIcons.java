package tunable;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public enum CommonIcons {
  FOLDER ("assets/folder.png"),
  EXPORT ("assets/export.png"),
  PRINT ("assets/print.png"),
  EDIT ("assets/edit.png"),
  DELETE ("assets/delete.png"),
  DROPDOWN ("assets/dropdown.png"),
  DROPUP ("assets/dropup.png");

  private final ImageIcon icon;

  private CommonIcons(String iconPath) {
    final var iconStream = getClass().getClassLoader().getResourceAsStream(iconPath);

    if (iconStream == null) {
      throw new IllegalArgumentException(iconPath + " is not valid");
    }
    
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
