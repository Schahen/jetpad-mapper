package jetbrains.jetpad.processor.gwt.metadata.bindings;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import jetbrains.jetpad.mapper.Synchronizers;
import jetbrains.jetpad.mapper.gwt.DomUtil;
import jetbrains.jetpad.model.property.Property;
import jetbrains.jetpad.model.property.ValueProperty;
import jetbrains.jetpad.processor.gwt.metadata.BindingData;

import javax.lang.model.element.Modifier;

public class InnerTextOfBindingData implements BindingData
{

  private String modelPropertyName;
  private String viewPropertyName;

  public InnerTextOfBindingData(String modelPropertyName, String viewPropertyName) {
    this.modelPropertyName = modelPropertyName;
    this.viewPropertyName = viewPropertyName;
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
            .builder(ParameterizedTypeName.get(Property.class, String.class), getModelPropertyName())
            .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
            .initializer("new $T<>(\"\")", ValueProperty.class)
            .build()
    );

    return typeSpecBuilder;
  }

  @Override
  public MethodSpec.Builder addNewSynchronizer(MethodSpec.Builder methodBuilder) {

    methodBuilder.addStatement("conf.add($T.forPropsOneWay(getSource().$N, $T.innerTextOf(getTarget().$N)))", Synchronizers.class, getModelPropertyName(), DomUtil.class, getViewPropertyName());

    return methodBuilder;
  }

}
