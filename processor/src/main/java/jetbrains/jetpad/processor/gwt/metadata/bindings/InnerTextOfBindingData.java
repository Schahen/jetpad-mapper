package jetbrains.jetpad.processor.gwt.metadata.bindings;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import jetbrains.jetpad.model.property.Property;
import jetbrains.jetpad.model.property.ValueProperty;
import jetbrains.jetpad.processor.gwt.metadata.BindingData;

import javax.lang.model.element.Modifier;

public class InnerTextOfBindingData implements BindingData
{

  private String modelPropertyName;

  public InnerTextOfBindingData(String modelPropertyName) {
    this.modelPropertyName = modelPropertyName;
  }

  @Override
  public String getModelPropertyName() {
    return this.modelPropertyName;
  }

  @Override
  public TypeSpec.Builder addField(TypeSpec.Builder typeSpecBuilder) {
    typeSpecBuilder.addField(
        FieldSpec
            .builder(ParameterizedTypeName.get(Property.class, String.class), getModelPropertyName())
            .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
            .initializer("new $T<>(\"\")", ValueProperty.class)
            .build()
    );

    return typeSpecBuilder;
  }

  @Override
  public MethodSpec.Builder addNewSynchronizer(MethodSpec.Builder methodBuilder) {
    return null;
  }

}
