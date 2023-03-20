package com.jetbrains.internship.exceptions;

import com.jetbrains.internship.operations.Add;

public class InvalidEdgeException extends RuntimeException {
    public InvalidEdgeException(Add edge) {
        super(String.format(
                "Wrong edge [%d, %d].",
                edge.nodeParentId(),
                edge.nodeId()
        ));
    }
}
