package com.jetbrains.internship.exceptions;

public class MissingOpeningBracketException extends ParsingException {
    public MissingOpeningBracketException(int errorPos, char openingBracket) {
        super("There is no opening bracket \"" + openingBracket + "\".", errorPos);
    }
}
