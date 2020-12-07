// Java program to solve Traveling Salesman Problem
// using Best First Branch and Bound.
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;

public class TSP {
    /*
    * In order to obtain a global static variable N that changes based
    * on the size of the matrix for input file, we must obtain it
    * through a method call GetN()
    * GetN() requires the filepath for the vertices.txt file
    * obtained through the setFilepath() method.
     */
    public static String filepath = setFilepath();
    public static int N = GetN();


    public static int paths=1;
    // final_path[] stores the final solution ie, the
    // path of the salesman.
    static int final_path[] = new int[N + 1];

    // visited[] keeps track of the already visited nodes
    // in a particular path
    static boolean visited[] = new boolean[N];

    // Stores the final minimum weight of shortest tour.
    static int final_res = Integer.MAX_VALUE;

    // Function to copy temporary solution to
    // the final solution
    static void copyToFinal(int curr_path[])
    {
        for (int i = 0; i < N; i++) {
            final_path[i] = curr_path[i];
        }
        final_path[N] = curr_path[0];
    }

    // Function to find the minimum edge cost
    // having an end at the vertex i
    static int firstMin(int adj[][], int i)
    {
        int min = Integer.MAX_VALUE;
        for (int k = 0; k < N; k++)
            if (adj[i][k] < min && i != k)
                min = adj[i][k];
        return min;
    }

    // function to find the second minimum edge cost
    // having an end at the vertex i
    static int secondMin(int adj[][], int i)
    {
        int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
        for (int j=0; j<N; j++)
        {
            if (i == j)
                continue;

            if (adj[i][j] <= first)
            {
                second = first;
                first = adj[i][j];
            }
            else if (adj[i][j] <= second &&
                    adj[i][j] != first)
                second = adj[i][j];
        }
        return second;
    }

    /* function that takes as arguments:
    * curr_bound -> lower bound of the root node
    * curr_weight-> stores the weight of the path so far
    *  level-> current level while moving in the search
    *        space tree
    *  curr_path[] -> where the solution is being stored which
    *             would later be copied to final_path[]
    */
    static void TSPRec(int adj[][], int curr_bound, int curr_weight,
                       int level, int curr_path[])
    {
        // base case is when we have reached level N which
        // means we have covered all the nodes once
        if (level == N)
        {
            // check if there is an edge from last vertex in
            // path back to the first vertex
            if (adj[curr_path[level - 1]][curr_path[0]] != 0)
            {
                // curr_res has the total weight of the
                // solution we got
                int curr_res = curr_weight +
                        adj[curr_path[level-1]][curr_path[0]];

                // Update final result and final path if
                // current result is better.
                if (curr_res < final_res)
                {
                    for(int i=0; i<N;i++)
                    System.out.println("Node taken: " + curr_path[i]);

                    copyToFinal(curr_path);
                    final_res = curr_res;
                }
            }
            return;
        }

        // for any other level iterate for all vertices to
        // build the search space tree recursively
        for (int i = 0; i < N; i++)
        {
            // Consider next vertex if it is not same (diagonal
            // entry in adjacency matrix and not visited
            // already)
            if (adj[curr_path[level-1]][i] != 0 &&
                    visited[i] == false)
            {
                int temp = curr_bound;
                curr_weight += adj[curr_path[level - 1]][i];

                // different computation of curr_bound for
                // level 2 from the other levels
                if (level==1)
                    curr_bound -= ((firstMin(adj, curr_path[level - 1]) +
                            firstMin(adj, i))/2);
                else
                    curr_bound -= ((secondMin(adj, curr_path[level - 1]) +
                            firstMin(adj, i))/2);

                // curr_bound + curr_weight is the actual lower bound
                // for the node that we have arrived on
                // If current lower bound < final_res, we need to explore
                // the node further
                if (curr_bound + curr_weight < final_res)
                {
                    curr_path[level] = i;
                    visited[i] = true;

                    // call TSPRec for the next level
                    TSPRec(adj, curr_bound, curr_weight, level + 1,
                            curr_path);
                    paths++;


                }

                // Else we have to prune the node by resetting
                // all changes to curr_weight and curr_bound
                curr_weight -= adj[curr_path[level-1]][i];
                curr_bound = temp;

                // Also reset the visited array
                Arrays.fill(visited,false);
                for (int j = 0; j <= level - 1; j++)
                    visited[curr_path[j]] = true;
            }
        }
    }

    // This function sets up final_path[]
    static void TSP(int adj[][])
    {
        int curr_path[] = new int[N + 1];

        /* Calculate initial lower bound for the root node
        * using the formula 1/2 * (sum of first min +
        * second min) for all edges.
        * Also initialize the curr_path and visited array
        */
        int curr_bound = 0;
        Arrays.fill(curr_path, -1);
        Arrays.fill(visited, false);

        // Compute initial bound
        for (int i = 0; i < N; i++)
            curr_bound += (firstMin(adj, i) +
                    secondMin(adj, i));

        // Rounding off the lower bound to an integer
        curr_bound = (curr_bound==1)? curr_bound/2 + 1 :
                curr_bound/2;

        // We start at vertex 1 so the first vertex
        // in curr_path[] is 0
        visited[0] = true;
        curr_path[0] = 0;

        // Call to TSPRec for curr_weight equal to
        // 0 and level 1
        TSPRec(adj, curr_bound, 0, 1, curr_path);

    }

    // Driver code
    public static void main(String[] args)throws FileNotFoundException{
            //Print N Size matrix for adjacent vertices
            System.out.println(N);
            int[][] adj;
            File input = new File(filepath);
            Scanner sc = new Scanner(input);

            //Set filepath to file
            //Read file and parse for ints
            //into a 2D array adj
            int intLength = 0;
            String[] length = sc.nextLine().trim().split("\\s+");
            for(int i=0; i<length.length;i++){
                intLength++;
            }
            sc.close();
            adj = new int[intLength][intLength];
           sc = new Scanner(input);
            int lineCount = 0;
            while(sc.hasNextLine()){
                String[] currentLine = sc.nextLine().trim().split("\\s+");
                for(int i=0; i<currentLine.length;i++){
                    adj[lineCount][i] = Integer.parseInt(currentLine[i]);
                }
                lineCount++;
            }


        //Start Travelling Salesman Problem Algortihm (Best First, Branch and Bound)
        TSP(adj);

            //Calculate total paths possible
            //Utilizes BigInteger to handle large values, and prints after optimal path is found
        BigInteger totalPaths = new BigInteger("1");
        BigInteger k = new BigInteger("1");
        while(k.intValueExact()<=N-1){
            totalPaths = totalPaths.multiply(k);
            k = k.add(BigInteger.ONE);
        }
        System.out.println("Paths checked: " + paths );
        System.out.println("Total possible paths: "+totalPaths);
        System.out.printf("Minimum cost : %d\n", final_res);
        System.out.printf("Path Taken : ");
        for (int i = 0; i <= N; i++)
        {
            System.out.printf("%d ", final_path[i]);
        }
    }
    public static int GetN(){
        int z=0;
        try {
            //Can't throw file not found exceptions
            //must use Try catch to handle
            //sets static variable N to z value, number of nodes
            File input = new File(filepath);
            Scanner sc = new Scanner(input);
            int intLength = 0;
            String[] length = sc.nextLine().trim().split("\\s+");
            for (int i = 0; i < length.length; i++) {
                intLength++;
            }
            sc.close();
            z = intLength;
        }catch (FileNotFoundException F){
            System.err.println("File not found; please enter format C:\\Users\\etc..");
        }
        return z;

    }
//Set file path to filepath static variable
    private static String setFilepath(){
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter file path for vertices.txt: ");
        String filepath = reader.nextLine();
        return filepath;
    }


}