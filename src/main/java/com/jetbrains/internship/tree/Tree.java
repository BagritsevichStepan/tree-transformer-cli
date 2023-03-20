package com.jetbrains.internship.tree;

import com.jetbrains.internship.tree.TreeImpl.Node;

import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

public interface Tree extends Addable, Removable, Displayable {
    Node getNode(long id);
    Map<Long, Node> getNodes();
    List<Node> getNodesInDfsOrder();
    boolean isEmpty();
}
