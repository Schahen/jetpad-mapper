package jetbrains.jetpad.processor.gwt.metadata.events;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

public class ClickEventData implements EventData {

  private String handlerName;

  public ClickEventData(String handlerName) {
    this.handlerName = handlerName;
  }

  @Override
  public TypeSpec.Builder addHandler(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder addHandlerBuilder = MethodSpec.methodBuilder(getHandlerName());
    addHandlerBuilder.addModifiers(Modifier.PUBLIC);
    typeSpec.addMethod(addHandlerBuilder.build());
    return typeSpec;
  }

  @Override
  public String getHandlerName() {
    return handlerName;
  }

  @Override
  public MethodSpec.Builder addEventListener(MethodSpec.Builder methodSpecBuilder) {
    return null;
  }
}
