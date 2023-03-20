package com.jetbrains.internship.operations;

public record Remove(long nodeId) implements Operation {
    @Override
    public String toString() {
        return String.format("REMOVE(%d)", nodeId);
    }
}
