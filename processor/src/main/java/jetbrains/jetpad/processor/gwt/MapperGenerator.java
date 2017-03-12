package jetbrains.jetpad.processor.gwt;

import com.google.gwt.dom.client.Element;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import jetbrains.jetpad.mapper.Mapper;
import jetbrains.jetpad.processor.gwt.metadata.FieldData;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.List;

public class MapperGenerator {
  private List<FieldData<Element>> fieldData;

  public MapperGenerator(List<FieldData<Element>> fieldData) {
    this.fieldData = fieldData;
  }

  public void generate(String packageName, String className, Appendable out) throws IOException {
    System.out.println("==============================" + className);
    TypeSpec.Builder mapperSpecBuilder = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC);

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
