package com.jetbrains.internship.exceptions;

public class MissingClosingBracketException extends ParsingException {
    public MissingClosingBracketException(int errorPos, char closingBracket) {
        super("There is no closing bracket \"" + closingBracket + "\".", errorPos);
    }
}
