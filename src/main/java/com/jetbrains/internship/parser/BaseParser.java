package com.jetbrains.internship.parser;

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

    protected boolean test(char expected) throws IOException {
        if (curChar == expected) {
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
