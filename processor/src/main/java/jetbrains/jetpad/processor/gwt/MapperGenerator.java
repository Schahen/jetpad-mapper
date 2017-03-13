package jetbrains.jetpad.processor.gwt;

import com.google.gwt.dom.client.Element;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import jetbrains.jetpad.mapper.Mapper;
import jetbrains.jetpad.processor.gwt.metadata.BindingData;
import jetbrains.jetpad.processor.gwt.metadata.FieldData;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.List;

public class MapperGenerator {
  private List<FieldData<Element>> fieldData;

  public MapperGenerator(List<FieldData<Element>> fieldData) {
    this.fieldData = fieldData;
  }

  public void generate(String packageName, String className, String modeClassName, String viewClassName, Appendable out) throws IOException {

    TypeVariableName targetType = TypeVariableName.get("S");
    TypeVariableName sourceType = TypeVariableName.get("T");

    ClassName modelClass = ClassName.get(packageName, modeClassName);
    ClassName viewClass = ClassName.get(packageName, viewClassName);
    TypeSpec.Builder mapperSpecBuilder = TypeSpec.classBuilder(className)
        .addTypeVariable(sourceType.withBounds(modelClass))
        .addTypeVariable(targetType.withBounds(viewClass))
        .superclass(ParameterizedTypeName.get(ClassName.get(Mapper.class), sourceType, targetType))
        .addModifiers(Modifier.PUBLIC);

    mapperSpecBuilder.addMethod(MethodSpec
        .constructorBuilder()
        .addStatement("super(new $T(), new $T())", modelClass, viewClass)
        .addModifiers(Modifier.PUBLIC)
        .build());

    mapperSpecBuilder.addMethod(MethodSpec
        .constructorBuilder()
        .addParameter(modelClass, "source")
        .addParameter(viewClass, "target")
        .addStatement("super(source, target)")
        .build());


    mapperSpecBuilder.addMethod(MethodSpec
        .constructorBuilder()
        .addParameter(modelClass, "source")
        .addStatement("super(source, new $T())", viewClass)
        .build()
    );

    MethodSpec.Builder registerSynchronizersMethodBuilder = MethodSpec
        .methodBuilder("registerSynchronizers")
        .addParameter(Mapper.SynchronizersConfiguration.class, "conf")
        .addStatement("super.registerSynchronizers(conf)");

    for (FieldData<Element> fieldDataRecord : fieldData) {
      for (BindingData bindingData: fieldDataRecord.getBindingData()) {
        bindingData.addNewSynchronizer(registerSynchronizersMethodBuilder);
      }
    }

    mapperSpecBuilder.addMethod(registerSynchronizersMethodBuilder.build());

    TypeSpec mapperClass = mapperSpecBuilder.build();

    JavaFile javaFile = JavaFile
        .builder(packageName, mapperClass)
        .build();

    javaFile.writeTo(out);
  }
}
