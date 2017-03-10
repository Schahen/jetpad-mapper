package jetbrains.jetpad.processor.gwt;

import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiField;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
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

  public void generate(String packageName, String className, Appendable out) throws IOException {
    String uiInterfaceName = className + "UiBinder";

    ClassName outerClass = ClassName.get(packageName, className);
    ClassName innerClass = outerClass.nestedClass(uiInterfaceName);

    TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC);
    for (FieldData<Element> fieldDataRecord : fieldData) {
      typeSpecBuilder
          .addField(
              FieldSpec.builder(innerClass, uiInterfaceName)
                  .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                  .initializer("GWT.create($T.class)", innerClass)
                  .build()
          )
          .addMethod(MethodSpec.constructorBuilder().addStatement("setElement(ourUiBinder.createAndBindUi(this))").build())
          .addField(
              FieldSpec.builder(fieldDataRecord.getElementClass(), fieldDataRecord.getName())
                  .addAnnotation(UiField.class)
                  .addModifiers(Modifier.PUBLIC)
                  .build()
          );
    }


    TypeSpec.Builder innerTypeSpec = TypeSpec.interfaceBuilder(uiInterfaceName);
    typeSpecBuilder.addType(innerTypeSpec.build());

    TypeSpec viewClass = typeSpecBuilder.build();

    JavaFile javaFile = JavaFile
        .builder(packageName, viewClass)
        .build();

    javaFile.writeTo(out);
  }
}
