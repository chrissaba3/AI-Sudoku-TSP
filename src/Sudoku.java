import java.io.*;
import java.util.Scanner;

public class Sudoku {
    public static void main(String[] args) throws FileNotFoundException {
        BufferedReader br = null;
        String line = null;
        int[][] matrix;
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter file path for input.txt: ");
        String filepath = reader.nextLine();
        File input = new File(filepath);
        Scanner sc = new Scanner(input);
        int intLength = 0;
        String[] length = sc.nextLine().trim().split("\\s+");
        for(int i=0; i<length.length;i++){
            intLength++;
        }
        sc.close();
        matrix = new int[intLength][intLength];
        sc = new Scanner(input);
        int lineCount = 0;
        while(sc.hasNextLine()){
            String[] currentLine = sc.nextLine().trim().split("\\s+");
                for(int i=0; i<currentLine.length;i++){
                    matrix[lineCount][i] = Integer.parseInt(currentLine[i]);
                }
                lineCount++;
        }

        for(int i=0; i<matrix.length;i++) {
            System.out.println();
            for (int j = 0; j < matrix.length; j++)
                System.out.print(matrix[i][j]+ " ");
        }
        System.out.println("\n\n Result: \n");
        if(Solve(matrix,matrix.length)){
            print(matrix,matrix.length);
        }
        else{
            System.out.println("No solution");
        }


    }
    public static boolean isSafe(int[][] board, int row, int col, int num){
        for(int d = 0; d < board.length; d++){
            if(board[row][d] == num){
                return false;
            }
        }
        for(int r = 0; r < board.length;r++){
            if(board[r][col]==num){
                return false;
            }
        }
        int sqrt = (int)Math.sqrt(board.length);
        int boxRowStart = row - row % sqrt;
        int boxColStart = col - col % sqrt;
        for(int r = boxRowStart; r<boxRowStart+sqrt;r++){
            for(int d = boxColStart; d< boxColStart+sqrt;d++){
                if(board[r][d] == num){
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean Solve(int[][]board, int n){
        int row = -1;
        int col = -1;
        boolean isEmpty= true;
        for(int i=0; i<n;i++){
            for(int j=0; j<n;j++){
                if(board[i][j]==0){
                    row = i;
                    col = j;
                    isEmpty=false;
                    break;
                }
            }
            if(!isEmpty){
                break;
            }
        }
        if(isEmpty){
            return true;
        }
        for(int num = 1; num<=n;num++){
            if(isSafe(board,row,col,num)){
                board[row][col]=num;
                if(Solve(board,n)){
                    return true;
                }
                else{
                    board[row][col]=0;
                }
            }
        }
        return false;
    }
    public static void print(int[][] board, int N)
    {
        // Print solution set line
        for (int r = 0; r < N; r++)
        {
            for (int d = 0; d < N; d++)
            {
                System.out.print(board[r][d]);
                System.out.print(" ");
            }
            System.out.print("\n");

            if ((r + 1) % (int) Math.sqrt(N) == 0)
            {
                System.out.print("");
            }
        }
    }
}
