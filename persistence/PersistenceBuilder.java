package persistence;

import model.core.*;
import java.io.*;

/**
 * Utility builder to make Persistence initialization nicer.
 */
public class PersistenceBuilder {
  private BalanceModel model;
  private File file;
  
  /**
   * Set a model
   * @return himself
   */
  public PersistenceBuilder withModel(BalanceModel model) {
    this.model = model;
    return this;
  }

  /**
   * set a File
   * @return himself
   */
  public PersistenceBuilder withFile(File file) {
    this.file = file;
    return this;
  }

  /**
   * @return new instance of Persistence
   */
  public Persistence create() {
    return new Persistence(model, file);
  }
}
