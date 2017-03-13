package jetbrains.jetpad.processor.gwt.metadata;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public interface BindingData {
  TypeSpec.Builder addField(TypeSpec.Builder typeSpecBuilder);
  MethodSpec.Builder addNewSynchronizer(MethodSpec.Builder methodBuilder);
  String getModelPropertyName();
  String getViewPropertyName();
}
