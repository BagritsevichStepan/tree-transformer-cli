package com.jetbrains.internship.parser.source;

import com.jetbrains.internship.parser.source.CharSource;
import com.jetbrains.internship.util.InputOutputUtil;

import java.io.*;

public class FileSource implements CharSource {
    private final Reader reader;
    private char[] buffer = new char[InputOutputUtil.BUFFER_SIZE];
    private int pos;
    private int read;

    public FileSource(InputStream inputStream, String charsetName) throws IOException {
        reader = new InputStreamReader(inputStream, charsetName);
    }

    private void read() throws IOException {
        if (pos == read) {
            read = reader.read(buffer);
            pos = 0;
        }
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
        reader.close();
    }
}
