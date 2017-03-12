package jetbrains.jetpad.processor.gwt;

import com.google.gwt.dom.client.Element;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import jetbrains.jetpad.mapper.Mapper;
import jetbrains.jetpad.processor.gwt.metadata.FieldData;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.List;

public class ModelGenerator {
  /*
    public class TodoListItem extends SimpleComposite<TodoList, TodoListItem> {
    public final Property<Boolean> completed = new ValueProperty<>(false);
    public final Property<String> text = new ValueProperty<>("");
  }
     */
  private List<FieldData<Element>> fieldData;

  public ModelGenerator(List<FieldData<Element>> fieldData) {
    this.fieldData = fieldData;
  }

  public void generate(String packageName, String className, Appendable out) throws IOException {
    TypeSpec.Builder modelClassBuilder = TypeSpec.classBuilder(className)
        .addModifiers(Modifier.PUBLIC);


    TypeSpec modeClass = modelClassBuilder.build();

    JavaFile javaFile = JavaFile
        .builder(packageName, modeClass)
        .build();

    javaFile.writeTo(out);
  }
}



