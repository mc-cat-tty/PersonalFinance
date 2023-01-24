package ui.components.containers;

import java.awt.*;
import java.io.*;
import java.util.concurrent.CancellationException;

public class LoadFileDialog extends FileDialog {
  private final java.util.List<String> extensions;

  public LoadFileDialog(
    Frame parent,
    String name,
    String currentPath,
    java.util.List<String> extensions
  ) {
    super(parent, name, FileDialog.LOAD);
    this.extensions = extensions;

    setDirectory(currentPath);
    setFilenameFilter((file, filename) -> extensions.stream().anyMatch(ext -> filename.endsWith(ext)));
  }

  public File open() throws CancellationException {
    setVisible(true);

    if (getFile() == null) {
      throw new CancellationException("Cancelled operation");
    }

    return new File(getDirectory(), getFile());
  }
}
