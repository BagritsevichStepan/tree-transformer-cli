package com.jetbrains.internship.parser;

import com.jetbrains.internship.exceptions.ParsingException;
import com.jetbrains.internship.operations.Operation;

import java.io.IOException;
import java.util.List;

public interface Parser<T> {
    T parse() throws IOException, ParsingException;
}
