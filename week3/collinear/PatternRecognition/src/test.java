import java.util.Arrays;

import edu.princeton.cs.algs4.*;
public class test {

	public static void main(String[] args) {
//		In in = new In("input9.txt");
//	    int n = in.readInt();
//	    Point[] points = new Point[n];
//	    for (int i = 0; i < n; i++) {
//	        int x = in.readInt();
//	        int y = in.readInt();
//	        points[i] = new Point(x, y);
//	    }
//		Arrays.sort(points);
//		Point[] B=new Point[n];
//		 for(int i=0;i<points.length;i++)
//			 B[i]=points[i];	
//		 Arrays.sort(B);
//		Arrays.sort(B,points[0].slopeOrder());
//		for(int i=0;i<B.length;++i)
//			StdOut.println(B[i].toString()+" "+B[0].compareTo(B[i])+" "+B[0].slopeTo(B[i]));
			for(int i=0;i<=32000;i+=4000)
				for(int j=0;j<=32000;j+=4000)
					StdOut.println(Integer.toString(i)+" "+Integer.toString(j));
	}

}
