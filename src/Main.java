import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        BufferedReader br = null;
        String line = null;
        int capacity;   //both objects have same capacity and contents
        ArrayList<Integer> wt = new ArrayList<>();      //Weight array list, unsorted
        ArrayList<Integer> val = new ArrayList<>();     //Value array list, unsorted
        Knapsack01 K01 = new Knapsack01();    //create new Dynamic Knapsack object
        System.out.println("Please enter the location of the input text file");

        //Format must be: C:\\Users...\input.txt


        /*
        Input.txt file format:
        First entry is capacity, just put number and no commas, then start new line for file reader to
        extract the weights and values into arrays
        example:
        100          <--- capacity
        30, 40       <weight>, <value>
        20, 10       <weight>, <value>
         */

        try {
            br = new BufferedReader(new FileReader(sc.nextLine()));
            line = br.readLine();
        } catch (FileNotFoundException fnfex) {
            System.err.println("File not found");
        }
        capacity = Integer.valueOf(line);               //takes first line and stores value into capacity variable
            line = br.readLine();
        while (line != null) {
            String[] lineParts = line.split(",");
            wt.add(Integer.valueOf(lineParts[0]));      //splits strings and adds values into appropriate arrays
            val.add(Integer.valueOf(lineParts[1]));
            line = br.readLine();
        }

            System.out.println(wt);     //prints weight array
            System.out.println(val);    //prints value array
            System.out.println(capacity);   //prints capacity

        /*
        * Since the knapsack functions must be array inputs and not array lists,
        * we need to convert weight and value arrayslists into arrays before passing them
         */
            int[] weight = new int[wt.size()];
            int[] value = new int[val.size()];
            for (int i = 0; i < wt.size(); i++) {
                weight[i] = wt.get(i);
                value[i] = val.get(i);
            }
            //Finally, print total values for each approach
            System.out.println("\n\n");
            System.out.println("Total Value of 0-1 Knapsack Dynamic: " + K01.knapSack(capacity, weight, value, value.length));
        }
    }
