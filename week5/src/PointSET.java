import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.TreeSet;
public class PointSET {
	   private TreeSet<Point2D> ts;// ts means TreeSet
	   public  PointSET()  
	   {
		   ts=new TreeSet<Point2D>();
	   }// construct an empty set of points 
	   public boolean isEmpty()
	   {
		   return ts.isEmpty();
	   }// is the set empty? 
	   public int size()
	   {
		   return ts.size();
	   }// number of points in the set 
	   public void insert(Point2D p)
	   {
		   if(p == null)
			   throw new java.lang.IllegalArgumentException();
		   ts.add(p);
	   }// add the point to the set (if it is not already in the set)
	   public  boolean contains(Point2D p)
	   {
		   if(p == null)
			   throw new java.lang.IllegalArgumentException();
		   return ts.contains(p);
	   }// does the set contain point p? 
	   public void draw()
	   {
		   for(Point2D point:ts)
		   {
			   point.draw();
		   }
	   }// draw all points to standard draw 
	   public Iterable<Point2D> range(RectHV rect)
	   {
		   if(rect == null)
			   throw new java.lang.IllegalArgumentException();
		   Queue<Point2D> rangeQueue = new Queue<Point2D>();
		   for(Point2D point:ts)
		   {
			   if(rect.contains(point))
				   rangeQueue.enqueue(point);
		   }
		   return rangeQueue;
	   }// all points that are inside the rectangle (or on the boundary) 
	   public Point2D nearest(Point2D p)
	   {
		   if(p == null)
			   throw new java.lang.IllegalArgumentException();
		   if(isEmpty())
			   return null;
		   Point2D answerPoint = new Point2D(0.0,0.0);
		   double answerDistance = 5;
		   for(Point2D point:ts)
		   {
			   if(point.distanceSquaredTo(p)<answerDistance)
			   {
				   answerDistance=point.distanceSquaredTo(p);
				   answerPoint = point;
			   }
		   }
		   return answerPoint;
	   }// a nearest neighbor in the set to point p; null if the set is empty 
	   public static void main(String[] args)
	   {
		   
	   }// unit testing of the methods (optional) 
}
