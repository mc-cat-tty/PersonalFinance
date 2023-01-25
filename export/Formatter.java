package export;

import model.core.BalanceModel;
import model.core.IReadableModel;

import java.io.*;

/**
 * A formatting utility that applies a transformation for each
 * line of text, and writes the result to a Writer.
 */
public class Formatter {
  private final IReadableModel model;
  private final Writer writer;
  private final IRecordFormatter lineFormatter;

  /**
   * Constructor.
   * @param model readable model of the balance. Provides input data
   * @param writer output stream. Flushed many times.
   * @param lineFormatter Abstract class for record formatting.
   */
  public Formatter(
    IReadableModel model,
    Writer writer,
    IRecordFormatter lineFormatter
  ) {
    this.model = model;
    this.writer = writer;
    this.lineFormatter = lineFormatter;
  }

  /**
   * Apply the transformation for each transaction of the set and write the result to the Writer.
   * @return true if every transaction has been processed; false otherwise.
   */
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
