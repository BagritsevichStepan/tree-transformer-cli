package com.jetbrains.internship.exceptions;

public class UnsupportedCommandException extends ParsingException {
    public UnsupportedCommandException(int errorPos) {
        super("Unsupported command.", errorPos);
    }
}
