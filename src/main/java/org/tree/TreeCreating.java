package org.tree;

import org.tree.exceptions.InvalidEdgeException;
import org.tree.exceptions.ParsingException;
import org.tree.operations.Add;
import org.tree.operations.Exit;
import org.tree.operations.Operation;
import org.tree.operations.Remove;
import org.tree.parser.source.ConsoleSource;
import org.tree.parser.OperationParser;
import org.tree.tree.SerializableTree;
import org.tree.tree.Tree;
import org.tree.tree.TreeImpl;
import org.tree.util.InputOutputUtil;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.*;

@Command(
        name = "creating",
        mixinStandardHelpOptions = true,
        version = "creating 1.0",
        description = "Can generate, print and save trees"
)
public class TreeCreating implements Runnable {
    private static final InputStream OPERATION_INPUT_STREAM = System.in;
    private BufferedWriter consoleWriter;

    @Option(names = {"-l", "--load-tree"}, description = {"Load the tree from the file"})
    private boolean loadTree = false;

    @Parameters(arity = "0..1", paramLabel = "<tree-file>", description = "File to load and save tree")
    private File file = new File("tree.txt");


    private SerializableTree loadTree() throws IOException, ClassNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream(InputOutputUtil.getAbsoluteFilePath(file));
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            SerializableTree tree = new TreeImpl();
            tree.readExternal(objectInputStream);
            return tree;
        }
    }

    private void saveTree(SerializableTree tree) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(InputOutputUtil.getAbsoluteFilePath(file));
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            tree.writeExternal(objectOutputStream);
            objectOutputStream.flush();
        }
    }

    private void writeToConsole(String s) {
        try {
            consoleWriter.write(s, 0, s.length());
            consoleWriter.flush();
        } catch (IOException e) {
            System.err.println("Failed to writeToConsole: " + e.getMessage());
        }
    }

    private void writelnToConsole(String s) {
        writeToConsole(s + System.lineSeparator());
    }

    private void printTree(Tree tree) {
        try {
            tree.display(consoleWriter);
            consoleWriter.flush();
        } catch (IOException e) {
            System.err.println("Failed to print tree.");
        }
    }

    private SerializableTree preloadTree() {
        if (loadTree) {
            try {
                SerializableTree tree = loadTree();
                printTree(tree);
                return tree;
            } catch (InvalidEdgeException e) {
                System.err.println(e.getMessage());
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Failed to load tree from the file: " + e.getMessage());
            }
        } else {
            SerializableTree tree = new TreeImpl();
            try {
                saveTree(tree);
                return tree;
            } catch (IOException e) {
                System.err.println("Failed to save tree into the file: " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public void run() {
        try {
            consoleWriter = InputOutputUtil.getConsoleWriter();
            SerializableTree tree = preloadTree();
            if (tree == null) {
                return;
            }

            OperationParser operationParser;
            try (ConsoleSource consoleReader = new ConsoleSource(OPERATION_INPUT_STREAM, InputOutputUtil.CHARSET)) {
                while (true) {
                    consoleReader.readAndSkipEmptyLines();
                    operationParser = new OperationParser(consoleReader);

                    Operation operation;
                    try {
                        operation = operationParser.parse();
                    } catch (ParsingException e) {
                        writelnToConsole(e.getMessage());
                        continue;
                    }

                    if (operation instanceof Exit) {
                        break;
                    }

                    tree = loadTree();
                    boolean treeShouldBeSaved = true;
                    if (operation instanceof Add addOperation) {
                        if (!tree.add(addOperation)) {
                            treeShouldBeSaved = false;
                            writelnToConsole("The add operation failed. The tree has not been changed:");
                        }
                    }
                    if (operation instanceof Remove removeOperation) {
                        if (!tree.remove(removeOperation)) {
                            treeShouldBeSaved = false;
                            writelnToConsole("The remove operation failed. The tree has not been changed:");
                        }
                    }

                    printTree(tree);
                    if (treeShouldBeSaved) {
                        saveTree(tree);
                    }
                }
            } catch (InvalidEdgeException e) {
                System.err.println(e.getMessage());
            } catch (IOException e) {
                System.err.println("An error occurred during reading / writing: " + e.getMessage());
            } catch (ClassNotFoundException e) {
            System.err.println("Failed to load tree from the file: " + e.getMessage());
            }
        } catch (UnsupportedEncodingException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                consoleWriter.close();
            } catch (IOException e) {
                System.err.println("Failed to close console output: " + e.getMessage());
            }
        }
    }
}
