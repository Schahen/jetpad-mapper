package jetbrains.jetpad.processor.gwt;

import org.w3c.dom.Node;

public class TextNodeResolver extends NodeResolver<Node> {

  private Node sourceNode;
  private Node targetNode;

  public TextNodeResolver(Node sourceNode, Node targetNode) {
    this.sourceNode = sourceNode;
    this.targetNode = targetNode;
  }

  @Override
  public Node resolve() {
    Node importedNode = targetNode.getOwnerDocument().importNode(sourceNode, false);
    targetNode.appendChild(importedNode);
    return importedNode;
  }

}
