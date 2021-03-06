import edu.princeton.cs.algs4.*;
public class Board {
	private final int dim;
	private final int[][] myblocks;
	private int hamming;
	private int manhattan;
	private int x0;
	private int y0;
	private Queue<Board> neighbours;
	private int LocationToNum(int x,int y)
	{
		if(x == dim-1 && y == dim-1)
			return 0;
		return x*dim+y+1;
	}
	private int[][] copy()
	{
		int[][] copy = new int[dim][dim];
		for(int i=0;i<dim;i++)
			copy[i] = myblocks[i].clone();
		return copy;
	}
	private int[][] NumToLocation(int num)
	{
		int[][] location = new int[1][2];
		if (num == 0)
		{
			location[0][0] = dim - 1;
			location[0][1] = dim - 1;
			return location;
		}
		location[0][0] = (num -1) / dim;
		location[0][1] = num % dim -1;
		if(location[0][1] == -1)
			location[0][1] = dim - 1;
		return location;
	}
    public Board(int[][] blocks)
    {
    		if(blocks == null)
    			throw new IllegalArgumentException();    		
    		dim = blocks.length;
    		myblocks = new int[dim][dim];
    		hamming = 0;
    		manhattan = 0;
    		for(int i=0;i<dim;i++)
    			for(int j=0; j<dim;j++)
    			{
    				myblocks[i][j]=blocks[i][j];
    				if(myblocks[i][j] == 0)
    				{
    					x0 = i;
    					y0 = j;
    				}
    				if(LocationToNum(i,j) != myblocks[i][j] && myblocks[i][j] != 0)
    				{
    					hamming++;
    					int[][] truelocation = NumToLocation(myblocks[i][j]);
    					manhattan += (Math.abs(i-truelocation[0][0])+Math.abs(j-truelocation[0][1]));
    				}
    			}	
    }
    		
    // construct a board from an n-by-n array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    public int dimension()
    {
    		return dim;
    }// board dimension n
    public int hamming()
    {
    		return hamming;
    }// number of blocks out of place
    public int manhattan()
    {
    		return manhattan;
    }// sum of Manhattan distances between blocks and goal
    public boolean isGoal()
    {
    		return manhattan == 0;
    }// is this board the goal board?
    public Board twin()// a board that is obtained by exchanging any pair of blocks
    {
    		int[][] tmp = copy();
    		if(x0 != 0)
    			swap(tmp,0,0,0,1);
    		else
    			swap(tmp,1,0,1,1);
    		return new Board(tmp);
    }
    private void swap(int[][] v, int rowA, int colA, int rowB, int colB) {
        int swap = v[rowA][colA];
        v[rowA][colA] = v[rowB][colB];
        v[rowB][colB] = swap;
    }
    public boolean equals(Object y)
    {
    		if (y == this) return true;
    		if(y == null) return false;
    		if(y.getClass() != this.getClass()) return false;
    		Board that = (Board) y;
    		if(that.dim != this.dim)
    			return false;
    		for(int i=0;i<dim;i++)
    			for(int j=0;j<dim;j++)
    				if(this.myblocks[i][j]!=that.myblocks[i][j])
    					return false;
    		return true;
    }// does this board equal y?
    public Iterable<Board> neighbors()
    {
    		if(neighbours != null)
    			return neighbours;
    		neighbours = new Queue<Board>();
    		if(x0 - 1 >= 0)
    		{
    			int[][] up = copy();
    			swap(up,x0,y0,x0-1,y0);
    			neighbours.enqueue(new Board(up));
    		}
    		if(y0 - 1 >= 0)
    		{
    			int[][] left = copy();
    			swap(left,x0,y0,x0,y0-1);
    			neighbours.enqueue(new Board(left));
    		}
    		if(x0 + 1 < dim)
    		{
    			int[][] down = copy();
    			swap(down,x0,y0,x0+1,y0);
    			neighbours.enqueue(new Board(down));
    		}
    		if(y0 + 1 < dim)
    		{
    			int[][] right = copy();
    			swap(right,x0,y0,x0,y0+1);
    			neighbours.enqueue(new Board(right));
    		}
    		return neighbours; 			
    }// all neighboring boards
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dim + "\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                s.append(String.format("%2d ", myblocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args)
    {
    		In in = new In("puzzle3x3-03.txt");
    		  int n = in.readInt();
    		    int[][] blocks = new int[n][n];
    		    for (int i = 0; i < n; i++)
    		        for (int j = 0; j < n; j++)
    		            blocks[i][j] = in.readInt();
    		    Board initial = new Board(blocks);
    		    StdOut.print(initial.toString());
    		    //StdOut.println(initial.manhattan);
    		    for (Board board : initial.neighbors())
                    StdOut.println(board);
    }// unit tests (not graded)
}
