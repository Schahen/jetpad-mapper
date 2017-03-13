package jetbrains.jetpad.processor.gwt;

import com.google.gwt.dom.client.Element;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import jetbrains.jetpad.mapper.Mapper;
import jetbrains.jetpad.processor.gwt.metadata.FieldData;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class MapperGenerator {
  private List<FieldData<Element>> fieldData;

  public MapperGenerator(List<FieldData<Element>> fieldData) {
    this.fieldData = fieldData;
  }

  public void generate(String packageName, String className, String modeClassName, String viewClassName, Appendable out) throws IOException {

    TypeVariableName targetType = TypeVariableName.get("T");
    TypeVariableName sourceType = TypeVariableName.get("S");

      TypeSpec.Builder mapperSpecBuilder = TypeSpec.classBuilder(className)
        .addTypeVariable(targetType.withBounds(ClassName.get(packageName, modeClassName)))
        .addTypeVariable(sourceType.withBounds(ClassName.get(packageName, viewClassName)))
        .superclass(ParameterizedTypeName.get(ClassName.get(Mapper.class), targetType, sourceType))
        .addModifiers(Modifier.PUBLIC);

    mapperSpecBuilder.addMethod(MethodSpec
        .methodBuilder("registerSynchronizers")
        .addParameter(Mapper.SynchronizersConfiguration.class, "conf")
        .addCode("super.registerSynchronizers(conf)")
        .build()
    );

    TypeSpec mapperClass = mapperSpecBuilder.build();

    JavaFile javaFile = JavaFile
        .builder(packageName, mapperClass)
        .build();

    javaFile.writeTo(out);
  }
}
