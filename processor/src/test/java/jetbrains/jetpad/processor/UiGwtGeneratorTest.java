package jetbrains.jetpad.processor;

import jetbrains.jetpad.processor.gwt.UiGwtGenerator;
import jetbrains.jetpad.processor.gwt.metadata.FieldData;
import jetbrains.jetpad.test.BaseTestCase;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UiGwtGeneratorTest extends BaseTestCase {

  @Test
  public void mimimalDocument() throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Path testPath = Paths.get("src/test/java/jetbrains/jetpad/processor/resources/Minimal.jeptad.xml");
    UiGwtGenerator uiGwtGenerator = new UiGwtGenerator();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    uiGwtGenerator.generate(testPath.toFile(), byteArrayOutputStream);

    assertEquals("<root/> should generate empty UI binder",
        "<ui:UiBinder xmlns:ui=\"urn:ui:com.google.gwt.uibinder\"/>",
        byteArrayOutputStream.toString());
  }

  @Test
  public void simpleHtmlDocument() throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Path testPath = Paths.get("src/test/java/jetbrains/jetpad/processor/resources/SimpleHtml.jetpad.xml");
    UiGwtGenerator uiGwtGenerator = new UiGwtGenerator();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    uiGwtGenerator.generate(testPath.toFile(), byteArrayOutputStream);

    assertEquals("html node should be translated as is",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/SimpleHtml.ui.xml").toFile()),
        byteArrayOutputStream.toString());
  }


  @Test
  public void UiHtmlDocument() throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Path testPath = Paths.get("src/test/java/jetbrains/jetpad/processor/resources/UiFields.jetpad.xml");
    UiGwtGenerator uiGwtGenerator = new UiGwtGenerator();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    List<FieldData<Element>> fieldData = uiGwtGenerator.generate(testPath.toFile(), byteArrayOutputStream);

    assertEquals("field data is found", 1, fieldData.size());
  }

}
