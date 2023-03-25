package com.jetbrains.internship.transformer;

import com.jetbrains.internship.operations.Add;
import com.jetbrains.internship.operations.Operation;
import com.jetbrains.internship.operations.Remove;
import com.jetbrains.internship.tree.Tree;
import com.jetbrains.internship.tree.TreeImpl;
import com.jetbrains.internship.tree.TreeImpl.Node;

import java.util.*;
import java.util.function.Function;

public class TreeTransformer implements Transformable {
    private final Tree treeToTransform;
    private final Tree desiredTree;
    private Map<Long, Node> subtreeNodes;
    private Map<Long, Node> desiredTreeNodes;
    private Tree subtree;

    public TreeTransformer(Tree treeToTransform, Tree desiredTree) {
        this.treeToTransform = treeToTransform;
        this.desiredTree = desiredTree;
    }

    @Override
    public List<Operation> transform() {
        subtree = new TreeImpl();
        List<Operation> operations = new ArrayList<>();
        removeOperations(operations);
        addOperations(operations);
        return operations;
    }

    private void removeOperations(List<Operation> operations) {
        desiredTreeNodes = desiredTree.getNodes();
        List<Node> treeToTransformInDfsOrder = treeToTransform.getNodesInDfsOrder();
        for (int i = 0; i < treeToTransformInDfsOrder.size(); i++) {
            final Node node = treeToTransformInDfsOrder.get(i);
            if (mustBeRemoved(node)) {
                List<Node> subtreeToRemove = getSubtree(i, treeToTransformInDfsOrder);
                removeSubtree(subtreeToRemove, operations);
                i = i + subtreeToRemove.size() - 1;
            } else if (i > 0) {
                subtree.add(new Add(node.parent().id(), node.id()));
            }
        }
    }

    private void addOperations(List<Operation> operations) {
        subtreeNodes = subtree.getNodes();
        List<Node> desiredTreeInDfsOrder = desiredTree.getNodesInDfsOrder();
        for (int i = 0; i < desiredTreeInDfsOrder.size(); i++) {
            if (mustBeAdded(desiredTreeInDfsOrder.get(i))) {
                List<Node> subtreeToAdd = getSubtree(i, desiredTreeInDfsOrder);
                addSubtree(subtreeToAdd, operations);
                i = i + subtreeToAdd.size() - 1;
            }
        }
    }

    private boolean mustBeRemoved(Node node) {
        if (!desiredTreeNodes.containsKey(node.id())) {
            return true;
        }
        return !desiredTreeNodes.get(node.id()).equals(node);
    }

    private boolean mustBeAdded(Node node) {
        return !subtreeNodes.containsKey(node.id());
    }

    private void removeSubtree(List<Node> subtreeInDfsOrder, List<Operation> operations) {
        Collections.reverse(subtreeInDfsOrder);
        performOperationsOnSubtree(subtreeInDfsOrder, node -> new Remove(node.id()), operations);
    }

    private void addSubtree(List<Node> subtreeInDfsOrder, List<Operation> operations) {
        if (subtreeInDfsOrder.get(0).isRoot()) {
            subtreeInDfsOrder.remove(0);
        }
        performOperationsOnSubtree(
                subtreeInDfsOrder,
                node -> new Add(node.parent().id(), node.id()),
                operations
        );
    }

    private void performOperationsOnSubtree(List<Node> subtreeInDfsOrder,
                                            Function<? super Node, ? extends Operation> operation,
                                            List<Operation> operations) {
        operations.addAll(subtreeInDfsOrder.stream().map(operation).toList());
    }

    private static List<Node> getSubtree(final int i, List<Node> treeInDfsOrder) {
        final int depth = treeInDfsOrder.get(i).depth();
        int j = i + 1;
        for (; j < treeInDfsOrder.size() &&
                treeInDfsOrder.get(j).depth() > depth; j++);
        return treeInDfsOrder.subList(i, j);
    }
}
