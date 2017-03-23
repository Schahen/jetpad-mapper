package jetbrains.jetpad.processor.gwt;

import com.google.gwt.dom.client.Element;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import jetbrains.jetpad.processor.gwt.metadata.bindings.BindingData;
import jetbrains.jetpad.processor.gwt.metadata.FieldData;
import jetbrains.jetpad.processor.gwt.metadata.events.EventData;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModelGenerator {
  /*
    public class TodoListItem extends SimpleComposite<TodoList, TodoListItem> {
    public final Property<Boolean> completed = new ValueProperty<>(false);
    public final Property<String> text = new ValueProperty<>("");
  }
     */
  private List<FieldData<Element>> fieldData;


  private boolean hasEventsBinded() {
    for (FieldData<Element> fieldDataRecord : fieldData) {
      for (EventData eventData: fieldDataRecord.getEventData()) {
        return true;
      }
    }
    return false;
  };

  public ModelGenerator(List<FieldData<Element>> fieldData) {
    this.fieldData = fieldData;
  }

  public void generate(String packageName, String className, Appendable out) throws IOException {
    TypeSpec.Builder modelClassBuilder = TypeSpec.classBuilder(className)
        .addModifiers(Modifier.PUBLIC);

    if (hasEventsBinded()) {
    }

    Set<String> createdFields = new HashSet<>();
    Set<String> createdHandlers = new HashSet<>();

    for (FieldData<Element> fieldDataRecord : fieldData) {
      for (BindingData bindingData: fieldDataRecord.getBindingData()) {
        if (createdFields.contains(bindingData.getModelPropertyName())) {
          // TODO (shabunc): most probably will need to throw an exception when we trying to generate field with same name and differentType
        } else {
          bindingData.addField(modelClassBuilder);
          createdFields.add(bindingData.getModelPropertyName());
        }
      }
    }

    TypeSpec modelClass = modelClassBuilder.build();

    JavaFile javaFile = JavaFile
        .builder(packageName, modelClass)
        .build();

    javaFile.writeTo(out);
  }
}



