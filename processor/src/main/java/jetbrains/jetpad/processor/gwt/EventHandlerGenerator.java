package jetbrains.jetpad.processor.gwt;

import com.google.gwt.dom.client.Element;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import jetbrains.jetpad.processor.gwt.metadata.FieldData;
import jetbrains.jetpad.processor.gwt.metadata.bindings.BindingData;
import jetbrains.jetpad.processor.gwt.metadata.events.EventData;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventHandlerGenerator {
  private List<FieldData<Element>> fieldData;

  public EventHandlerGenerator(List<FieldData<Element>> fieldData) {
    this.fieldData = fieldData;
  }

  public void generate(String packageName, String className, String modelClassName, Appendable out) throws IOException {
    TypeSpec.Builder eventHandlerClassBuilder = TypeSpec.classBuilder(className)
        .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC);

    Set<String> createdHandlers = new HashSet<>();

    ClassName modelClassNameType= ClassName.get(packageName, modelClassName);

    for (FieldData<Element> fieldDataRecord : fieldData) {
      for (EventData eventData : fieldDataRecord.getEventData()) {
        if (!createdHandlers.contains(eventData.getHandlerName())) {
          eventData.addHandler(eventHandlerClassBuilder, modelClassNameType);
          createdHandlers.add(eventData.getHandlerName());
        }
      }
    }

    TypeSpec modelClass = eventHandlerClassBuilder.build();

    JavaFile javaFile = JavaFile
        .builder(packageName, modelClass)
        .build();

    javaFile.writeTo(out);
  }
}
