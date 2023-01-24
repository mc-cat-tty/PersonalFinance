package ui.components.containers;

import java.awt.*;
import java.io.*;
import java.util.concurrent.CancellationException;

public class SaveFileDialog extends FileDialog {
  private final java.util.List<String> extensions;

  public SaveFileDialog(
    Frame parent,
    String name,
    String currentPath,
    java.util.List<String> extensions
  ) {
    super(parent, name, FileDialog.SAVE);
    this.extensions = extensions;

    setDirectory(currentPath);
  }

  public File open() throws CancellationException {
    setVisible(true);

    if (getFile() == null) {
      throw new CancellationException("Cancelled operation");
    }

    return new File(getDirectory(), addExtension(getFile()));
  }

  private String addExtension(String filename) {
    if (extensions.stream().anyMatch(ext -> filename.endsWith(ext))) {
      return filename;
    }

    return filename + extensions.get(0);
  }
}
