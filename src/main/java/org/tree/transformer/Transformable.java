package org.tree.transformer;

import org.tree.operations.Operation;

import java.util.List;

public interface Transformable {
    List<Operation> transform();
}
