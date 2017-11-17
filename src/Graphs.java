/* Graphs - Programming Assignment 4 - Adhish Thite | Ved Paranjape */

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Graphs {

    // Instantiate Vertices and Edges Class

    private List <Vertex> vertexList = new ArrayList<Vertex>();
    private List <Edge> edgeList = new ArrayList<Edge>();
    private Integer[][] adjMap;
    private Boolean isGraphBuilt = false;

    private static Integer INFINITY = 99999999;

    public static void main(String[] args) {
        Graphs graphs = new Graphs();

        String dataFileName = "karate.gml";
        String choice = null;

        while (choice == null || !choice.equalsIgnoreCase("exit")){
            System.out.println("\nEnter Command:\t");

            Scanner sc = new Scanner(System.in);
            choice = sc.nextLine();

            switch (choice) {
                case "graph":
                    graphs.buildGraph(dataFileName);
                    break;

                case "fw":
                case "FW" :
                    graphs.doFloydWarshall();
                    break;

                case "exit":
                case "Exit":
                    break;

                default:
                    System.out.println("\n" + choice + " - Invalid Command, type \'exit\' to close this program.");
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
                    if(nextLine.trim().equalsIgnoreCase("[")) {
                        nextLine = buffReader.readLine();

                        if(nextLine.contains("id"))  {
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
                    if(nextLine.trim().equalsIgnoreCase("[")) {
                        nextLine = buffReader.readLine();

                        if(nextLine.contains("source"))  {
                            sourceId = Integer.parseInt(nextLine.trim().split("\\s+")[1]);
                        }

                        nextLine = buffReader.readLine();

                        if(nextLine.contains("target"))  {
                            targetId = Integer.parseInt(nextLine.trim().split("\\s+")[1]);
                        }
                    }

                    if(sourceId != null && targetId != null) {
                        edgeList.add(new Edge(sourceId,targetId));
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

        for(int i = 0; i < size; i ++) {
            for(int j = 0; j < size; j++) {
                adjMap[i][j] = INFINITY;
            }
        }

        // Initializing the Adjacency Matrix - END

        // Updating Adjaceny Matrix with Edges - BEGIN
        for(Edge tempEdge : edgeList) {
            adjMap[tempEdge.sourceId - 1][tempEdge.targetId - 1] = adjMap[tempEdge.targetId - 1][tempEdge.sourceId - 1] = 1;
        }
        // Updating Adjaceny Matrix with Edges- END

        for(int i = 0; i < size; i ++) {
            for(int j = 0; j < size; j++) {
                if(adjMap[i][j] == INFINITY){
                    System.out.print(" 0 ");
                } else {
                    System.out.print(" " + adjMap[i][j] + " ");
                }
            }
            System.out.println();
        }

        // Set the isGraphBuilt property to TRUE
        isGraphBuilt = true;
    }

    // Vertex Class

    private class Vertex {
        Integer id;

        public Vertex(Integer id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return ("\nVertex Id:\t" + id);
        }
    }

    // Edges Class

    private class Edge {
        Integer sourceId;
        Integer targetId;

        public Edge(Integer sourceId, Integer targetId) {
            this.sourceId = sourceId;
            this.targetId = targetId;
        }

        @Override
        public String toString() {
            return ("\nEdge:\n\t" + "Source Id:\t" + sourceId + "\n\tTarget Id:\t" + targetId);
        }
    }

    /* ALGORITHMS IMPLEMENTATION */

    /* FLOYD-WARSHALL ALGORITHM */

    private void doFloydWarshall() {
        int size = vertexList.size();

        Integer[][] distanceMatrix = new Integer[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                distanceMatrix[i][j] = adjMap[i][j];
            }
        }

        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (distanceMatrix[i][k] + distanceMatrix[k][j] < distanceMatrix[i][j]) {
                        distanceMatrix[i][j] = distanceMatrix[i][k] + distanceMatrix[k][j];
                    }
                }
            }
        }

        // Print the Output of Floyd-Warshall Algorithm Implementation
        for(int i = 0; i < size; i ++) {
            for(int j = 0; j < size; j++) {
                if(distanceMatrix[i][j] == INFINITY){
                    System.out.print(" 0 ");
                } else {
                    System.out.print(" " + distanceMatrix[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}