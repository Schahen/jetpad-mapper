package jetbrains.jetpad.processor.gwt;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import jetbrains.jetpad.processor.gwt.metadata.FieldData;
import org.w3c.dom.Element;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.List;

public class ViewGenerator {

  private List<FieldData<Element>> fieldData;

  public ViewGenerator(List<FieldData<Element>> fieldData) {
    this.fieldData = fieldData;
  }

  public void generate(Appendable out) throws IOException {
    TypeSpec viewClass = TypeSpec
        .classBuilder("SomeView")
        .addModifiers(Modifier.PUBLIC)
        .build();

    JavaFile javaFile = JavaFile
        .builder("some.very.important", viewClass)
        .build();

    javaFile.writeTo(out);
  }
}
