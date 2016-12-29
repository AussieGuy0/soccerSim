package me.anthonybruno.soccerSim.reader;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * Created by anthony on 29/12/16.
 */
public abstract class NodeTraversor {

    private Node rootNode;

    public NodeTraversor(Node node) {
       this.rootNode = node;
    }

    public void traverseNodes() {
        NodeList childNodes = rootNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node current = childNodes.item(i);
            if (!(current instanceof Text)) {
                String tagName = current.getNodeName();
                String tagValue = current.getTextContent();
                nodeVisited(current, tagName, tagValue);
            }
        }
    }

    public abstract void nodeVisited(Node current, String tagName, String tagValue);

}
