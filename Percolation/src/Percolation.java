import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {

	private final WeightedQuickUnionUF myUF;// used to test if percolated
	private final WeightedQuickUnionUF myUF2;// used to test if full
	private final int n; 
	private boolean[] isopen; 
	private int numberofOpensites = 0;
	private int coordinationTransfer(int row,int col)
	{
		return (this.n + 1) * row + col;
	}
	public Percolation(int n)
	{
		if(n <= 0)
			throw new IllegalArgumentException();
		myUF = new WeightedQuickUnionUF((n + 1) * (n + 1)); 
		myUF2 = new WeightedQuickUnionUF((n + 1) * (n + 1)); 
		this.n = n;
		isopen=new boolean[(n + 1) * (n + 1)]; 
	}
	public boolean isOpen(int row,int col)
	{
		if (row <= 0||col <= 0||row > this.n||col > this.n)
			throw new IllegalArgumentException();
		return isopen[coordinationTransfer(row, col)]; 
	}
	public int numberOfOpenSites()
	{
		return numberofOpensites; 
	}
	public boolean isFull(int row,int col)
	{
		if (row <= 0||col <= 0||row > this.n||col > this.n)
			throw new IllegalArgumentException(); 
		if (isOpen(row, col))
			return myUF2.connected(coordinationTransfer(row, col),0); 
		else 
			return false; 
	}

	public void open(int row, int col)
	{
		if (row <= 0||col <= 0||row > this.n||col > this.n)
			throw new IllegalArgumentException(); 
		if (isOpen(row, col))
			return;
		else
		{
			isopen[coordinationTransfer(row, col)]=true;
			numberofOpensites++;
			if (row-1 > 0&&isOpen(row - 1, col))// up
			{
				myUF.union(coordinationTransfer(row - 1, col), coordinationTransfer(row, col));
				myUF2.union(coordinationTransfer(row - 1, col), coordinationTransfer(row, col));
			}
			if (row+1<=this.n&&isOpen(row+1, col))// down
				{
				myUF.union(coordinationTransfer(row + 1, col), coordinationTransfer(row, col));
				myUF2.union(coordinationTransfer(row + 1, col), coordinationTransfer(row, col));
				}
			if (col - 1 > 0&&isOpen(row, col - 1))// left
				{
				myUF.union(coordinationTransfer(row, col - 1), coordinationTransfer(row, col));
				myUF2.union(coordinationTransfer(row, col - 1), coordinationTransfer(row, col));
				}
			if (col + 1 <= this.n&&isOpen(row, col + 1))// right
			{
				myUF.union(coordinationTransfer(row, col + 1), coordinationTransfer(row , col));
				myUF2.union(coordinationTransfer(row, col + 1), coordinationTransfer(row , col));
			}
			if (row == 1)
			{
				myUF.union(coordinationTransfer(1, col), 0); 
				myUF2.union(coordinationTransfer(1, col), 0); 
			}
			if(row == n)
				 myUF.union(coordinationTransfer(n, col), 1); 
				
		}
	}
	public boolean percolates()
	{
		return myUF.connected(0, 1);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}



}
