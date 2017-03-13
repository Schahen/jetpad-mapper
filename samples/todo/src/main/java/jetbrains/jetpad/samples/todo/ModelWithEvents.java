package jetbrains.jetpad.samples.todo;

import com.google.gwt.user.client.Window;
import jetbrains.jetpad.samples.todo.templates.TodoExperimentalModel;

public class ModelWithEvents extends TodoExperimentalModel {
  @Override
  public boolean pingMe() {
    Window.alert(Boolean.toString(flag.get()));
    return true;
  }
}
