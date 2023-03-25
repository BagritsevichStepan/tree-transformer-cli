package com.jetbrains.internship.exceptions;

public class MissingSeparatorException extends ParsingException {
    public MissingSeparatorException(int errorPos, char separator) {
        super("The separator \"" + separator + "\" is missing.", errorPos);
    }
}
