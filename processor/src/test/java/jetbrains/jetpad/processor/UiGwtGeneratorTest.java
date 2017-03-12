package jetbrains.jetpad.processor;

import com.google.gwt.dom.client.Element;
import jetbrains.jetpad.processor.gwt.UiGwtGenerator;
import jetbrains.jetpad.processor.gwt.metadata.FieldData;
import jetbrains.jetpad.test.BaseTestCase;
import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
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
    UiGwtGenerator uiGwtGenerator = new UiGwtGenerator("org.jetbrains.jetpad","SimpleHtml");
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    uiGwtGenerator.generate(testPath.toFile(), byteArrayOutputStream, System.out);

    assertEquals("<root/> should generate empty UI binder",
        "<ui:UiBinder xmlns:ui=\"urn:ui:com.google.gwt.uibinder\"/>",
        byteArrayOutputStream.toString());
  }

  @Test
  public void simpleHtmlDocument() throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Path testPath = Paths.get("src/test/java/jetbrains/jetpad/processor/resources/SimpleHtml.jetpad.xml");
    UiGwtGenerator uiGwtGenerator = new UiGwtGenerator("org.jetbrains.jetpad","SimpleHtml");
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    uiGwtGenerator.generate(testPath.toFile(), byteArrayOutputStream, System.out);

    assertEquals("html node should be translated as is",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/SimpleHtml.ui.xml").toFile()),
        byteArrayOutputStream.toString());
  }


  @Test
  public void UiHtmlDocument() throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Path testPath = Paths.get("src/test/java/jetbrains/jetpad/processor/resources/UiFields.jetpad.xml");
    UiGwtGenerator uiGwtGenerator = new UiGwtGenerator("org.jetbrains.jetpad","SimpleHtml");
    ByteArrayOutputStream uiOutputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream viewOutputStream = new ByteArrayOutputStream();
    List<FieldData<Element>> fieldData = uiGwtGenerator.generate(testPath.toFile(), uiOutputStream, viewOutputStream);

    assertEquals("field data is found", 4, fieldData.size());

    assertEquals("html node should be translated as is",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/UiFields.ui.xml").toFile()),
        uiOutputStream.toString());

    ///Users/shabunc/job/my-jetpad-mapper/processor/src/test/java/jetbrains/jetpad/processor/resources/out/views/UiFieldsView.generated
    assertEquals("view translatead correctly",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/views/UiFieldsView.generated").toFile()),
        viewOutputStream.toString());
  }

}
