package ui.core;

/**
 * Utility builder for IComponent.
 * 
 * @see IComponent
 * @deprecated
 */
public class ComponentBuilder {
  public ComponentBuilder(IComponent component) {
    component.composeView();
  }

  public ComponentBuilder(IComponent[] components) {
    for (var component : components) {
      component.composeView();
    }
  }
}
