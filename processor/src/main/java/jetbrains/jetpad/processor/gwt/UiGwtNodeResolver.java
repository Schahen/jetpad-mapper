package jetbrains.jetpad.processor.gwt;

import com.google.gwt.dom.client.Element;
import jetbrains.jetpad.processor.gwt.metadata.FieldData;
import jetbrains.jetpad.processor.gwt.metadata.GwtFieldData;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class UiGwtNodeResolver extends NodeResolver<List<FieldData<Element>>> {

  private Node sourceNode;
  private Node targetNode;

  public UiGwtNodeResolver(Node sourceNode, Node targetNode) {
    this.sourceNode = sourceNode;
    this.targetNode = targetNode;
  }

  @Override
  public List<FieldData<Element>> resolve() {
    return resolve(sourceNode, targetNode);
  }

  private boolean isFieldDataNode(Node node) {
    if (node != null) {
      return node.getNodeName().equals("__field");
    }
    return false;
  }

  private FieldData<Element> fetchFieldData(Node node) {
    if (isFieldDataNode(node.getFirstChild())) {
      Node fieldData = node.getFirstChild();
      NamedNodeMap attributes = fieldData.getAttributes();
      return new GwtFieldData<Element>(attributes.getNamedItem("name").getNodeValue(), Element.class);
    }

    return null;
  }

  public List<FieldData<Element>> resolve(Node sourceNode, Node targetNode) {
    NodeList nodes = sourceNode.getChildNodes();
    List<FieldData<Element>> fieldDatas = new ArrayList<>();
    for (int i = 0; i < nodes.getLength(); i++) {
      Node node = nodes.item(i);
      if (node.getNodeType() == Node.TEXT_NODE) {
        new TextNodeResolver(node, targetNode).resolve();
      } else
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        if (!isFieldDataNode(node)) {

          org.w3c.dom.Element importedNode = new DomResolver(node, targetNode).resolve();
          resolve(node, importedNode);

          FieldData<Element> fieldData = fetchFieldData(node);
          if (fieldData != null) {
            fieldDatas.add(fieldData);
            importedNode.setAttribute("ui:field", fieldData.getName());
          }
        }
      }
    }

    return fieldDatas;
  }

}
