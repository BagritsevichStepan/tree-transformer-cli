package com.jetbrains.internship.exceptions;

public class InvalidIdException extends ParsingException {
    public InvalidIdException(int errorPos) {
        super("Wrong ID.", errorPos);
    }
}
