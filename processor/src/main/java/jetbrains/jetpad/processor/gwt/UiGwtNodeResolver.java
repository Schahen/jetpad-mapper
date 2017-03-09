package jetbrains.jetpad.processor.gwt;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UiGwtNodeResolver extends NodeResolver<Boolean> {

  private Node sourceNode;
  private Node targetNode;

  public UiGwtNodeResolver(Node sourceNode, Node targetNode) {
    this.sourceNode = sourceNode;
    this.targetNode = targetNode;
  }

  @Override
  public Boolean resolve() {
    return resolve(sourceNode, targetNode);
  }

  public Boolean resolve(Node sourceNode, Node targetNode) {
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
    return true;
  }

}
