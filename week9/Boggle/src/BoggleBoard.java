import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
public class BoggleBoard
{
	private char[][] board;
	private int width;
	private int height;
    // Initializes a random 4-by-4 Boggle board.
    // (by rolling the Hasbro dice)
    public BoggleBoard()
    {
    		this.board = new char[4][4];
    		width = 4;
    		height = 4;
    }
    // Initializes a random m-by-n Boggle board.
    // (using the frequency of letters in the English language)
    public BoggleBoard(int m, int n)
    {
    		this.board = new char[m][n];
    		width = n;
    		height = m;
    }
    // Initializes a Boggle board from the specified filename.
    public BoggleBoard(String filename)
    {
    		In in = new In(filename);
    		height = in.readInt();
    		width = in.readInt();
    		in.readLine();
    		this.board = new char[height][width];
    		for(int row = 0; row < height; row++)
    		{    			
    			String[] tmp = in.readLine().split("\\s+");
    			for(int col = 0; col < width; col++)
    			{   		
    				board[row][col] = tmp[col].charAt(0);   				
    			}
    		}
    }
    // Initializes a Boggle board from the 2d char array.
    // (with 'Q' representing the two-letter sequence "Qu")
    public BoggleBoard(char[][] a)
    {
    		this.board = a;
    		height = a.length;
    		width = a[0].length;
    }
    // Returns the number of rows.
    public int rows()
    {
    		return this.height;
    }
    // Returns the number of columns.
    public int cols()
    {
    		return this.width;
    }
    // Returns the letter in row i and column j.
    // (with 'Q' representing the two-letter sequence "Qu")
    public char getLetter(int i, int j)
    {
    		if(i >= height || j >= width || i < 0 || j < 0)
    			throw new java.lang.IllegalArgumentException();
    		return board[i][j];
    		
    }
    // Returns a string representation of the board.
    public String toString()
    {
    		StringBuilder result = new StringBuilder();
    		for(int row = 0; row < height; row ++)
    		{
    			for(int col = 0; col < width; col ++)
    			{
    				if(board[row][col] == 'Q')
    					result.append("QU");
    				else  					
    					result.append(board[row][col]);
    				result.append(" ");
    			}
    			result.append('\n');
    		}
    		return result.toString();
    }	
}
