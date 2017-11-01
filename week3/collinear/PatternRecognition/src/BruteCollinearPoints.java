import edu.princeton.cs.algs4.*;
import java.util.*;
public class BruteCollinearPoints 
{
	private LineSegment[] resultsegements;
	private int n=0;

   public BruteCollinearPoints(Point[] points)
   {
	   if(points==null)
		   throw new java.lang.IllegalArgumentException();
	   for(int i=0;i<points.length;i++)
			 if(points[i]==null)
				 throw new IllegalArgumentException();
	   final List<LineSegment> tmp=new LinkedList<LineSegment>();
	   Point[] Mypoints=points.clone();
	   Arrays.sort(Mypoints);
	   if(points.length>1)
	   {
		   for(int i=0;i<points.length-1;i++)
			   if(Mypoints[i].compareTo(Mypoints[i+1])==0)
				   throw new IllegalArgumentException();
	   }
	   for(int p1=0;p1<Mypoints.length-3;p1++)
		   for(int p2=p1+1;p2<Mypoints.length-2;p2++)
			   for(int p3=p2+1;p3<Mypoints.length-1;p3++)
				   for(int p4=p3+1;p4<Mypoints.length;p4++)
				   {
					   if(Mypoints[p1].slopeTo(Mypoints[p2])==Mypoints[p1].slopeTo(Mypoints[p3])&&Mypoints[p1].slopeTo(Mypoints[p4])==Mypoints[p1].slopeTo(Mypoints[p2]))
					   {
						   //StdOut.println("p1:"+p1+"p2:"+p2+"p3:"+p3+"p4:"+p4);
						   tmp.add(new LineSegment(Mypoints[p1],Mypoints[p4]));
						   n++;
					   }
				   }
	  resultsegements=new LineSegment[n];
	  for(int i=0;i<n;i++)
		  resultsegements=tmp.toArray(new LineSegment[0]);  
   }// finds all line segments containing 4 points
   public  int numberOfSegments()
   {
	   return n;
   }// the number of line segments
   public LineSegment[] segments() 
   {
	  return resultsegements.clone();
   }// the line segments

}

