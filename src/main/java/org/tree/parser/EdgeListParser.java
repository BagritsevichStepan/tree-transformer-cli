package org.tree.parser;

import org.tree.exceptions.*;
import org.tree.operations.Add;
import org.tree.parser.source.CharSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
            operations.add(parseEdge());
        }
        return operations;
    }

    private Add parseEdge() throws ParsingException, IOException {
        return parseAddOperation(OPEN_EDGE, EDGE_SEPARATOR, CLOSE_EDGE);
    }
}
