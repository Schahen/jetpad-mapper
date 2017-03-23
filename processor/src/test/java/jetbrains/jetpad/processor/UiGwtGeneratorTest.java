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
    uiGwtGenerator.generate(testPath.toFile(), byteArrayOutputStream, System.out, System.out, System.out, System.out);

    assertEquals("<root/> should generate empty UI binder",
        "<ui:UiBinder xmlns:ui=\"urn:ui:com.google.gwt.uibinder\"/>",
        byteArrayOutputStream.toString());
  }

  @Test
  public void simpleHtmlDocument() throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Path testPath = Paths.get("src/test/java/jetbrains/jetpad/processor/resources/SimpleHtml.jetpad.xml");
    UiGwtGenerator uiGwtGenerator = new UiGwtGenerator("org.jetbrains.jetpad","SimpleHtml");
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    uiGwtGenerator.generate(testPath.toFile(), byteArrayOutputStream, System.out, System.out, System.out, System.out);

    assertEquals("html node should be translated as is",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/SimpleHtml.ui.xml").toFile()),
        byteArrayOutputStream.toString());
  }

  @Test
  public void simpleNestedField() throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Path testPath = Paths.get("src/test/java/jetbrains/jetpad/processor/resources/SimpleNestedField.jetpad.xml");
    UiGwtGenerator uiGwtGenerator = new UiGwtGenerator("org.jetbrains.jetpad","SimpleNestedField");
    ByteArrayOutputStream uiOutputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream viewOutputStream = new ByteArrayOutputStream();
    uiGwtGenerator.generate(testPath.toFile(), uiOutputStream, viewOutputStream, System.out, System.out, System.out);

    assertEquals("html node should be translated as is",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/SimpleNestedField.ui.xml").toFile()),
        uiOutputStream.toString());

    assertEquals("view translatead correctly",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/views/SimpleNestedField.generated").toFile()),
        viewOutputStream.toString());
  }


  @Test
  public void SamePropertyMultipleFields() throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Path testPath = Paths.get("src/test/java/jetbrains/jetpad/processor/resources/SamePropertyMultipleFields.jetpad.xml");
    UiGwtGenerator uiGwtGenerator = new UiGwtGenerator("org.jetbrains.jetpad","SamePropertyMultipleFields");
    ByteArrayOutputStream uiOutputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream viewOutputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream modelOutputStream = new ByteArrayOutputStream();
    uiGwtGenerator.generate(testPath.toFile(), uiOutputStream, viewOutputStream, modelOutputStream, System.out, System.out);

    assertEquals("view translated correctly",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/views/SamePropertyMultipleFields.generated").toFile()),
        viewOutputStream.toString());

    assertEquals("model translated correctly",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/models/SamePropertyMultipleFields.generated").toFile()),
        modelOutputStream.toString());
  }

  @Test
  public void SameHandlerMultipleFields() throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Path testPath = Paths.get("src/test/java/jetbrains/jetpad/processor/resources/SameHandlerMultipleFields.jetpad.xml");
    UiGwtGenerator uiGwtGenerator = new UiGwtGenerator("org.jetbrains.jetpad","SameHandlerMultipleFields");
    ByteArrayOutputStream uiOutputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream viewOutputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream modelOutputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream mapperOutputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream eventHandlerStream = new ByteArrayOutputStream();

    uiGwtGenerator.generate(testPath.toFile(), uiOutputStream, viewOutputStream, modelOutputStream, eventHandlerStream, mapperOutputStream);

    assertEquals("view translated correctly",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/views/SameHandlerMultipleFields.generated").toFile()),
        viewOutputStream.toString());

    assertEquals("model translated correctly",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/models/SameHandlerMultipleFields.generated").toFile()),
        modelOutputStream.toString());

    assertEquals("model translated correctly",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/mappers/SameHandlerMultipleFields.generated").toFile()),
        mapperOutputStream.toString());

    assertEquals("handler translated correctly",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/handlers/SameHandlerMultipleFields.generated").toFile()),
        eventHandlerStream.toString());
  }

  @Test
  public void EventsTest() throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Path testPath = Paths.get("src/test/java/jetbrains/jetpad/processor/resources/EventsTest.jetpad.xml");
    UiGwtGenerator uiGwtGenerator = new UiGwtGenerator("org.jetbrains.jetpad","SameHandlerMultipleFields");
    ByteArrayOutputStream uiOutputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream viewOutputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream modelOutputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream mapperOutputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream eventHandlerStream = new ByteArrayOutputStream();

    uiGwtGenerator.generate(testPath.toFile(), uiOutputStream, viewOutputStream, modelOutputStream, eventHandlerStream, mapperOutputStream);

    assertEquals("view translated correctly",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/views/EventsTest.generated").toFile()),
        viewOutputStream.toString());

    assertEquals("model translated correctly",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/models/EventsTest.generated").toFile()),
        modelOutputStream.toString());

    assertEquals("model translated correctly",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/mappers/EventsTest.generated").toFile()),
        mapperOutputStream.toString());

    assertEquals("handler translated correctly",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/handlers/EventsTest.generated").toFile()),
        eventHandlerStream.toString());
  }


  @Test
  public void BindingsTest() throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Path testPath = Paths.get("src/test/java/jetbrains/jetpad/processor/resources/Bindings.jetpad.xml");
    UiGwtGenerator uiGwtGenerator = new UiGwtGenerator("org.jetbrains.jetpad","Bindings");
    ByteArrayOutputStream uiOutputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream viewOutputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream modelOutputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream mapperOutputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream eventHandlerStream = new ByteArrayOutputStream();

    uiGwtGenerator.generate(testPath.toFile(), uiOutputStream, viewOutputStream, modelOutputStream, eventHandlerStream, mapperOutputStream);

    assertEquals("view translated correctly",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/views/Bindings.generated").toFile()),
        viewOutputStream.toString());

    assertEquals("model translated correctly",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/models/Bindings.generated").toFile()),
        modelOutputStream.toString());

    assertEquals("model translated correctly",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/mappers/Bindings.generated").toFile()),
        mapperOutputStream.toString());

    assertEquals("handler translated correctly",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/handlers/Bindings.generated").toFile()),
        eventHandlerStream.toString());
  }

  @Test
  public void UiHtmlDocument() throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Path testPath = Paths.get("src/test/java/jetbrains/jetpad/processor/resources/UiFields.jetpad.xml");
    UiGwtGenerator uiGwtGenerator = new UiGwtGenerator("org.jetbrains.jetpad","SimpleHtml");
    ByteArrayOutputStream uiOutputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream viewOutputStream = new ByteArrayOutputStream();
    List<FieldData<Element>> fieldData = uiGwtGenerator.generate(testPath.toFile(), uiOutputStream, viewOutputStream, System.out, System.out, System.out);

    assertEquals("field data is found", 4, fieldData.size());

    assertEquals("html node should be translated as is",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/UiFields.ui.xml").toFile()),
        uiOutputStream.toString());

    assertEquals("view translated correctly",
        FileUtils.readFileToString(Paths.get("src/test/java/jetbrains/jetpad/processor/resources/out/views/UiFields.generated").toFile()),
        viewOutputStream.toString());
  }

}
