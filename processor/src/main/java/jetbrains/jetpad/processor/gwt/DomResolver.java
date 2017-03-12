package jetbrains.jetpad.processor.gwt;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DomResolver extends NodeResolver<Element> {

  private Node sourceNode;
  private Node targetNode;

  public DomResolver(Node sourceNode, Node targetNode) {
    this.sourceNode = sourceNode;
    this.targetNode = targetNode;
  }

  @Override
  public Element resolve() {
      Node importedNode = targetNode.getOwnerDocument().importNode(sourceNode, false);
      targetNode.appendChild(importedNode);
      return (Element)importedNode;
  }

}
