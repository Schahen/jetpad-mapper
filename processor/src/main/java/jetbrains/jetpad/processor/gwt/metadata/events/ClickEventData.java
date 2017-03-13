package jetbrains.jetpad.processor.gwt.metadata.events;

import com.squareup.javapoet.MethodSpec;

public class ClickEventData implements EventData {

  private String methodName;

  public ClickEventData(String methodName) {
    this.methodName = methodName;
  }

  @Override
  public MethodSpec.Builder addEventListener(MethodSpec.Builder methodSpecBuilder) {
    return null;
  }
}
