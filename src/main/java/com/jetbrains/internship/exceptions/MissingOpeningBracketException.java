package com.jetbrains.internship.exceptions;

public class MissingOpeningBracketException extends ParsingException {
    public MissingOpeningBracketException(int errorPos) {
        super("There is no opening bracket \"[\".", errorPos);
    }
}
