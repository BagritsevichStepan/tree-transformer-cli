# Tree Transformer

The repository contains the implementation of CLI that can generate, print and save trees. Also calculates the list of transformations required to get the second tree from the first.

## Links
* [Usage](#usage)
  * [First part. Transforming](#transforming)
  * [Second part. Creating](#creating)
* [Project Details](#project-details)
  * [Tree](#tree)
  * [Transformer](#transformer)


## Usage

https://user-images.githubusercontent.com/43710058/228082494-96f4eae6-89a0-482c-96aa-b456ec166459.mp4

Run the programm using executable file.

The CLI is developed with [picocli](https://picocli.info). To find out what startup parameters exist, use the `-h`, `--help` or `help` parameter, as shown in the video.

### <a name="transforming"></a>First part. Transforming

This program accepts 2 general trees as an input and calculates the list of transformations required to get the second tree from the first.

To run it use the command `./tree-transformer.sh transforming`. You can add the parameter `-p` or `--print-trees` to print both trees to the consonle. Also you can define the files name in parameters or print them in the console after the programm starts.

As a result you will get a list of transformations required to get the second tree from the first.

<img width="791" alt="Bildschirmfoto 2023-03-28 um 01 04 14" src="https://user-images.githubusercontent.com/43710058/228086331-f55865f0-899b-4ef3-b7ee-e1050c0f124c.png">

#### Input Files Format

The program accepts trees in the format:

`$[${parentId1}$,${id1}$]$[${parentId2}$,${id2}$]$...`

where `$` - the different number of whitespaces. All ids must have `Long` format.

### <a name="creating"></a>Second part. Creating

This programm accepts `ADD`, `REMOVE` and `EXIT` commands and applies them to the tree stored in memory. After each command, the current tree structure is printed to the console. After each command the programm saves and loads the generated tree in binary format.

To run it use the command `./tree-transformer.sh creating`. You can add the parameter `-l` or `--load-tree` to load the tree from file before the program starts. Also you can define the file name in parameters, which will be used to save the tree and from which the tree will be loaded. If you do not specify it, the standard file `tree.txt` will be used.

Аfter the program starts, you can generate the tree with the `ADD` and `REMOVE` commands. To end the program use the `EXIT` command.

<img width="761" alt="Bildschirmfoto 2023-03-28 um 01 31 33" src="https://user-images.githubusercontent.com/43710058/228089848-22e91538-730b-479c-9f91-063a908b6946.png">

#### Commands Format

The program accepts commands in the format:

`$ADD(${parentId}$,${id}$)$`, `$REMOVE(${id}$)$`

where `$` - the different number of whitespaces. The program is _case-insensitive_, so you can print `reMoVe` too. All ids must have `Long` format.

## Project Details

### Tree

The `TreeImpl` class uses `Map` to build tree, so the amortized time for `ADD` and `REMOVE` is `O(1)`. Thus, to build a tree from a file takes `O(n)`.

`SerializableTree` is an abstract class that implements `Tree` and `Externalizable` interfaces. It was decided to implement `Externalizable` because we can save trees more efficiently than with `Serializable`. We only need to serialize the list of edges. And as we can build the tree in `O(n)` it is also fast. All other `tree` classes should extend this class to be serializable.

### Transformer

`TreeTransformer` takes two trees and finds a transformation list for `O(n)`.










