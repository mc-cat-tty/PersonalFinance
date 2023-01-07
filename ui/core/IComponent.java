package ui.core;

/**
 * Convenience interface for marking GUI components.
 */
public interface IComponent {
  default public void composeView() { };

  default public void registerCallbacks() { };
}
