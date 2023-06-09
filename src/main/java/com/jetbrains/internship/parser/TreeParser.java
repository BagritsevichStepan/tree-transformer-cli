package com.jetbrains.internship.parser;

import com.jetbrains.internship.exceptions.InvalidEdgeException;
import com.jetbrains.internship.exceptions.ParsingException;
import com.jetbrains.internship.parser.source.CharSource;
import com.jetbrains.internship.tree.Tree;
import com.jetbrains.internship.tree.TreeImpl;

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
