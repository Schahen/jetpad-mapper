package jetbrains.jetpad.processor;

import jetbrains.jetpad.processor.gwt.UiGwtGenerator;
import jetbrains.jetpad.test.BaseTestCase;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class UiGwtGeneratorTest extends BaseTestCase {

  @Test
  public void mimimalDocument() throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Path testPath = Paths.get("src/test/java/jetbrains/jetpad/processor/resources/Minimal.jeptad.xml");
    UiGwtGenerator uiGwtGenerator = new UiGwtGenerator();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    uiGwtGenerator.generate(testPath.toFile(), byteArrayOutputStream);

    assertEquals("<root/> should generate empty UI binder", byteArrayOutputStream.toString(), "<ui:UiBinder xmlns:ui=\"urn:ui:com.google.gwt.uibinder\"/>");
  }

  @Test
  public void simpleHtmlDocument() throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Path testPath = Paths.get("src/test/java/jetbrains/jetpad/processor/resources/SimpleHtml.jetpad.xml");
    UiGwtGenerator uiGwtGenerator = new UiGwtGenerator();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    uiGwtGenerator.generate(testPath.toFile(), byteArrayOutputStream);

    assertEquals("html node should be translated as is", byteArrayOutputStream.toString(), FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/SimpleHtml.ui.xml").toFile()));
  }

}
