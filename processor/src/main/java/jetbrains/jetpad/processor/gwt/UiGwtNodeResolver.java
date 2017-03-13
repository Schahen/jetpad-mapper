package jetbrains.jetpad.processor.gwt;

import jetbrains.jetpad.processor.gwt.metadata.FieldData;
import jetbrains.jetpad.processor.gwt.metadata.GwtFieldData;
import jetbrains.jetpad.processor.gwt.metadata.bindings.CheckedBindingData;
import jetbrains.jetpad.processor.gwt.metadata.bindings.HasClassBindingData;
import jetbrains.jetpad.processor.gwt.metadata.bindings.InnerTextOfBindingData;
import jetbrains.jetpad.processor.gwt.metadata.events.ClickEventData;
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
    return node.getAttributes().getNamedItem("jetpad_field") != null;
  }

  private FieldData<Element> fetchFieldData(Node node) {
    if (isFieldDataNode(node)) {
      NamedNodeMap attributes = node.getAttributes();
      Class<Element> clazz = Element.class;
      Node type = attributes.getNamedItem("jetpad_field_type");
      if (type != null) {
        try {
          clazz = (Class<Element>) Class.forName(String.format("com.google.gwt.dom.client.%s", type.getNodeValue()));
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
      }
      String viewFieldName = attributes.getNamedItem("jetpad_field").getNodeValue();
      GwtFieldData<Element> jetpad_field = new GwtFieldData<>(viewFieldName, clazz);

      // Resolve bindings
      Node bindingsAttr = attributes.getNamedItem("jetpad_bind");
      if (bindingsAttr != null) {
        String[] bindings = bindingsAttr.getNodeValue().split("\\s+");
        for (int i = 0, l = bindings.length; i < l; i++) {
          String binding = bindings[i];
          String[] bindingTokens = binding.split(":");
          String bindingName = bindingTokens[0];
          if (bindingName.equals("innerTextOf")) {
            String modelParam = bindingTokens[1];
            jetpad_field.addBinding(new InnerTextOfBindingData(modelParam, viewFieldName));
          } else if (bindingName.equals("checkbox")) {
            String modelParam = bindingTokens[1];
            jetpad_field.addBinding(new CheckedBindingData(modelParam, viewFieldName));
          } else if (bindingName.equals("hasClass")) {
            String modelParam = bindingTokens[1];
            String className = bindingTokens[2];
            jetpad_field.addBinding(new HasClassBindingData(modelParam, viewFieldName, className));
          }
        }
      }

      // Resolve events

      Node onclick = attributes.getNamedItem("onclick");
      if (onclick != null) {
        jetpad_field.addEvent(new ClickEventData(onclick.getNodeValue(), viewFieldName));
      }

      return jetpad_field;
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
        org.w3c.dom.Element importedNode = new DomResolver(node, targetNode).resolve();

        FieldData<Element> fieldData = fetchFieldData(node);
        if (fieldData != null) {
          fieldDatas.add(fieldData);
          importedNode.setAttribute("ui:field", fieldData.getName());
          importedNode.removeAttribute("jetpad_field");
          importedNode.removeAttribute("jetpad_field_type");
          importedNode.removeAttribute("jetpad_binding");
          importedNode.removeAttribute("onclick");
        }

        fieldDatas.addAll(resolve(node, importedNode));
      }
    }

    return fieldDatas;
  }

}
