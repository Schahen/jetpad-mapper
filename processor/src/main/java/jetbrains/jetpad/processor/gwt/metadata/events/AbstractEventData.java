package jetbrains.jetpad.processor.gwt.metadata.events;


import com.google.gwt.user.client.Event;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

public abstract class AbstractEventData implements EventData {

  private String handlerName;
  private String fieldName;

  public AbstractEventData(String handlerName, String fieldName) {
    this.handlerName = handlerName;
    this.fieldName = fieldName;
  }

  @Override
  public TypeSpec.Builder addHandler(TypeSpec.Builder typeSpec, ClassName modelClassName) {
    MethodSpec.Builder addHandlerBuilder = MethodSpec.methodBuilder(getHandlerName());
    addHandlerBuilder.addParameter(modelClassName, "model");
    addHandlerBuilder.addParameter(Event.class, "evt");
    addHandlerBuilder.addModifiers(Modifier.PUBLIC).returns(boolean.class);
    typeSpec.addMethod(addHandlerBuilder.build());
    return typeSpec;
  }

  @Override
  public String getHandlerName() {
    return handlerName;
  }

  @Override
  public String getFieldName() {
    return fieldName;
  }
}
