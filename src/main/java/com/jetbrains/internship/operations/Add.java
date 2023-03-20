package com.jetbrains.internship.operations;

public record Add(long nodeParentId, long nodeId) implements Operation {
    @Override
    public String toString() {
        return String.format("ADD(%d, %d)", nodeParentId, nodeId);
    }
}
