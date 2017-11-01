import java.util.Arrays;
import java.util.LinkedList;

import edu.princeton.cs.algs4.*;
import java.util.List;

public class FastCollinearPoints {
	private LineSegment[] resultsegements;
	private int n=0;
	
	 public FastCollinearPoints(Point[] points)
	 {
		 n=0;
		 if(points == null)
			 throw new IllegalArgumentException();
		 for(int i=0;i<points.length;i++)
			 if(points[i] == null)
				 throw new IllegalArgumentException();
		 Point[] Mypoints=points.clone();
		 Arrays.sort(Mypoints);
		   if(Mypoints.length>1)
		   {
			   for(int i=0;i<Mypoints.length-1;i++)
				   if(Mypoints[i].compareTo(Mypoints[i+1])==0)
					   throw new IllegalArgumentException();
		   }
		 final List<LineSegment> tmp=new LinkedList<LineSegment>();
		 Point[] tmppoints = new Point[Mypoints.length];
		 for(int i=0;i<Mypoints.length;i++)
			 tmppoints[i]=Mypoints[i];		 	 
		 for(int i=0;i<Mypoints.length;i++)
		 {
			 Arrays.sort(tmppoints);
			 Arrays.sort(tmppoints,Mypoints[i].slopeOrder());
			
			 int cnt=1;
			 double slope=Double.NEGATIVE_INFINITY;
			 double falseslope = Double.NEGATIVE_INFINITY;
			 for(int j=1;j<tmppoints.length;j++)
			 {			
				if(tmppoints[0].slopeTo(tmppoints[j])==slope)
				 {
					 cnt++;		 
				 }
				 else if(tmppoints[0].slopeTo(tmppoints[j])!=slope)
				 {				 
					 if(cnt>=4)
					 {		
						 tmp.add(new LineSegment(tmppoints[0],tmppoints[j-1]));
						 ++n;					 
					 }
					 cnt=1;
					 if(tmppoints[0].compareTo(tmppoints[j])<0&&tmppoints[0].slopeTo(tmppoints[j])!=falseslope)
					 {
						 slope=tmppoints[0].slopeTo(tmppoints[j]);
						 cnt++;
					 }
					 else if(tmppoints[0].slopeTo(tmppoints[j])!=falseslope&&tmppoints[0].compareTo(tmppoints[j])>0)
						 falseslope=tmppoints[0].slopeTo(tmppoints[j]);
				 }
				if(j==tmppoints.length-1&&cnt>=4)
				{
					 tmp.add(new LineSegment(tmppoints[0],tmppoints[j]));
					 ++n;
				}
					
			 }					 
		 }
		 resultsegements=tmp.toArray(new LineSegment[0]);
	 }// finds all line segments containing 4 or more points
	 public int numberOfSegments()
	 {
		 return n;
	 }// the number of line segments
	 public LineSegment[] segments()
	 {
		 //StdOut.print(n);
		 return resultsegements.clone();
	 }// the line segments
	 public static void main(String[] args) {

		    // read the n points from a file
		    In in = new In("grid6x6.txt");
		    int n = in.readInt();
		    Point[] points = new Point[n];
		    for (int i = 0; i < n; i++) {
		        int x = in.readInt();
		        int y = in.readInt();
		        points[i] = new Point(x, y);
		    }

		    // draw the points
		    StdDraw.enableDoubleBuffering();
		    StdDraw.setXscale(0, 32768);
		    StdDraw.setYscale(0, 32768);
		    for (Point p : points) {
		        p.draw();
		    }
		    StdDraw.show();

		    // print and draw the line segments
		    FastCollinearPoints collinear = new FastCollinearPoints(points);
		    for (LineSegment segment : collinear.segments()) {
		        StdOut.println(segment);
		        segment.draw();
		    }
		    StdDraw.show();
		}

}
