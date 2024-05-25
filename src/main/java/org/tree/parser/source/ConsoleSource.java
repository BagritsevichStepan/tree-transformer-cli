package org.tree.parser.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConsoleSource implements CharSource {
    private final BufferedReader reader;
    private int pos;
    private String read = "";

    public ConsoleSource(InputStream inputStream, String charsetName) throws IOException {
        reader = new BufferedReader(new InputStreamReader(inputStream, charsetName));
    }

    public void readAndSkipEmptyLines() throws IOException {
        while (!read());
    }

    private boolean read() throws IOException {
        read = reader.readLine();
        pos = 0;
        return !read.equals("");
    }

    @Override
    public boolean hasNext() throws IOException {
        return pos < read.length();
    }

    @Override
    public char getNext() throws IOException {
        return read.charAt(pos++);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
