/* Graphs - Programming Project 4 - Adhish Thite | Ved Paranjape */

/**

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

 */

import java.io.*;
import java.util.*;

public class Graphs {

    // Input Filename
    private final static String dataFileName = "karate.gml";

    // Instantiate Vertices and Edges Class
    private List<Vertex> vertexList = new ArrayList<Vertex>();
    private List<Edge> edgeList = new ArrayList<Edge>();

    // Adjacency Matrix
    private Integer[][] adjMap;
    private LinkedList<Integer> adjacencyList[];

    // Flag to check whether the graph is built
    private static Boolean isGraphBuilt = false;

    // Set MAX Value
    private static Integer INFINITY = 99999999;

    // Performance of Algorithm
    private Long performance = -1L;

    public static void main(String[] args) {
        Graphs graphs = new Graphs();

        String choice = null;

        while (choice == null || !choice.equalsIgnoreCase("exit")) {
            System.out.println("\nEnter Command:\t");

            Scanner sc = new Scanner(System.in);
            choice = sc.nextLine();

            switch (choice.toLowerCase()) {
                case "graph":
                    graphs.buildGraph(dataFileName);
                    break;

                case "print":
                    graphs.printGraph();
                    break;

                case "dijkstra":
                    graphs.dijkstra();
                    break;

                case "bfs":
                    graphs.BFS();
                    break;

                case "performance":
                    graphs.getPerformance();
                    break;

                case "quit":
                case "exit":
                    return;

                default:
                    System.out.println("\n" + choice + " - Invalid Command, type \'quit\' to close this program.");
                    break;
            }
        }
    }


    // Builds the Graph using the Input File
    private void buildGraph(String fileName) {
        File inputFile = new File(fileName);

        try {
            BufferedReader buffReader = new BufferedReader(new FileReader(inputFile));
            String readLine;

            while ((readLine = buffReader.readLine()) != null) {
                readLine = readLine.trim();

                String nextLine;

                // Parsing the input data

                // NODE (VERTICES CLASS) - BEGIN
                if (readLine.equalsIgnoreCase("node")) {
                    nextLine = buffReader.readLine();
                    if (nextLine.trim().equalsIgnoreCase("[")) {
                        nextLine = buffReader.readLine();

                        if (nextLine.contains("id")) {
                            vertexList.add(new Vertex(Integer.parseInt(nextLine.trim().split("\\s+")[1])));
                        }
                    }
                }

                // Initializing the Adjacency Matrix
                adjMap = new Integer[vertexList.size()][vertexList.size()];

                // NODE (VERTICES CLASS) - END

                // EDGES - BEGIN
                if (readLine.equalsIgnoreCase("edge")) {
                    Integer sourceId = null;
                    Integer targetId = null;

                    nextLine = buffReader.readLine();
                    if (nextLine.trim().equalsIgnoreCase("[")) {
                        nextLine = buffReader.readLine();

                        if (nextLine.contains("source")) {
                            sourceId = Integer.parseInt(nextLine.trim().split("\\s+")[1]);
                        }

                        nextLine = buffReader.readLine();

                        if (nextLine.contains("target")) {
                            targetId = Integer.parseInt(nextLine.trim().split("\\s+")[1]);
                        }
                    }

                    if (sourceId != null && targetId != null) {
                        edgeList.add(new Edge(sourceId, targetId));
                    }

                }
                // EDGES - END
            }
            buffReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initializing the Adjacency Matrix - BEGIN

        int size = vertexList.size();

        adjMap = new Integer[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                adjMap[i][j] = INFINITY;
            }
        }

        // Initializing the Adjacency Matrix - END

        // Updating Adjaceny Matrix with Edges - BEGIN
        for (Edge tempEdge : edgeList) {
            adjMap[tempEdge.sourceId - 1][tempEdge.targetId - 1] = adjMap[tempEdge.targetId - 1][tempEdge.sourceId - 1] = 1;
        }
        // Updating Adjaceny Matrix with Edges- END

        // Initializing Adjacency List - BEGIN
        adjacencyList = new LinkedList[size];

        for (int i = 0; i < size; i++) {
            adjacencyList[i] = new LinkedList();
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (adjMap[i][j] == 1) {
                    adjacencyList[i].add(j);
                }
            }
        }
        // Initializing Adjacency List - END

        // Set the isGraphBuilt property to TRUE
        isGraphBuilt = true;
    }

    // Prints the Graph

    private void printGraph() {
        if (!isGraphBuilt) {
            System.out.println("\nGraph is not built. Please use the \'graph\' command to build the graph.\n");
        } else {
            int size = vertexList.size();

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (adjMap[i][j] == INFINITY) {
                        System.out.print(" 0 ");
                    } else {
                        System.out.print(" " + adjMap[i][j] + " ");
                    }
                }
                System.out.println();
            }
        }
    }

    // Algorithm Performance

    private void getPerformance() {
        if (performance == -1) {
            System.out.println("\nAlgorithm has not been implemented !\n");
        } else {
            System.out.println("\nPerformance:\t\t" + performance + " ms");
        }
    }

    // VERTEX CLASS

    private class Vertex {
        Integer id;

        private Vertex(Integer id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return ("\nVertex Id:\t" + id);
        }
    }

    // EDGES CLASS

    private class Edge {
        Integer sourceId;
        Integer targetId;

        private Edge(Integer sourceId, Integer targetId) {
            this.sourceId = sourceId;
            this.targetId = targetId;
        }

        @Override
        public String toString() {
            return ("\nEdge:\n\t" + "Source Id:\t" + sourceId + "\n\tTarget Id:\t" + targetId);
        }
    }


    /*****************************************************************************************************************************************/
    /* ALGORITHMS IMPLEMENTATION */

    /* DIJKSTRA'S ALGORITHM */

    private void dijkstra() {
        if (!isGraphBuilt) {
            System.out.println("\nGraph is not built. Please use the \'graph\' command to build the graph.\n");
        } else {
            // Take the Input Source from user
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter Source Vertex:\t");
            Integer source = sc.nextInt();

            /* DIJKSTRA'S ALGORITHM START */

            // Capture Performance Details
            Long startTime = System.currentTimeMillis();

            Integer distances[] = new Integer[vertexList.size()];

            Boolean shortestTree[] = new Boolean[vertexList.size()];

            for (int i = 0; i < vertexList.size(); i++) {
                distances[i] = INFINITY;
                shortestTree[i] = false;
            }

            distances[source] = 0;

            for (int index = 0; index < vertexList.size() - 1; index++) {

                int u = getMinimumDistance(distances, shortestTree);

                shortestTree[u] = true;

                for (int v = 0; v < vertexList.size(); v++) {
                    if (!shortestTree[v] && adjMap[u][v] != 0 && distances[u] != Integer.MAX_VALUE && distances[u] + adjMap[u][v] < distances[v]) {
                        distances[v] = distances[u] + adjMap[u][v];
                    }
                }
            }

            /* DIJKSTRA'S ALGORITHM START */

            performance = System.currentTimeMillis() - startTime;

            System.out.println();
            System.out.println("Shortest Distance of "+source+" from ");
            for (int i = 0; i < vertexList.size(); i++) {
                System.out.println(i+" is "+distances[i]);
            }
        }
    }


    // Get Minimum Distance

    private int getMinimumDistance(Integer dist[], Boolean shortestTree[]) {

        Integer minimum = INFINITY;
        Integer min_index = -1;

        for (int v = 0; v < vertexList.size(); v++) {
            if (!shortestTree[v] && dist[v] <= minimum) {
                minimum = dist[v];
                min_index = v;
            }
        }
        return min_index;
    }

    /*****************************************************************************************************************************************/

    /* BREADTH FIRST SEARCH */

    void BFS() {

        if (!isGraphBuilt) {
            System.out.println("\nGraph is not built. Please use the \'graph\' command to build the graph.\n");
        } else {

            // Take the Input Source from user
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter Source Vertex:\t");
            Integer source = sc.nextInt();

            /* BREADTH FIRST SEARCH ALGORITHM - START */

            // Capture Performance Details
            Long startTime = System.currentTimeMillis();

            boolean visited[] = new boolean[vertexList.size()];

            LinkedList<Integer> queue = new LinkedList<Integer>();

            visited[source] = true;
            queue.add(source);

            System.out.println("\n\tBreadth First Traversal is :");

            while (queue.size() != 0) {
                source = queue.poll();
                System.out.print("\t" + source + " ");
                System.out.println();
                Iterator<Integer> i = adjacencyList[source].listIterator();
                while (i.hasNext()) {
                    int n = i.next();
                    if (!visited[n]) {
                        visited[n] = true;
                        queue.add(n);
                    }
                }
            }
            /* BREADTH FIRST SEARCH ALGORITHM - START */
            performance = System.currentTimeMillis() - startTime;

            System.out.println();
            System.out.println();
        }
    }
}