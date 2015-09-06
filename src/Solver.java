import edu.princeton.cs.algs4.In;
//import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.util.*;
import java.lang.*;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private boolean isSolvable = true;
     private Node searchnode;
        
     private Node searchnodetwin;
     
         
  private class Node implements Comparable {
   
     private Node previous;
    private Board board;
    private int priority = 0;
    
   
    private int moves = 0;
  
   private Node(Board board, Node previous) {
        
        this.board = board;
       this.previous = previous;
        if (previous == null){
            moves = 0 ;}
        else {
            moves = previous.moves+1;
        }
        this.moves = moves;
        this.priority = this.moves + board.hamming();
    }
      @Override
      public int compareTo(Object that) {
            if (this.priority > ((Node) that).priority) {
                return 1;
            }
            else if (this.priority < ((Node) that).priority) {
                return -1;
            }

            return 0;
        }
    } 
    
    public Solver(Board initial)   {
        
      
            
       
       searchnode = new Node(initial,null);
       searchnodetwin = new Node(initial.twin(),null);
       
       //searchnode.moves=initial.;
       
     MinPQ<Node> gametree = new MinPQ<Node>() ;
      MinPQ<Node> gametreetwin = new MinPQ<Node>() ;
     
    while (isSolvable==true) {
            if(searchnode.board.isGoal()){
                break;
                
            }

            searchnode= solving(gametree, searchnode);
//            System.out.println("This is real");

            if(searchnodetwin.board.isGoal()){
                isSolvable = false;
                break;
            }

            searchnodetwin = solving(gametreetwin, searchnodetwin);
//             System.out.println("This is twin");
        }
    
    }       
    

   // find a solution to the initial board (using the A* algorithm)
   private Node solving(MinPQ<Node> minpq, Node node)
    {
        Iterable<Board> n = node.board.neighbors();

        for (Board gameboard : n) {
            if (node.previous == null || !node.previous.board.equals(gameboard)) {
                minpq.insert(new Node(gameboard, node));
            }
        }

        return minpq.delMin();
    } 
    
    public boolean isSolvable()  {
       return isSolvable; }          // is the initial board solvable?
    
    public int moves()    {      
     if (isSolvable()){return searchnode.moves;}
     return -1; }                 // min number of moves to solve initial board; -1 if unsolvable
    
    public Iterable<Board> solution()   {
        if(!isSolvable){
            return null;
        }
        Stack<Board> stack = new Stack<Board>();
        Node a = searchnode;
        while (a != null) {
            stack.push(a.board);
            a=a.previous;
        }
        return stack;}
    
    // sequence of boards in a shortest solution; null if unsolvable 
    // this method for printing out is given in the lecture
    public static void main(String[] args) {
         
    In in = new In(args[0]);
    int N = in.readInt();
    
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++){
        for (int j = 0; j < N; j++){
            
            blocks[i][j] = in.readInt();
            }
    }
   
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    } 
    }  
}