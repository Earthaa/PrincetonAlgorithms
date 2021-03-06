import edu.princeton.cs.algs4.*;
public class Solver {
	private Stack<Board> answer= new Stack<Board>();
	private MinPQ<Step> InitPQ =new MinPQ<Step>();
	private MinPQ<Step> TwinPQ =new MinPQ<Step>();
	private Boolean Solvable = false;
	private class Step implements Comparable<Step>
	{
		private Board myBoard;
		private Step predecessor;
		private int predictDistance;
		private int moves;
		public Step(Board currentBoard,Step prev,int moves)
		{
			myBoard = currentBoard;
			predecessor = prev;
			predictDistance = moves + currentBoard.manhattan();
			this.moves=moves;
		}
		public int compareTo(Step that) {
			if (this.predictDistance > that.predictDistance)
				return 1;
			else if(this.predictDistance == that.predictDistance)
				return 0;
			else 
				return -1;
		}
		public int GetMoves()
		{
			return this.moves;
		}
		public Board GetBoard()
		{
			return this.myBoard;
		}
	}
    public Solver(Board initial)
    {
    		if (initial == null)
    			throw new IllegalArgumentException();
    		InitPQ.insert(new Step(initial,null,0));
    		TwinPQ.insert(new Step(initial.twin(),null,0));
    		do
    		{
    			Step ini=InitPQ.delMin();
    			Step twi=TwinPQ.delMin();
    			if(ini.GetBoard().isGoal())
    			{
    				Solvable=true;
    				do
    				{
    				answer.push(ini.GetBoard());
    				ini=ini.predecessor;
    				}
    				while(ini!=null);
    			break;
    		}
    			
    			else if(twi.GetBoard().isGoal()){break;}
    			for(Board board:ini.GetBoard().neighbors())
    			{
    				if(ini.predecessor!=null&&board.equals(ini.predecessor.GetBoard())) 
    					continue;
    				InitPQ.insert(new Step(board,ini,ini.GetMoves()+1));
    			}
    			for(Board board:twi.GetBoard().neighbors())
    			{
    				if(twi.predecessor!=null&&board.equals(twi.predecessor.GetBoard())) 
    					continue;
    				TwinPQ.insert(new Step(board,twi,twi.GetMoves()+1));
    			}
    		}while(true);

    }// find a solution to the initial board (using the A* algorithm)
    public boolean isSolvable()
    {
    		return Solvable;
    }// is the initial board solvable?
    public int moves()
    {
    		if(Solvable) return answer.size()-1;
    		else return -1;
    }// min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution()
    {
    		if(Solvable) return answer;
    		else return null;
    }// sequence of boards in a shortest solution; null if unsolvable
    public static void main(String[] args) {

        // create initial board from file
        In in = new In("puzzle15.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        // solve the puzzle
        Solver solver = new Solver(initial);
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board+"\nmanhatan:"+board.manhattan());
        }
    }

}
