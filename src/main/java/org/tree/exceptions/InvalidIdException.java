package org.tree.exceptions;

public class InvalidIdException extends ParsingException {
    public InvalidIdException(int errorPos) {
        super("Wrong ID.", errorPos);
    }
}
