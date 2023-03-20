package com.jetbrains.internship.exceptions;

public class MissingCommaException extends ParsingException {
    public MissingCommaException(int errorPos) {
        super("The comma is missing.", errorPos);
    }
}
