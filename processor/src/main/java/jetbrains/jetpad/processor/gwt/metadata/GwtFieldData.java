package jetbrains.jetpad.processor.gwt.metadata;


import com.google.gwt.dom.client.Element;

public class GwtFieldData<T extends Element> implements FieldData<T> {

  private Class<T> klass;
  private String name;

  public GwtFieldData(String name, Class<T> klass) {
    this.name = name;
    this.klass = klass;
  }

  @Override
  public Class<T> getElementClass() {
    return this.klass;
  }

  @Override
  public String getName() {
    return name;
  }
}
