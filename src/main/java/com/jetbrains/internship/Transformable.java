package com.jetbrains.internship;

import com.jetbrains.internship.operations.Operation;

import java.util.List;

public interface Transformable {
    List<Operation> transform();
}
