


public class Knapsack01 {

    static int max(int a, int b){
        return (a > b) ? a : b; //returns max value between two inputs
    }
    static int knapSack(int W, int wt[], int val[], int n){ //Knapsack function, inputs capacity, weight and value array, and length of value array (n)

        int i, j; //row col
        int K[][] = new int[n + 1][W + 1];
        //Build K solution array from bottom up.
        for(i=0; i<= n; i++){
            for(j = 0; j<= W; j++){
                if(i==0 || j == 0){
                    K[i][j]=0; //row 0 and col 0 are set to 0 Base case
                }else if(wt[i-1]<=j){
                    K[i][j]= max(val[i-1] + K[i-1][j-wt[i-1]], K[i-1][j]); //Recomputations of the same subproblems are avoided in bottom up. return maximum of two cases, includes or doesnt.
                }else{
                    K[i][j]= K[i-1][j]; //Iterative instead of recursive giving time complexity of O(nW) instead of O(2^n), store solutions in the matrix, and do not recompute same subproblems
                }

            }

        }

        return K[n][W]; //total value of solution set.


    }
}
