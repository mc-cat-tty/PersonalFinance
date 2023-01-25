package export;

import model.core.BalanceModel;
import model.core.IReadableModel;

import java.io.*;

public class Formatter {
  private final IReadableModel model;
  private final Writer writer;
  private final ILineFormatter lineFormatter;

  public Formatter(
    IReadableModel model,
    Writer writer,
    ILineFormatter lineFormatter
  ) {
    this.model = model;
    this.writer = writer;
    this.lineFormatter = lineFormatter;
  }

  public boolean format() {
    for (final var transaction : model.getTransactions()) {
      try {
        writer.write(lineFormatter.transform(transaction));
        writer.flush();
      } catch (IOException e) {
        e.printStackTrace();
        return false;
      }
    }

    return true;
  }
}
