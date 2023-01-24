package persistence;

import model.core.*;
import java.io.*;
import java.util.*;

public class Persistence {
  private BalanceModel model;
  private File file;

  public Persistence(BalanceModel model, File file) {
    this.file = file;
    this.model = model;
  }

  public boolean save() {
    try (
      final var outStream = new FileOutputStream(file, false);
      final var objOutStream = new ObjectOutputStream(outStream);
    ) {
      objOutStream.writeObject(model.getTransactions());
      objOutStream.flush();
    }
    catch (IOException e) {
      return false;
    } 

    return true;
  }

  @SuppressWarnings("unchecked") public boolean load() {
    try (
      final var inStream = new FileInputStream(file);
      final var objInStream = new ObjectInputStream(inStream);
    ) {
      final var transactions = (Collection<Transaction>) objInStream.readObject();
      model.setTransactions(transactions);
    }
    catch (IOException | ClassNotFoundException e) {
      return false;
    }

    return true;
  }
}
