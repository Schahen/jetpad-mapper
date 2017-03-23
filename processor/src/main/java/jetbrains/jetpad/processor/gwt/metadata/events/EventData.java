package jetbrains.jetpad.processor.gwt.metadata.events;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public interface EventData {
  String getHandlerName();
  String getFieldName();
  TypeSpec.Builder addHandler(TypeSpec.Builder typeSpec, ClassName className);
  MethodSpec.Builder addEventListener(MethodSpec.Builder methodSpecBuilder);
}
