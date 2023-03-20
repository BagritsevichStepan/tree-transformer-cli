package com.jetbrains.internship.parser;

import java.io.*;

public class FileSource implements CharSource, Closeable {
    private final Reader reader;
    private char[] buffer = new char[1024];
    private int pos;
    private int read;

    private void read() throws IOException {
        if (pos == read) {
            read = reader.read(buffer);
            pos = 0;
        }
    }

    public FileSource(InputStream inputStream) throws IOException {
        reader = new InputStreamReader(inputStream);
    }

    public FileSource(InputStream inputStream, String charsetName) throws IOException {
        reader = new InputStreamReader(inputStream, charsetName);
    }

    @Override
    public boolean hasNext() throws IOException {
        read();
        return read != -1;
    }

    @Override
    public char getNext() throws IOException {
        read();
        return buffer[pos++];
    }

    @Override
    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }
}
