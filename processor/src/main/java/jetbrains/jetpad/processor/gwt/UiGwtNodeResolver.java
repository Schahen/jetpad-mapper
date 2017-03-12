package jetbrains.jetpad.processor.gwt;

import jetbrains.jetpad.processor.gwt.metadata.FieldData;
import jetbrains.jetpad.processor.gwt.metadata.GwtFieldData;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;


import com.google.gwt.dom.client.Element;

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
      Class<Element> clazz = Element.class;
      Node type = attributes.getNamedItem("type");
      if (type != null) {
        try {
          clazz = (Class<Element>) Class.forName(String.format("com.google.gwt.dom.client.%s", type.getNodeValue()));
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
      }
      return new GwtFieldData<Element>(attributes.getNamedItem("name").getNodeValue(), clazz);
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

          FieldData<Element> fieldData = fetchFieldData(node);
          if (fieldData != null) {
            fieldDatas.add(fieldData);
            importedNode.setAttribute("ui:field", fieldData.getName());
          }

          fieldDatas.addAll(resolve(node, importedNode));
        }
      }
    }

    return fieldDatas;
  }

}
