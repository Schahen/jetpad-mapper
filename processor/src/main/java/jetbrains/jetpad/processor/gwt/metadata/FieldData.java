package jetbrains.jetpad.processor.gwt.metadata;

import jetbrains.jetpad.processor.gwt.metadata.events.EventData;

import java.util.List;

public interface FieldData<T> {
   Class<T> getElementClass();
   String getName();

   List<BindingData> getBindingData();
   List<EventData> getEventData();
}
