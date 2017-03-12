package jetbrains.jetpad.processor.gwt.metadata;

import java.util.List;

public interface FieldData<T> {
   Class<T> getElementClass();
   String getName();

   List<BindingData> getBindingData();
}
