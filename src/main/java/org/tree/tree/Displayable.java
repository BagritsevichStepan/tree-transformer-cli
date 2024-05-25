package org.tree.tree;

import java.io.BufferedWriter;
import java.io.IOException;

public interface Displayable {
    void display(BufferedWriter output) throws IOException;
}
