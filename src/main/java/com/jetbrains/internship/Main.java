package com.jetbrains.internship;

import com.jetbrains.internship.exceptions.InvalidEdgeException;
import com.jetbrains.internship.exceptions.ParsingException;
import com.jetbrains.internship.parser.FileSource;
import com.jetbrains.internship.parser.TreeParser;
import com.jetbrains.internship.tree.Tree;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    final private static String ABSOLUTE_PATH = Paths.get("").toAbsolutePath().toString();

    private static Tree getTree(String fileName) {
        try (FileSource fileSource = new FileSource(
                    new FileInputStream(ABSOLUTE_PATH + fileName),
                    StandardCharsets.UTF_8.name()
            )) {
            return new TreeParser(fileSource).parse();
        } catch (IOException e) {
            System.err.printf("An error occurred with input file \"%s\": %s%n", fileName, e.getMessage());
        } catch (ParsingException | InvalidEdgeException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        final String fileName1;
        final String fileName2;
        try (Scanner scanner = new Scanner(System.in)) {
            fileName1 = args.length > 0 ? args[0] : scanner.next();
            fileName2 = args.length > 1 ? args[1] : scanner.next();
        } catch (NoSuchElementException e) {
            System.err.println("There is no file name.");
            return;
        }

        Tree treeToTransform = getTree(fileName1);
        if (treeToTransform == null) {
            return;
        }

        Tree desiredTree = getTree(fileName2);
        if (desiredTree == null) {
            return;
        }

        String operations = new TreeTransformer(treeToTransform, desiredTree).transform().toString();
        System.out.println(new StringBuilder(operations)
                .deleteCharAt(operations.length() - 1)
                .deleteCharAt(0)
        );
    }
}
