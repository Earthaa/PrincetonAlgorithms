import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class PercolationStats {

	
	private final double[] threshold; 
	private final int T;
	private double cons=1.96;
	private double mean=-1.0;
	private double stddev=-1.0;
	public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
	 {
		
		T=trials;
		if(n <= 0 || T <= 0)
			   throw new IllegalArgumentException();
		   
		   Percolation perc;
		   threshold=new double[trials];
		   
		   for (int i = 0; i < trials; i++)
		   {
			   perc=new Percolation(n);
			   while (!perc.percolates())
			   {
				   int row=StdRandom.uniform(1 , n + 1);
			   	   int col=StdRandom.uniform(1 , n + 1);
			   	   perc.open(row, col);
			   }
			   threshold[i]=(double)perc.numberOfOpenSites() / (double)(n * n);
			   
		   }
	 }
	public double mean()
	{
		mean=StdStats.mean(threshold);
		return mean;
	}// sample mean of percolation threshold
	public double stddev() 
	{
		stddev=StdStats.stddev(threshold);
		return stddev;	
	}// sample standard deviation of percolation threshold
	public double confidenceLo()
	{
		if(mean == -1.0)
			mean();
		if(stddev == -1.0)
			stddev();
		return mean - cons * stddev / Math.sqrt(T);
	}// low  endpoint of 95% confidence interval
	public double confidenceHi()
	{
		if(mean == -1.0)
			mean();
		if(stddev == -1.0)
			stddev();
		return mean + cons * stddev / Math.sqrt(T);
	}// high endpoint of 95% confidence interval

	public static void main(String[] args) 
	{
		int n = 0,trial = 0;
		In in=new In(args[0]);
		while(!in.isEmpty())
		{
			n=in.readInt();
			trial=in.readInt();
			PercolationStats ps=new PercolationStats(n,trial);
			StdOut.println("mean                    ="+ps.mean());
			StdOut.println("stddev                  ="+ps.stddev());
			StdOut.println("95% confidence interval ="+"["+ps.confidenceLo()+","+ps.confidenceHi()+"]");
		}


	}

}
