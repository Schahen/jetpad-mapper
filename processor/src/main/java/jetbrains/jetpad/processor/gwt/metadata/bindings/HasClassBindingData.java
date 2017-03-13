package jetbrains.jetpad.processor.gwt.metadata.bindings;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import jetbrains.jetpad.mapper.Synchronizers;
import jetbrains.jetpad.mapper.gwt.DomUtil;
import jetbrains.jetpad.model.property.Property;
import jetbrains.jetpad.model.property.ValueProperty;

import javax.lang.model.element.Modifier;

public class HasClassBindingData implements BindingData
{

  private String modelPropertyName;
  private String viewPropertyName;
  private String className;

  public HasClassBindingData(String modelPropertyName, String viewPropertyName, String className) {
    this.modelPropertyName = modelPropertyName;
    this.viewPropertyName = viewPropertyName;
    this.className = className;
  }

  @Override
  public String getModelPropertyName() {
    return this.modelPropertyName;
  }

  @Override
  public String getViewPropertyName() {
    return viewPropertyName;
  }

  @Override
  public TypeSpec.Builder addField(TypeSpec.Builder typeSpecBuilder) {
    typeSpecBuilder.addField(
        FieldSpec
            .builder(ParameterizedTypeName.get(Property.class, Boolean.class), getModelPropertyName())
            .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
            .initializer("new $T<>(true)", ValueProperty.class)
            .build()
    );

    return typeSpecBuilder;
  }

  @Override
  public MethodSpec.Builder addNewSynchronizer(MethodSpec.Builder methodBuilder) {
    methodBuilder.addStatement("conf.add($T.forPropsOneWay(getSource().$N, $T.hasClass(getTarget().$N, $S)))", Synchronizers.class, getModelPropertyName(), DomUtil.class, getViewPropertyName(), className);
    return methodBuilder;
  }

}
