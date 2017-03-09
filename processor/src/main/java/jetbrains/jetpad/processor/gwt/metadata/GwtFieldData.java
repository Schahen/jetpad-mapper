package jetbrains.jetpad.processor.gwt.metadata;

import org.w3c.dom.Element;


public class GwtFieldData<T extends Element> implements FieldData<T> {

  private Class<T> klass;

  public GwtFieldData(Class<T> klass) {
    this.klass = klass;
  }

  @Override
  public Class<T> getElementClass() {
    return this.klass;
  }
}
