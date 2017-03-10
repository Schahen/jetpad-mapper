package jetbrains.jetpad.processor;

import com.google.gwt.resources.client.ClientBundle;
import jetbrains.jetpad.processor.gwt.UiGwtGenerator;
import org.xml.sax.SAXException;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.StandardLocation;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@SupportedAnnotationTypes({"jetbrains.jetpad.processor.Jetpad"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class JetpadProcessor extends AbstractProcessor {

  private FileObject getResource(JavaFileManager.Location location,
      CharSequence pkg,
      CharSequence relativeName) {
    FileObject resource = null;
    try {
      resource = processingEnv.getFiler().getResource(location, pkg, relativeName);
    } catch (IOException e) {
      return null;
    }
    return resource;
  }

  private void log(String message) {
    Messager messager = processingEnv.getMessager();
    messager.printMessage(Diagnostic.Kind.NOTE, message);
  }

  private void error(String message) {
    Messager messager = processingEnv.getMessager();
    messager.printMessage(Diagnostic.Kind.ERROR, message);
  }

  private void writeOutputStream(FileObject resource, OutputStream outputStream) throws IOException {
    Writer writer = resource.openWriter();
    writer.write(outputStream.toString());
    writer.close();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    for (Element element: roundEnv.getElementsAnnotatedWith(Jetpad.class)) {
      Jetpad annotation = element.getAnnotation(Jetpad.class);

      PackageElement packageElement = processingEnv.getElementUtils().getPackageOf(element);

      //String packageName =  packageElement.getQualifiedName().toString();
      //String pkgPath = packageName.replace(".", "/");

      String[] fragments = annotation.value().split(":");
      String packageName = fragments[0];
      String name = fragments[1];
      String templateName = name + ".jetpad.xml";


      FileObject templatePath = getResource(StandardLocation.SOURCE_PATH, packageName, templateName);

      if (templatePath != null) {
        UiGwtGenerator uiGwtGenerator = new UiGwtGenerator(packageName, name);

        try {
          ByteArrayOutputStream viewOutputStream = new ByteArrayOutputStream();
          ByteArrayOutputStream uiXmlOutputStream = new ByteArrayOutputStream();

          uiGwtGenerator.generate(Paths.get(templatePath.toUri()).toFile(), uiXmlOutputStream, viewOutputStream);

          FileObject viewResource = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, packageName, uiGwtGenerator.getViewClassName() + ".java");
          writeOutputStream(viewResource, viewOutputStream);

          FileObject uiXmlResource = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, packageName, uiGwtGenerator.getUiXmlName());
          writeOutputStream(uiXmlResource, uiXmlOutputStream);
        } catch (ParserConfigurationException e) {
          e.printStackTrace();
        } catch (SAXException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        } catch (TransformerException e) {
          e.printStackTrace();
        }
      }

    }

    return true;
  }
}
