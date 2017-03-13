package jetbrains.jetpad.processor.gwt.metadata.events;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public interface EventData {
  String getHandlerName();
  TypeSpec.Builder addHandler(TypeSpec.Builder typeSpec);
  MethodSpec.Builder addEventListener(MethodSpec.Builder methodSpecBuilder);
}
