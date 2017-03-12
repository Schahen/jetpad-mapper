package jetbrains.jetpad.processor.gwt.metadata;

public interface FieldData<T> {
   Class<T> getElementClass();
   String getName();
}
