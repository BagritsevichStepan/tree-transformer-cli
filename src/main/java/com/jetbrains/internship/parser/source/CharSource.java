package com.jetbrains.internship.parser.source;

import java.io.Closeable;
import java.io.IOException;

public interface CharSource extends Closeable {
    boolean hasNext() throws IOException;
    char getNext() throws IOException;
}
