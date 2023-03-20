package com.jetbrains.internship.exceptions;

public class MissingClosingBracketException extends ParsingException {
    public MissingClosingBracketException(int errorPos) {
        super("There is no closing bracket \"]\".", errorPos);
    }
}
