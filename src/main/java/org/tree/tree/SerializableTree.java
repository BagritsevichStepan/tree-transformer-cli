package org.tree.tree;

import org.tree.exceptions.InvalidEdgeException;
import org.tree.operations.Add;
import org.tree.tree.TreeImpl.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class SerializableTree implements Tree, Externalizable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(toEdgesList(getNodesInDfsOrder()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        buildTree((ArrayList<SerializableEdge>) in.readObject());
    }

    private List<SerializableEdge> toEdgesList(List<Node> nodes) {
        return new ArrayList<>(nodes.stream()
                .skip(1)
                .map(node -> new SerializableEdge(node.parent().id(), node.id()))
                .toList());
    }

    private void buildTree(List<SerializableEdge> edges) {
        edges.forEach(edge -> {
            Add addOperation = new Add(edge.parentId, edge.id);
            if (!add(addOperation)) {
                throw new InvalidEdgeException(addOperation);
            }
        });
    }

    private record SerializableEdge(long parentId, long id) implements Serializable {}
}
