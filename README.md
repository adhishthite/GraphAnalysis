# Implementation of Graph Algorithms

### Algorithms and Data Structures - Programming Project 4

##### https://github.com/athiteuncc/GraphAnalysis

***
Adhish Thite<br>
Ved Paranjape
---

This program is an implementation of the 'Dijkstra's' and 'Breadth First Search' Graph algorithms in Java, using the 'Zachary's Karate Club' dataset

---

**Compiler used** : JAVAC<br>
**Platform** :  macOS High Sierra 10.13<br>
**IDE** : IntelliJ IDEA Community 2017.2

---
##### Dataset
+ We have used the '**Zachary's Karate Club**' dataset which is a social network of friendships between 34 members of a karate club at a US university in the 1970s.<br>
+ **Source** - W. W. Zachary, An information flow model for conflict and fission in small groups, Journal of Anthropological Research 33, 452-473 (1977)

##### Program Design
+ The start of program begins in the 'main' function where we get the command from the user to perform.

+ The main command is - **'graph'**<br>
We use custom inner classes called as 'Edges' and 'Vertices' to build the graph.<br>
We then create an _Adjacency Matrix_ and an _Adjacency List_ from the Vertices and Edges.

+ **'print'** Command<br>
Prints the adjacency matrix representation of the Graph.<br>

+ **'bfs'** Command<br>
Takes the input vertex from the user and then prints the _Breadth First_ Traversal of the graph.

+ **'dijkstra'** Command <br>
Implements the Dijsktra's Algorithm to find the shortest path from the input source vertex.<br>The input vertex is taken from the user. <br>

+ **'performance'** Command<br>
Prints the performance of the algorithm which has been last run on the graph.<br>

+ **'quit'** Command<br>
Quits the program.<br>

##### Data Structures
+ Inner Class - **'Edge'**<br>
Stores the _edges_ by taking the _'sourceId'_ and _'targetId'_.

+ Inner Class - **'Vertex'**<br>
Stores the _edges_ by taking the _'id'_ from the input data file.<br>

+ **Adjacency Matrix**
+ **Adjacency List**

##### Running the program
+ Open the Command Prompt(Windows) OR Terminal(Mac/Linux/UNIX) and navigate to the folder where the 'graphs.java' file exists.
+ Make sure that the data input file (eg. karate.gml) file is placed in the same folder; otherwise the program will throw an error.
+ Run the following commands :<br>
`javac Graphs.java`<br>
`java Graphs`
+ User can enter the following commands -<br>
    1. graph
    2. print
    3. bfs
    4. dijkstra
    5. performance
    6. quit
    
+ Note that the **graph** command is the primary command. No other command will work unless the **graph** command is used.

##### Program Limitations
+ The program will fail for all other datasets as it is tuned to Karate dataset only.
+ The program will fail if the source vertex for 'bfs' and 'dijkstra' command is out of bounds of the adjacency matrix.

##### Analytics

**Dijkstra's Algorithm**

+ The Dijkstra’s algorithm’s output gives the output as the shortest distances from the source vertex provided to all nodes in the friendship graph from the karate dataset.<br>
+ The shortest distance to each node signifies how strongly the person (source node) knows the respective people.<br>
+ A low distance means that the person knows the other person very well and the larger the distance, weaker the friendship.<br>
+ Another observation that we made was that every node had some non-infinitive distance from each other node, which implies that the social graph in the dataset was completely connected and there was a strong sense of affability among the karate group. 

**Bread First Search Algorithm**

+ The BFS algorithm gives the breadth first traversal of the graph from each person (source node).
+ If we build a tree from this BFS traversal, every level of the BFS traversal tree signifies the level of social connection that the person has in the group.