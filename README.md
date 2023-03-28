# Tree Transformer

The repository contains a solution of the [2 test task](https://gist.github.com/mmazurkevich/cff4726d088589e6990088000fbe210f) in [Workspace Model Metadata Storage Team](https://internship.jetbrains.com/projects/1327/).

Technologies that were used: Java Serialization, [Picocli](https://picocli.info), JUnit.

## Links
* [Usage](#usage)
  1. [First part. Transforming](#transforming)
  2. [Second part. Creating](#creating)
* [Project Structure](#project-structure)
  1. [Tree](#tree)
  2. [Transformer](#transformer)
  3. [Parser](#parser)
  4. [Testing](#testing)


## Usage

https://user-images.githubusercontent.com/43710058/228082494-96f4eae6-89a0-482c-96aa-b456ec166459.mp4

To run the plugin use the command `java -jar wmms-test-task2-1.0.jar` in the Terminal or open the project in your IDEA and then press the button `Run`.

The CLI is developed with [picocli](https://picocli.info). To find out what startup parameters exist, use the `-h`, `--help` or `help` parameter, as shown in the video.

### <a name="transforming"></a>First part. Transforming

This program accepts 2 general trees as an input and calculates the list of transformations required to get the second tree from the first.

 To run it use the command `java -jar wmms-test-task2-1.0.jar transforming`. You can add the parameter `-p` or `--print-trees` to print both trees to the consonle. Also you can define the files name in parameters or print them in the console after the programm starts.

As a result you will get a list of transformations required to get the second tree from the first.

<img width="791" alt="Bildschirmfoto 2023-03-28 um 01 04 14" src="https://user-images.githubusercontent.com/43710058/228086331-f55865f0-899b-4ef3-b7ee-e1050c0f124c.png">

#### Input Files Format

The program accepts trees in the format:

`$[${parentId1}$,${id1}$]$[${parentId2}$,${id2}$]$...`

where `$` - the different number of whitespaces. All ids must have `Long` format.

### <a name="creating"></a>Second part. Creating

This programm accepts `ADD`, `REMOVE` and `EXIT` commands and applies them to the tree stored in memory. After each command, the current tree structure is printed to the console. After each command the programm saves and loads the generated tree in binary format using _Java Serialization_.

To run it use the command `java -jar wmms-test-task2-1.0.jar creating`. You can add the parameter `-l` or `--load-tree` to load the tree from file before the program starts. Also you can define the file name in parameters, which will be used to save the tree and from which the tree will be loaded. If you do not specify it, the standard file `tree.txt` will be used.

Аfter the program starts, you can generate the tree with the `ADD` and `REMOVE` commands. To end the program use the `EXIT` command.

<img width="761" alt="Bildschirmfoto 2023-03-28 um 01 31 33" src="https://user-images.githubusercontent.com/43710058/228089848-22e91538-730b-479c-9f91-063a908b6946.png">

#### Commands Format

The program accepts commands in the format:

`$ADD(${parentId}$,${id}$)$`, `$REMOVE(${id}$)$`

where `$` - the different number of whitespaces. The program is _case-insensitive_, so you can print `reMoVe` too. All ids must have `Long` format.

## Project Structure

### Tree

The tree implementation is in the package `tree` and has the following structure:

<img width="684" alt="Bildschirmfoto 2023-03-28 um 02 25 39" src="https://user-images.githubusercontent.com/43710058/228095805-ea6f9ec9-ef95-48e0-8642-0756c2eb8280.png">

`TreeImpl` implements tree, contains class `Node`. The class uses `Map` to build tree, so the amortized time for `ADD` and `REMOVE` is `O(1)`. Thus, to build a tree from a file takes `O(n)`.

`SerializableTree` is an abstract class that implements `Tree` and `Externalizable` interfaces. I decided to implement `Externalizable` because we can save trees more efficiently than with `Serializable`. We only need to serialize the list of edges. And as we can build the tree in `O(n)` it is also fast. All other `tree` classes should extend this class to be serializable.

### Transformer

The tree transformer implementation is in the package `transformer` and has one interface `Transformable` and one class `TreeTransformer` which implemenets this interface. `TreeTransformer` takes two trees and finds a transformation list for `O(n)`.

### Parser

The parser implementation is in the package `parser` and has the following structure: 

<img width="684" alt="Bildschirmfoto 2023-03-28 um 02 48 52" src="https://user-images.githubusercontent.com/43710058/228098372-f10d7594-4401-469c-944c-0fb310b7701b.png">

The interface `Parser` implements parser, the `CharSource` interface is used by parser classes to get the current char.

### Testing

For testing was used JUnit.










