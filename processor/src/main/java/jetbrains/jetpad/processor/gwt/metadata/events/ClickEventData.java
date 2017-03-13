package jetbrains.jetpad.processor.gwt.metadata.events;

import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Event;
import com.squareup.javapoet.MethodSpec;

public class ClickEventData extends AbstractEventData {

  public ClickEventData(String handlerName, String fieldName) {
    super(handlerName, fieldName);
  }

  @Override
  public MethodSpec.Builder addEventListener(MethodSpec.Builder methodSpecBuilder) {
    methodSpecBuilder.addStatement("$T.$N(getTarget().$N).click(new $T(){@Override public boolean f($T e) {return $N().$N();}})", GQuery.class, "$", getFieldName(), Function.class, Event.class, "getSource", getHandlerName());
    return methodSpecBuilder;
  }
}
