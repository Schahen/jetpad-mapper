package jetbrains.jetpad.processor.gwt.metadata;


import com.google.gwt.dom.client.Element;
import jetbrains.jetpad.processor.gwt.metadata.bindings.BindingData;
import jetbrains.jetpad.processor.gwt.metadata.events.EventData;

import java.util.ArrayList;
import java.util.List;

public class GwtFieldData<T extends Element> implements FieldData<T> {

  private Class<T> klass;
  private String name;

  private final List<BindingData> bindingDataList = new ArrayList<>();
  private final List<EventData> eventDataList = new ArrayList<>();

  public boolean addBinding(BindingData bindingData) {
    return bindingDataList.add(bindingData);
  }
  public boolean addEvent(EventData eventData) { return eventDataList.add(eventData); }

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

  @Override
  public List<BindingData> getBindingData() {
    return bindingDataList;
  }

  @Override
  public List<EventData> getEventData() {
    return eventDataList;
  }
}
