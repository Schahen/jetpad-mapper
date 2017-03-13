package jetbrains.jetpad.processor.gwt.metadata.events;

import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Event;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

public class ClickEventData implements EventData {

  private String handlerName;
  private String fieldName;

  public ClickEventData(String handlerName, String fieldName) {
    this.handlerName = handlerName;
    this.fieldName = fieldName;
  }

  @Override
  public TypeSpec.Builder addHandler(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder addHandlerBuilder = MethodSpec.methodBuilder(getHandlerName());
    addHandlerBuilder.addModifiers(Modifier.PUBLIC).returns(boolean.class).addStatement("return true");
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

  @Override
  public MethodSpec.Builder addEventListener(MethodSpec.Builder methodSpecBuilder) {
    methodSpecBuilder.addStatement("$T.$N(getTarget().$N).click(new $T(){@Override public boolean f($T e) {return $N().$N();}})", GQuery.class, "$", getFieldName(), Function.class, Event.class, "getSource", getHandlerName());
    return methodSpecBuilder;
  }
}
