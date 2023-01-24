package persistence;

import model.core.*;
import java.io.*;

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

  public Persistence create() {
    return new Persistence(model, file);
  }
}
