package org.tree.parser;

import org.tree.exceptions.ParsingException;
import org.tree.operations.Operation;

import java.io.IOException;
import java.util.List;

public interface Parser<T> {
    T parse() throws IOException, ParsingException;
}
