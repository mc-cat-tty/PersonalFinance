package persistence;

import model.core.*;
import java.io.*;
import java.util.*;

public class PersistenceBuilder {
  private BalanceModel model;
  private File file;
  
  public PersistenceBuilder withModel(BalanceModel model) {
    this.model = model;
    return this;
  }

  public PersistenceBuilder withFile(File file) {
    this.file = file;
    return this;
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
      e.printStackTrace();
      return false;
    } 

    return true;
  }

  public boolean load() {
    try (
      final var inStream = new FileInputStream(file);
      final var objInStream = new ObjectInputStream(inStream);
    ) {
      final var transactions = (Collection<Transaction>) objInStream.readObject();
      model.setTransactions(transactions);
    }
    catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }
}
