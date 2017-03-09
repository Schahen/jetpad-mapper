package jetbrains.jetpad.processor.gwt;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class NodeResolver<T> {
  public abstract T resolve();
}
