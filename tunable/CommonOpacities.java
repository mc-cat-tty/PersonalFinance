package tunable;

public enum CommonOpacities {
  TEXT_INPUT (0.5f),
  TEXT_SECONDARY (0.6f);


  private final float opacity;
  
  private CommonOpacities(float opacity) {
    this.opacity = opacity;
  }

  public float getOpacity() {
    return this.opacity;
  }
}
