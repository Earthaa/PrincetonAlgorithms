import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
public class BoggleSolver
{
	private final PrefixTries  tries = new PrefixTries();
	private boolean visited[];
	private BoggleBoard myBoard;
	private Set<String> allWords = new HashSet<String>();
	private StringBuffer sb;
	private  static class Node
	{			
		private Node[] next = new Node[26];
	}
	private class PrefixTries
	{
		private Node root;
		private PrefixTries()
		{
			root = new Node();
		}
		private void add(String key)
		{
			root = put(root,key,0);
		}
		private Node put(Node x,String key,int d)
		{
			Node result = root;
			while(d != key.length())
			{
				if(x.next[key.charAt(d)-65] == null)
					x.next[key.charAt(d)-65] = new Node();
				x = x.next[key.charAt(d)-65];
				d++;
			}						
			return result;
		}
//		private boolean contains(String key)
//		{
//			if(get(root,key,0) == null)
//				return false;
//			return true;
//		}
		private Node get(Node x,String key,int d)
		{
			while(x != null && d!=key.length()) 
			{
				 char c = key.charAt(d);
			     x = x.next[c-65];
			     d++;
			}
		return x;
	       
		}
	}
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary)
    {
    		if(dictionary == null)
    			throw new java.lang.IllegalArgumentException();
    		for(int i = 0; i < dictionary.length; i++)
    		{
    			tries.add(dictionary[i]);
    			allWords.add(dictionary[i]);
    		}
    }
    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    private int getNumber(int row, int col)
    {
    		return row * myBoard.cols() + col;
    }
    private boolean isValid(int row, int col)
    {
    		if(row < 0 || row >= myBoard.rows() || col < 0 || col >= myBoard.cols())
    			return false;
    		else
    			return true;
    }
    private void createAdj(int row, int col,int[][] adj)
    {
    		int num = getNumber(row,col);
    		int d = 0;
    		if(isValid(row + 1,col))
    			adj[num][d] = getNumber(row+1,col);
    		else
    			adj[num][d] = -1;
    		d++;
    		if(isValid(row-1,col))
    			adj[num][d] = getNumber(row-1,col);
    		else
    			adj[num][d] = -1;
    		d++;
    		if(isValid(row,col+1))
    			adj[num][d] = getNumber(row,col+1);
    		else
    			adj[num][d] = -1;
    		d++;
    		if(isValid(row,col-1))
    			adj[num][d] = getNumber(row,col-1);
    		else
    			adj[num][d] = -1;
    		d++;
    		if(isValid(row+1,col+1))
    			adj[num][d] = getNumber(row+1,col+1);
    		else
    			adj[num][d] = -1;
    		d++;
    		if(isValid(row+1,col-1))
    			adj[num][d] = getNumber(row+1,col-1);
    		else
    			adj[num][d] = -1;
    		d++;
    		if(isValid(row-1,col-1))
    			adj[num][d] = getNumber(row-1,col-1);
    		else
    			adj[num][d] = -1;
    		d++;
    		if(isValid(row-1,col+1))
    			adj[num][d] = getNumber(row-1,col+1);
    		else
    			adj[num][d] = -1;
    		d++;
    }
   private void dfsSearch(int row,int col,int[][] adj,Set<String> validWords,Node SearchNode)
    {
    		int num = getNumber(row,col);
    		visited[num] = true;
    		sb.append(myBoard.getLetter(row, col));
    		Node nextNode;
    		if(myBoard.getLetter(row, col) == 'Q')
    		{	
    				sb.append('U');
    				nextNode = tries.get(SearchNode, sb.toString(), sb.length()-2);
    		}		
    		else
    			nextNode = tries.get(SearchNode, sb.toString(), sb.length()-1);	
    		if(nextNode == null)
    		{
    			visited[num] = false;
    			sb.deleteCharAt(sb.length() - 1);
    			if(myBoard.getLetter(row, col) == 'Q')
    				sb.deleteCharAt(sb.length() - 1);
    			return;
    		}
    		if(sb.length() >= 3 && allWords.contains(sb.toString()))
    			validWords.add(sb.toString());
    		for(int nextNum:adj[num])
    		{
    			if(nextNum == -1 || visited[nextNum])
    				continue;
    			int nextRow = nextNum / myBoard.cols();
    			int nextCol = nextNum % myBoard.cols();
    			dfsSearch(nextRow,nextCol,adj,validWords,nextNode);
    		}
    		visited[num] = false;
    		sb.deleteCharAt(sb.length()-1);
    		if(myBoard.getLetter(row, col) == 'Q')
			sb.deleteCharAt(sb.length() - 1);
    }
	public Iterable<String> getAllValidWords(BoggleBoard board)
    {
    		if(board == null)
    			throw new java.lang.IllegalArgumentException(); 	
    		myBoard = board;
    		Set<String> validWords  = new TreeSet<String>();
    		visited = new boolean[myBoard.cols() * myBoard.rows()];
    		sb = new StringBuffer();
    		int[][] adj =  new int[myBoard.cols() * myBoard.rows()][8];
    		for(int row = 0; row < board.rows(); row++)
    			for(int col = 0 ; col < board.cols(); col++)
    			{ 				
    				createAdj(row,col,adj);   				  				
    			}
    		for(int row = 0 ; row < board.rows(); row++)
    			for(int col = 0; col < board.cols(); col++)
    			{
    				dfsSearch(row,col,adj,validWords,tries.root);
    			}
    		return validWords;
    }
    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word)
    {
    		if(word == null)
    			throw new java.lang.IllegalArgumentException();
    		if(word.length() <= 2 || !allWords.contains(word))
    			return 0;
    		else if(word.length()<=4)
    			return 1;
    		else if(word.length()<=5)
    			return 2;
    		else if(word.length()<=6)
    			return 3;
    		else if(word.length()<=7)
    			return 5;
    		else
    			return 11;
    }
    public static void main(String[] args) {
        In in = new In("dictionary-algs4.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard("board-q.txt");
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
