package com.jetbrains.internship.parser;

import java.io.IOException;

public interface CharSource {
    boolean hasNext() throws IOException;
    char getNext() throws IOException;
}
