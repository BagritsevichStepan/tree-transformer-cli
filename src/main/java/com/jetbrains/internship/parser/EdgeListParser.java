package com.jetbrains.internship.parser;

import com.jetbrains.internship.exceptions.*;
import com.jetbrains.internship.operations.Add;
import com.jetbrains.internship.operations.Operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class EdgeListParser extends BaseParser<List<Add>> {
    private static final char OPEN_EDGE = '[';
    private static final char EDGE_SEPARATOR = ',';
    private static final char CLOSE_EDGE = ']';

    public EdgeListParser(CharSource source) throws IOException {
        super(source);
    }

    @Override
    public List<Add> parse() throws IOException, ParsingException {
        List<Add> operations = new ArrayList<>();
        while (!fileEnded()) {
            skipWhitespaces();
            if (!fileEnded()) {
                operations.add(parseEdge());
            }
        }
        return operations;
    }

    private Add parseEdge() throws IOException, ParsingException {
        if (!test(OPEN_EDGE)) {
            throw new MissingOpeningBracketException(getPos());
        }

        skipWhitespaces();
        final long nodeParentId = parseId();
        skipWhitespaces();

        if (!test(EDGE_SEPARATOR)) {
            throw new MissingCommaException(getPos());
        }

        skipWhitespaces();
        final long nodeId = parseId();
        skipWhitespaces();

        if (!test(CLOSE_EDGE)) {
            throw new MissingClosingBracketException(getPos());
        }
        return new Add(nodeParentId, nodeId);
    }

    private long parseId() throws IOException, InvalidIdException {
        final String number = parseString(c -> (isDigit(c) || c == '-' || c == '+'));
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException e) {
            throw new InvalidIdException(getPos());
        }
    }
}
