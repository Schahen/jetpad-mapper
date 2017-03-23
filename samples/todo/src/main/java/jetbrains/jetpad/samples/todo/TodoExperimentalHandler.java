package jetbrains.jetpad.samples.todo;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import jetbrains.jetpad.samples.todo.templates.TodoExperimentalEventHandler;
import jetbrains.jetpad.samples.todo.templates.TodoExperimentalModel;

public class TodoExperimentalHandler extends TodoExperimentalEventHandler {
  @Override
  public boolean pingMe(TodoExperimentalModel model, Event evt) {
    return false;
  }
}
