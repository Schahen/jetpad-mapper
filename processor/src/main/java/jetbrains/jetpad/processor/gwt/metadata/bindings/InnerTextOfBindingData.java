package jetbrains.jetpad.processor.gwt.metadata.bindings;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import jetbrains.jetpad.processor.gwt.metadata.BindingData;

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
    return null;
  }

  @Override
  public MethodSpec.Builder addNewSynchronizer(MethodSpec.Builder methodBuilder) {
    return null;
  }

}
