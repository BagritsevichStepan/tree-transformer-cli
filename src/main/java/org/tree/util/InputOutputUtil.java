package org.tree.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class InputOutputUtil {
    private static final String ABSOLUTE_PATH = Paths.get("").toAbsolutePath().toString();
    public static final String CHARSET = StandardCharsets.UTF_8.name();
    public static final int BUFFER_SIZE = 1024;

    public static String getAbsoluteFilePath(File file) {
        return file.getAbsolutePath();
    }

    public static String getAbsoluteFilePath(String filePath) {
        return ABSOLUTE_PATH + filePath;
    }

    public static BufferedWriter getConsoleWriter() throws UnsupportedEncodingException {
        return new BufferedWriter(new OutputStreamWriter(System.out, CHARSET), BUFFER_SIZE);
    }
}
