package org.tree.parser;

import org.tree.exceptions.InvalidEdgeException;
import org.tree.exceptions.ParsingException;
import org.tree.parser.source.CharSource;
import org.tree.tree.Tree;
import org.tree.tree.TreeImpl;

import java.io.IOException;

public class TreeParser implements Parser<Tree> {
    private final EdgeListParser parser;

    public TreeParser(CharSource source) throws IOException {
        this.parser = new EdgeListParser(source);
    }

    @Override
    public Tree parse() throws IOException, ParsingException {
        Tree tree = new TreeImpl();
        parser.parse().forEach(operation -> {
            if (!tree.add(operation)) {
                throw new InvalidEdgeException(operation);
            }
        });
        return tree;
    }
}
