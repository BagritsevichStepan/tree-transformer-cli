package org.tree.parser;

import org.tree.exceptions.*;
import org.tree.operations.Add;
import org.tree.parser.source.CharSource;

import java.io.IOException;
import java.util.function.Predicate;

public abstract class BaseParser<T> implements Parser<T> {
    private final CharSource source;
    private char curChar;
    private int curPos = 0;
    private static final char END_OF_FILE = 0;

    protected BaseParser(CharSource source) throws IOException {
        this.source = source;
        nextChar();
    }

    public Add parseAddOperation(char openingBracket, char bracketSeparator, char closingBracket) throws IOException, ParsingException {
        if (!test(openingBracket)) {
            throw new MissingOpeningBracketException(getPos(), openingBracket);
        }

        skipWhitespaces();
        final long nodeParentId = parseLong();
        skipWhitespaces();

        if (!test(bracketSeparator)) {
            throw new MissingSeparatorException(getPos(), bracketSeparator);
        }

        skipWhitespaces();
        final long nodeId = parseLong();
        skipWhitespaces();

        if (!test(closingBracket)) {
            throw new MissingClosingBracketException(getPos(), closingBracket);
        }
        return new Add(nodeParentId, nodeId);
    }

    protected long parseLong() throws IOException, InvalidIdException {
        final String number = parseString(c -> (isDigit(c) || c == '-' || c == '+'));
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException e) {
            throw new InvalidIdException(getPos());
        }
    }

    protected String parseString(Predicate<Character> isStringCharacter) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        while (isStringCharacter.test(curChar)) {
            stringBuilder.append(curChar);
            nextChar();
        }
        return stringBuilder.toString();
    }

    protected void nextChar() throws IOException {
        if (source.hasNext()) {
            curChar = source.getNext();
            curPos++;
        } else {
            curChar = END_OF_FILE;
        }
    }

    protected int getPos() {
        return curPos;
    }

    protected boolean testString(String expected) throws IOException {
        // == expected.chars().allMatch(this::test)
        for (char c : expected.toCharArray()) {
            if (!test(c)) {
                return false;
            }
        }
        return true;
    }

    protected boolean testStringCaseInsensitive(String expected) throws IOException {
        // == expected.chars().allMatch(this::testCaseInsensitive)
        for (char c : expected.toCharArray()) {
            if (!testCaseInsensitive(c)) {
                return false;
            }
        }
        return true;
    }

    protected boolean test(char expected) throws IOException {
        if (curChar == expected) {
            nextChar();
            return true;
        }
        return false;
    }

    protected boolean testCaseInsensitive(char expected) throws IOException {
        if (Character.toLowerCase(curChar) == Character.toLowerCase(expected)) {
            nextChar();
            return true;
        }
        return false;
    }

    protected void skipWhitespaces() throws IOException {
        while (isWhitespace(curChar)) {
            nextChar();
        }
    }

    protected boolean fileEnded() {
        return curChar == END_OF_FILE;
    }

    protected static boolean isWhitespace(char c) {
        return Character.isWhitespace(c);
    }

    protected static boolean isDigit(char c) {
        return Character.isDigit(c);
    }
}
