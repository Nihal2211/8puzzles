import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.MinPQ;
import java.util.*;
import java.lang.*;
import edu.princeton.cs.algs4.Queue;;
public class Board {
    
    
    private int[][] blocks;
    private int N;
    private int moves;
    private int count=0;
   
      private int[][] goalboard;
    
   
    private int xyto1D (int x, int y) {
       int arrayindex = y + 1 +N * x;
       return arrayindex;
   } 
    
    public Board(int[][] block)  { 
       if (block.length <= 0) throw new IllegalArgumentException ("N can't be 0");  // construct a board from an N-by-N array of blocks
       this.N = block.length;
       this.blocks = new int [this.N][this.N];
           //(int)( sizeof(blocks) / sizeof(blocks[0]) );
         for (int i=0; i<this.N ; i++){
           for (int j=0 ; j<this.N; j++){
                    
                   this.blocks[i][j]= block[i][j];
                   
           }}
   
       generate_goalboard(this.N);
      count++;

      }   
    private void generate_goalboard(int N){
        if (count==0){
           goalboard = new int[N][N];
       for (int i=0; i<N ; i++){
           for (int j=0 ; j<N; j++){
               if ((i==N-1) && (j==N-1)){
                    goalboard[i][j]= 0;
                   
               }
               else {
                   goalboard[i][j]= xyto1D(i,j);
               }
                   
           }
           
       }
        goalboard[N-1][N-1] = 0;  
        }  
        
    }// (where blocks[i][j] = block in row i, column j)
    public int dimension()  {
        return N;  }  
    
// board dimension N
    public int hamming()   {
        
      int position;
      position = 0;
      for (int i=0; i<this.N ; i++){
           for (int j=0 ; j<this.N; j++){
               if (blocks[i][j]!=this.goalboard[i][j]){
                   position++;
               }            
           }     
       }
        return position;}    
   

    public int manhattan() {
       int count =0;
       int expectedcolumn;
       int expectedrow;
       int i;
       int j;
       for (i =0; i<this.N;i++){
           for (j=0;j<this.N;j++){
               if ((this.blocks[i][j]!=this.goalboard[i][j]) && (this.blocks[i][j] !=0)){
                  int num= this.blocks[i][j];
                  if ((num % this.N) ==0) {
                    expectedcolumn = N-1;
                    expectedrow = (this.N/num)-1;
                  }
                  else {
                      expectedrow = num/this.N;
                      expectedcolumn = (num % this.N)-1;
                      
                      }
                 count = count+ Math.abs(expectedrow-i)+Math.abs(expectedcolumn-j);
               }
           }
       }
       
    return count;}                // sum of Manhattan distances between blocks and goal
   
    public boolean isGoal()  {
        if (hamming() == 0){
            return true;
        }
    return false;} 
   
    private int[][] copyarray(int [][] temp){
     int[][] twin;
       twin= new int[this.N][this.N];
        for (int b =0; b<this.N;b++){
           for (int a =0; a<this.N;a++){
               twin[b][a] = temp[b][a];}
        }
       return twin;
    }
    
   public Board twin()    {
       int[][] twin;
       twin= new int[this.N][this.N];
       
   
    // int[][] twin = System.arraycopy(twin, 0, this.blocks, 0, this.N);
     int row; 
     int column;
     row = 0;
     column = 0;
     int value1 = 0;
     int value2 = this.blocks[0][0];
     twin = copyarray(this.blocks);
     outerloop :  for (int b =0; b<this.N;b++){
           for (int a =0; a<this.N;a++){
              

               value2 = this.blocks[b][a];
               if ((this.blocks[b][a] !=0) && (value1!=0)&& (a > 0)){
                    row = b;
                    column = a;
                    
                  break outerloop;
               }
               value1 = this.blocks[b][a];
           }
     }
    
//     System.out.println("vak" + value1);
//       System.out.println("vaf" + value2);
    twin[row][column]= value1;
    twin[row][column-1] = value2;
    
    return new Board(twin);
   }  
    
// a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y)  {
        
     if (y == this.blocks) return true;
     if (y == null) return false;
     if (y.getClass() != this.getClass()) return false;
      Board other = (Board) y;
        if (this.dimension() != other.dimension()) {
            return false;
        }
         if (this.hamming() != other.hamming()) {
            return false;
        }
      for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (this.blocks[row][col] != other.blocks[row][col]) {
                    return false;
                }
            }
        }
       // Transaction that = (Transaction) other;
       // return (this.amount == that.amount) && (this.who.equals(that.who))
     
     return true;//&& (this.when.equals(that.when));
    }      // does this board equal y?
   public Iterable<Board> neighbors()   {
     
      Queue<Board> queue = new Queue<Board>();  
      int a =0; 
      int b =0;
      
       loop:  for (int row =0; row<this.N;row++){
           for (int column =0; column<this.N;column++){
//               System.out.println(this.blocks[row][column]);
             if (this.blocks[row][column]==0){
                 b=row;
                 a= column;
             break loop;
             }
           }
        }  
//         System.out.println(b + "sfs" + a);
           //left // column greater than 0
           if ((a>0)&&(this.blocks[b][a]==0)){
           int [][] ar;
           ar= copyarray(this.blocks);
           Board s;
           s= new Board (swap(ar,b,a,b,a-1));
           queue.enqueue(s);
//            System.out.println("With left");
//            System.out.println(toString());
           }
           
           //top // row greater than 0
           if ((b>0)&&(this.blocks[b][a]==0)) {
                      int [][] ar;
                      ar= copyarray(this.blocks);
            Board s = new Board(swap(ar,b,a,b-1,a)); 
//            System.out.println("With top");
//                        System.out.println(toString());
           queue.enqueue(s);}
           
           //right // columns should be smaller than n-1
           if ((a < this.N-1)&&(this.blocks[b][a]==0)) {
                                 int [][] ar;
                                 ar= copyarray(this.blocks);
                                 Board s = new Board(swap(ar,b,a,b,a+1));
//                                      System.out.println("With right");
//                                             System.out.println(toString());
           queue.enqueue(s);}
           
           //bottom // columns should be smaller than n-1
           if ((b < this.N-1)&&(this.blocks[b][a]==0)) {
           int [][] ar;
           ar= copyarray(this.blocks);
             Board s = new Board(swap(ar,b,a,b+1,a));
//                  System.out.println("With bottom");
//                         System.out.println(toString());
           queue.enqueue(s);}
           
           return queue;
     }
     
      
      // all neighboring boards*/
    
     private int[][] swap(int[][] a, int i, int j, int i1, int j1) {
        
        int tmp = a[i1][j1];
        a[i1][j1] = a[i][j];
        a[i][j] = tmp;
       // System.out.println("with a");
       // System.out.println(toString());
        return a;
    }
    
    public String toString()  {
     StringBuilder s = new StringBuilder();
    s.append(N + "\n");
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            s.append(String.format("%2d ", blocks[i][j]));
        }
        s.append("\n");
    }
    return s.toString();}             // string representation of this board (in the output format specified below)

    public static void main(String[] args) {
       /*int [][]tiles;
        String filename = args[0];
        In in = new In(filename);
        int arg0 = in.readInt();
        tiles = new int[arg0][arg0];
        Board gameboard = new Board(tiles);*/
        
      
}
}