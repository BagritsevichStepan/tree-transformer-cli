package com.jetbrains.internship;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

@Command(
        name = "Tree Transformer",
        subcommands = {TreeTransforming.class, TreeCreating.class, CommandLine.HelpCommand.class},
        description = "Can generate, print and save trees. Also calculates the list of transformations required to get the second tree from the first."
)
public class TreeCLI {
    @Spec
    CommandSpec commandSpec;

    public static void main(String[] args) {
        new CommandLine(new TreeCLI()).execute(args);
    }
}
