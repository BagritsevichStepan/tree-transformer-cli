package org.tree.exceptions;

import org.tree.operations.Add;

public class InvalidEdgeException extends RuntimeException {
    public InvalidEdgeException(Add edge) {
        super(String.format(
                "Wrong edge [%d, %d].",
                edge.nodeParentId(),
                edge.nodeId()
        ));
    }
}
