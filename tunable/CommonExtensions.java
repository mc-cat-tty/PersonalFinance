package tunable;

public enum CommonExtensions {
  MAIN_EXT (".finance"),
  BACKUP_EXT (".finance.bak");

  private final String ext;

  private CommonExtensions(String ext) {
    this.ext = ext;
  }

  public String getExt() {
    return ext;
  }
}
