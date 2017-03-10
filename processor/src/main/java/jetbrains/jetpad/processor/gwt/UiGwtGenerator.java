package jetbrains.jetpad.processor.gwt;

import com.google.gwt.dom.client.Element;
import jetbrains.jetpad.processor.gwt.metadata.FieldData;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.List;


public class UiGwtGenerator {

  private DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    documentBuilderFactory.setNamespaceAware(true);
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    return documentBuilder;
  }

  private Document createDocument() throws ParserConfigurationException {
    DocumentBuilder documentBuilder = getDocumentBuilder();
    return documentBuilder.newDocument();
  }

  private Document parse(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilder documentBuilder = getDocumentBuilder();
    return documentBuilder.parse(xmlFile);
  }

  private Document parse(String src) throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilder documentBuilder = getDocumentBuilder();
    return documentBuilder.parse(new InputSource(new StringReader(src)));
  }

  private Document createGwtDocument() throws ParserConfigurationException {
    Document outDoc = createDocument();

    org.w3c.dom.Element outRoot = outDoc.createElement( "ui:UiBinder");
    //<ui:UiBinder xmlns:ui='urn:ui:com.google.gwt.uibinder'>
    outRoot.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ui", "urn:ui:com.google.gwt.uibinder");

    outDoc.appendChild(outRoot);

    return outDoc;
  }

  private void toOutputStream(OutputStream outputStream, Document gwtDoc) throws TransformerException {
    DOMSource source = new DOMSource(gwtDoc);
    StreamResult streamResult = new StreamResult(outputStream);
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    try {
      Transformer transformer = transformerFactory.newTransformer();


      transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

      transformer.transform(source, streamResult);
    } catch (TransformerConfigurationException e) {
      e.printStackTrace();
    }
  }

  public List<FieldData<Element>> generate(File xmlFile, OutputStream outputStream) throws ParserConfigurationException, SAXException, IOException, TransformerException {
    Document inDoc = parse(xmlFile);
    Node rootElement = inDoc.getDocumentElement();
    rootElement.normalize();

    Document gwtDoc = createGwtDocument();

    List<FieldData<Element>> fieldData =  new UiGwtNodeResolver(rootElement, gwtDoc.getDocumentElement()).resolve();

    new ViewGenerator(fieldData).generate("some.very.important", "SomeGWTView", System.out);

    toOutputStream(outputStream, gwtDoc);

    return fieldData;
  }

}
