package jetbrains.jetpad.processor.gwt.metadata.events;

import com.squareup.javapoet.MethodSpec;

public interface EventData {
  MethodSpec.Builder addEventListener(MethodSpec.Builder methodSpecBuilder);
}
