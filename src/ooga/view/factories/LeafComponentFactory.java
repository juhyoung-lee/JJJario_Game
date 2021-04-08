package ooga.view.factories;

import java.util.Objects;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class LeafComponentFactory extends ComponentFactory {

  private final ActionFactory af;

  public LeafComponentFactory(ActionFactory af) {
    this.af = af;
  }

  @Override
  public Object make(Element e) throws Exception {
    if (e.getNodeName().equals("Image")) {
      return makeImage(e);
    }

    String compName = e.getNodeName();
    ResourceBundle currRB = ResourceBundle.getBundle(
        "view_resources/factory_bundles/" + compName + "Keys");
    Node component = (Node) makeComponentBase(currRB, compName);
    component.setId(e.getAttribute("id"));
    NodeList nl = e.getChildNodes();

    for (int i = 0; i < nl.getLength(); i++) {
      org.w3c.dom.Node tempNode = nl.item(i);
      if (tempNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
        Element childElem = (Element) tempNode;
        if (childElem.getNodeName().equals("Action")) {
          ((Button) component).setOnAction(af.makeAction(childElem));
        } else if (hasChildElements(childElem)) {
          addChild(currRB, component, childElem);
        } else {
          editProperty(currRB, component, childElem);
        }
      }
    }

    return component;
  }

  private Image makeImage(Element elem) {
    String imagePath = elem.getElementsByTagName("Path").item(0).getTextContent();
    return new Image(
        Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(imagePath)));
  }
}
