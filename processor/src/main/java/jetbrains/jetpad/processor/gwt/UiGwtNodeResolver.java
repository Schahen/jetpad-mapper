package jetbrains.jetpad.processor.gwt;

import jetbrains.jetpad.processor.gwt.metadata.FieldData;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

public class UiGwtNodeResolver extends NodeResolver<List<FieldData>> {

  private Node sourceNode;
  private Node targetNode;

  public UiGwtNodeResolver(Node sourceNode, Node targetNode) {
    this.sourceNode = sourceNode;
    this.targetNode = targetNode;
  }

  @Override
  public List<FieldData> resolve() {
    return resolve(sourceNode, targetNode);
  }

  public List<FieldData> resolve(Node sourceNode, Node targetNode) {
    NodeList nodes = sourceNode.getChildNodes();
    for (int i = 0; i < nodes.getLength(); i++) {
      Node node = nodes.item(i);
      if (node.getNodeType() == Node.TEXT_NODE) {
        new TextNodeResolver(node, targetNode).resolve();
      } else
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Node importedNode = new DomResolver(node, targetNode).resolve();
        resolve(node, importedNode);
      }
    }
    return null;
  }

}
