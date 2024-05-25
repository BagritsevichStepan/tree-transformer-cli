package org.tree.operations;

public record Remove(long nodeId) implements Operation {
    @Override
    public String toString() {
        return String.format("REMOVE(%d)", nodeId);
    }
}
