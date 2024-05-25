package org.tree.exceptions;

public class UnsupportedCommandException extends ParsingException {
    public UnsupportedCommandException(int errorPos) {
        super("Unsupported command.", errorPos);
    }
}
