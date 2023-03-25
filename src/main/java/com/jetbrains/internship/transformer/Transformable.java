package com.jetbrains.internship.transformer;

import com.jetbrains.internship.operations.Operation;

import java.util.List;

public interface Transformable {
    List<Operation> transform();
}
