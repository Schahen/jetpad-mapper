package jetbrains.jetpad.processor.gwt;

import com.google.gwt.dom.client.Element;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import jetbrains.jetpad.processor.gwt.metadata.FieldData;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.List;

public class ViewGenerator {

  private List<FieldData<Element>> fieldData;

  public ViewGenerator(List<FieldData<Element>> fieldData) {
    this.fieldData = fieldData;
  }

  public void generate(Appendable out) throws IOException {
    TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder("SomeView").addModifiers(Modifier.PUBLIC);
    for (FieldData<Element> fieldDataRecord : fieldData) {
      typeSpecBuilder.addField(fieldDataRecord.getElementClass(), fieldDataRecord.getName(), Modifier.PUBLIC);
    }

    TypeSpec viewClass = typeSpecBuilder.build();

    JavaFile javaFile = JavaFile
        .builder("some.very.important", viewClass)
        .build();

    javaFile.writeTo(out);
  }
}
