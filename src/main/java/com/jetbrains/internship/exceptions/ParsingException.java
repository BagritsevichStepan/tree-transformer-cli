package com.jetbrains.internship.exceptions;

public abstract class ParsingException extends Exception {
    public ParsingException(String message, int errorPos) {
        super(String.format("Parsing error at %d: %s", errorPos + 1, message));
    }
}
