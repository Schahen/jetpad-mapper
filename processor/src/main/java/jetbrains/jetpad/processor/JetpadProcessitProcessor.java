package jetbrains.jetpad.processor;

import jetbrains.jetpad.processor.gwt.UiGwtGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.shabunc.maven.Processor;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import static java.nio.file.StandardOpenOption.CREATE;

public class JetpadProcessitProcessor implements Processor {

  @Override
  public Iterator<File> getFiles() {
    File dir = new File("src/main/java");

    String[] extensions = new String[] {"jetpad.xml"};
    IOFileFilter filter = new SuffixFileFilter(extensions, IOCase.SENSITIVE);
    Iterator iter = FileUtils.iterateFiles(dir, filter, TrueFileFilter.INSTANCE);
    return iter;
  }


  private void writeToResource(Path viewResource, ByteArrayOutputStream outputStream) throws IOException {
    viewResource.toFile().getParentFile().mkdirs();
    OutputStream fileOutputStream = Files.newOutputStream(viewResource, CREATE);
    System.out.println(String.format("writing %s", viewResource.toString()));
    fileOutputStream.write(outputStream.toByteArray());
  }

  @Override
  public void process(File file) {

    String fullPath = file.toString();
    String trimmedPath = fullPath.replace(".jetpad.xml", "").replaceFirst("src/main/java/", "");

    int lastSeparatorIndex = trimmedPath.lastIndexOf('/');
    String packagePath = trimmedPath.substring(0, lastSeparatorIndex);
    String shortClassName = trimmedPath.substring(lastSeparatorIndex + 1);

    String packageName = packagePath.replace("/", ".");
    UiGwtGenerator uiGwtGenerator = new UiGwtGenerator(packageName, shortClassName);

    ByteArrayOutputStream viewOutputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream uiXmlOutputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream modelStream = new ByteArrayOutputStream();
    ByteArrayOutputStream mapperStream = new ByteArrayOutputStream();

    try {
      uiGwtGenerator.generate(file, uiXmlOutputStream, viewOutputStream, modelStream, mapperStream);

      String pathToGeneratedSources = "target/generated-sources/apt";
      Path viewResource = Paths.get(pathToGeneratedSources, packagePath, uiGwtGenerator.getViewName() + ".java");
      writeToResource(viewResource, viewOutputStream);

      Path modelResource = Paths.get(pathToGeneratedSources, packagePath, uiGwtGenerator.getModelName() + ".java");
      writeToResource(modelResource, modelStream);

      Path mapperResource = Paths.get(pathToGeneratedSources, packagePath, uiGwtGenerator.getMapperName() + ".java");
      writeToResource(mapperResource, mapperStream);

      Path uiXmlResource = Paths.get(pathToGeneratedSources, packagePath, uiGwtGenerator.getUiXmlName());
      writeToResource(uiXmlResource, uiXmlOutputStream);
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
