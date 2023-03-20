package com.jetbrains.internship.tree;

import com.jetbrains.internship.operations.Add;
import com.jetbrains.internship.operations.Remove;

import java.io.OutputStreamWriter;
import java.util.*;

public class TreeImpl extends SerializableTree {
    private final Map<Long, Node> nodes = new HashMap<>();
    private final Set<Long> leavesIds = new HashSet<>();
    private Node root;

    public record Node(long id, int depth, Node parent, List<Node> children) {
        private Node(long id, Node parent) {
            this(id, parent != null ? (parent.depth + 1) : 0, parent, new ArrayList<>());
            if (parent != null) {
                parent.addChild(this);
            }
        }

        private Node(long id) {
            this(id, null);
        }

        private void dfs(List<Node> nodes) {
            nodes.add(this);
            children.forEach(child -> child.dfs(nodes));
        }

        private void addChild(Node node) {
            children.add(node);
        }

        private void removeChild(Node node) {
            children.remove(node);
        }

        public boolean isRoot() {
            return parent == null;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Node objAsNode) {
                return id == objAsNode.id && depth == objAsNode.depth && (
                        (isRoot() && objAsNode.isRoot()) ||
                                (!isRoot() && !objAsNode.isRoot() && parent.id == objAsNode.parent.id)
                );
            }
            return false;
        }

        @Override
        public String toString() {
            return String.format("Node(id = %d, depth = %d)", id, depth);
        }
    }

    private void setRoot(long id) {
        root = new Node(id);
        addNewNode(root);
    }

    private boolean contains(long id) {
        return nodes.containsKey(id);
    }

    @Override
    public Node getNode(long id) {
        return nodes.getOrDefault(id, null);
    }

    @Override
    public List<Node> getNodesInDfsOrder() {
        List<Node> nodes = new ArrayList<>();
        root.dfs(nodes);
        return nodes;
    }

    @Override
    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    @Override
    public Map<Long, Node> getNodes() {
        return nodes;
    }

    @Override
    public boolean add(Add addOperation) {
        if (canBeAdded(addOperation)) {
            performAdd(addOperation);
            return true;
        }
        return false;
    }

    private boolean canBeAdded(Add addOperation) {
        if (isEmpty()) {
            setRoot(addOperation.nodeParentId());
        }
        return contains(addOperation.nodeParentId()) &&
                !contains(addOperation.nodeId());
    }

    private void performAdd(Add addOperation) {
        removeLeaveId(addOperation.nodeParentId());
        addNewNode(new Node(addOperation.nodeId(), getNode(addOperation.nodeParentId())));
    }

    private void addNewNode(Node node) {
        nodes.put(node.id, node);
        leavesIds.add(node.id);
    }

    @Override
    public boolean remove(Remove removeOperation) {
        if (canBeRemoved(removeOperation)) {
            performRemove(removeOperation);
            return true;
        }
        return false;
    }

    private boolean canBeRemoved(Remove removeOperation) {
        return root != null && leavesIds.contains(removeOperation.nodeId());
    }

    private void performRemove(Remove removeOperation) {
        Node node = getNode(removeOperation.nodeId());
        removeLeaveId(node.id);

        if (!node.isRoot()) {
            node.parent.removeChild(node);
            addNewNode(node.parent);
        } else {
            root = null;
        }
    }

    private void removeLeaveId(long id) {
        leavesIds.remove(id);
    }

    @Override
    public void display(OutputStreamWriter output) {
        // TODO
    }
}
