package com.jetbrains.internship;

import com.jetbrains.internship.exceptions.InvalidEdgeException;
import com.jetbrains.internship.exceptions.ParsingException;
import com.jetbrains.internship.parser.source.FileSource;
import com.jetbrains.internship.parser.TreeParser;
import com.jetbrains.internship.transformer.TreeTransformer;
import com.jetbrains.internship.tree.Tree;
import com.jetbrains.internship.util.InputOutputUtil;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.IntStream;

@Command(
        name = "transforming",
        mixinStandardHelpOptions = true,
        version = "transforming 1.0",
        description = "Calculates the list of transformations required to get the second tree from the first"
)
public class TreeTransforming implements Runnable {
    private static final int FILES_COUNT = 2;

    @Option(names = {"-p", "--print-trees"}, description = {"Prints both trees to the console"})
    private boolean printTrees = false;

    @Parameters(arity = "0..2", paramLabel = "<tree-file>", description = "File(s) to read trees")
    private File[] files;

    private static Tree getTree(File file) {
        try (FileSource fileSource = new FileSource(
                    new FileInputStream(InputOutputUtil.getAbsoluteFilePath(file)),
                    InputOutputUtil.CHARSET
            )) {
            return new TreeParser(fileSource).parse();
        } catch (IOException e) {
            System.err.printf(
                    "An error occurred with input file \"%s\": %s%n",
                    InputOutputUtil.getAbsoluteFilePath(file),
                    e.getMessage()
            );
        } catch (ParsingException | InvalidEdgeException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void run() {
        File[] newFiles = new File[FILES_COUNT];
        if (files != null) {
            System.arraycopy(files, 0, newFiles, 0, files.length);
        }
        files = newFiles;

        try (Scanner scanner = new Scanner(System.in, InputOutputUtil.CHARSET)) {
            IntStream.range(0, FILES_COUNT).forEach(i -> {
                if (files[i] == null) {
                    files[i] = new File(scanner.next());
                }
            });
        } catch (NoSuchElementException e) {
            System.err.println("There is no file name.");
            return;
        }

        Tree treeToTransform = getTree(files[0]);
        if (treeToTransform == null) {
            return;
        }

        Tree desiredTree = getTree(files[1]);
        if (desiredTree == null) {
            return;
        }

        try (BufferedWriter consoleWriter = InputOutputUtil.getConsoleWriter()) {
            if (printTrees) {
                treeToTransform.display(consoleWriter);
                consoleWriter.write(System.lineSeparator());
                desiredTree.display(consoleWriter);
                consoleWriter.write(System.lineSeparator());
                consoleWriter.flush();
            }

            String operations = new TreeTransformer(treeToTransform, desiredTree).transform().toString();
            consoleWriter.write(new StringBuilder(operations)
                    .deleteCharAt(operations.length() - 1)
                    .deleteCharAt(0).toString()
            );
            consoleWriter.flush();
        } catch (IOException e) {
            System.err.println("An error occurred during writing to console: " + e.getMessage());
        }
    }
}
