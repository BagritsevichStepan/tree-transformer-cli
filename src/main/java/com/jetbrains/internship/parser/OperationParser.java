package com.jetbrains.internship.parser;

import com.jetbrains.internship.exceptions.*;
import com.jetbrains.internship.operations.Add;
import com.jetbrains.internship.operations.Exit;
import com.jetbrains.internship.operations.Operation;
import com.jetbrains.internship.operations.Remove;
import com.jetbrains.internship.parser.source.CharSource;

import java.io.IOException;

public class OperationParser extends BaseParser<Operation> {
    private static final String ADD_OPERATION = "add";
    private static final String REMOVE_OPERATION = "remove";
    private static final String EXIT_OPERATION = "exit";
    private static final char OPEN_OPERATION = '(';
    private static final char OPERATION_SEPARATOR = ',';
    private static final char CLOSE_OPERATION = ')';

    public OperationParser(CharSource source) throws IOException {
        super(source);
    }

    @Override
    public Operation parse() throws IOException, ParsingException {
        skipWhitespaces();
        if (testStringCaseInsensitive(ADD_OPERATION)) {
             return parseAddOperation();
        }
        if (testStringCaseInsensitive(REMOVE_OPERATION)) {
            return parseRemoveOperation();
        }
        if (testStringCaseInsensitive(EXIT_OPERATION)) {
            return new Exit();
        }
        throw new UnsupportedCommandException(getPos());
    }

    private Add parseAddOperation() throws ParsingException, IOException {
        return parseAddOperation(OPEN_OPERATION, OPERATION_SEPARATOR, CLOSE_OPERATION);
    }

    private Remove parseRemoveOperation() throws IOException, ParsingException {
        if (!test(OPEN_OPERATION)) {
            throw new MissingOpeningBracketException(getPos(), OPEN_OPERATION);
        }

        skipWhitespaces();
        final long nodeId = parseLong();
        skipWhitespaces();

        if (!test(CLOSE_OPERATION)) {
            throw new MissingClosingBracketException(getPos(), CLOSE_OPERATION);
        }
        return new Remove(nodeId);
    }
}
