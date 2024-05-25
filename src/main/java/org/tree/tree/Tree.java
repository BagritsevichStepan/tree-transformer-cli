package org.tree.tree;

import org.tree.tree.TreeImpl.Node;

import java.util.List;
import java.util.Map;

public interface Tree extends Addable, Removable, Displayable {
    Node getNode(long id);
    Map<Long, Node> getNodes();
    List<Node> getNodesInDfsOrder();
    boolean isEmpty();
}
