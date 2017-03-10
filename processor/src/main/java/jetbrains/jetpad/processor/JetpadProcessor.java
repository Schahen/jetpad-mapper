package jetbrains.jetpad.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes({"jetbrains.jetpad.processor.Jetpad"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class JetpadProcessor extends AbstractProcessor {

  private void log(String message) {
    Messager messager = processingEnv.getMessager();
    messager.printMessage(Diagnostic.Kind.NOTE, message);
  }

  private void error(String message) {
    Messager messager = processingEnv.getMessager();
    messager.printMessage(Diagnostic.Kind.ERROR, message);
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    log("===============================");
    log("===============================");
    log("===============================");
    log("===============================");
    log("===============================");
    log("===============================");
    log("===============================");
    log("===============================");
    return true;
  }
}
