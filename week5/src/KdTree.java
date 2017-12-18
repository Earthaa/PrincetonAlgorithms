import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
public class KdTree {
	   private static class Node
	   {
		   private Point2D p;
		   private RectHV rect;
		   private int layer;
		   private Node lb;
		   private Node rt;
		   private Node(Point2D myPoint,RectHV myrect)
		   {
			   p = myPoint;
			   rect = myrect;
		   }
		   private Node(Point2D myPoint)
		   {
			   p = myPoint;
		   }
		   private int compareTo(Node that)
		   {
			   if(p.x()==that.p.x()&&p.y()==that.p.y())
				   return 0;
			   if(layer % 2 != 0)
			   {
				   if(p.x()<=that.p.x())
					   return -1;
				   else
					   return 1;
			   }
			   else
			   {
				   if(p.y()<=that.p.y())
					   return -1;
				   else 
					   return 1;
			   }
		   }
	   }
	   private Node root = null;
	   private int size;
	   private Point2D nearestPoint = null;
	   private double distanceSquared = Double.MIN_VALUE;
	   public KdTree()
	   {
		   size = 0;
		   root = null;
	   }// construct an empty set of points 
	   public boolean isEmpty()
	   {
		   return size == 0;
			   
	   }// is the set empty? 
	   public int size()
	   {
		   return size;
	   }// number of points in the set 
	   private Node find(Node newPoint,Node x)
	   {
		 
		   int cmp = x.compareTo(newPoint);
		   if(cmp == 1)
		   {
			   if(x.lb!=null)
				   return find(newPoint, x.lb);
			   else
				   return x;				   
		   }
		   else if(cmp == -1)
		   {
			   if(x.rt!=null)
				   return find(newPoint,x.rt);
			   else
				   return x;
		   }
		   return x;
	   }//get the parent of where to insert
	   public void insert(Point2D p) 
	   {
		   if(p == null)
			   throw new java.lang.IllegalArgumentException();
		   if(isEmpty())
		   {
			   RectHV tmprect = new RectHV(0.0,0.0,1.0,1.0);
			   root = new Node(p,tmprect);
			   root.layer = 1;
			   this.size = 1;
			   return;
		   }
		   Node newPoint = new Node(p);
		   Node parentPoint = find(newPoint,root);
		   int cmp = parentPoint.compareTo(newPoint);
		   if(cmp == 0)
			   return;
		   else if(cmp == 1)
		   {
			   size ++;
			   parentPoint.lb = newPoint;
			   newPoint.layer = parentPoint.layer+1;
			   if(parentPoint.layer % 2 !=0)
				   newPoint.rect = new RectHV(parentPoint.rect.xmin(),parentPoint.rect.ymin(),
						   parentPoint.p.x(),parentPoint.rect.ymax());
			   else
				   newPoint.rect = new RectHV(parentPoint.rect.xmin(),parentPoint.rect.ymin(),
						   parentPoint.rect.xmax(),parentPoint.p.y());
			   
		   }
		   else
		   {
			   size ++;
			   parentPoint.rt = newPoint;
			   newPoint.layer = parentPoint.layer+1;
			   if(parentPoint.layer % 2 !=0)
				   newPoint.rect = new RectHV(parentPoint.p.x(),parentPoint.rect.ymin(),
						   parentPoint.rect.xmax(),parentPoint.rect.ymax());
			   else
				   newPoint.rect = new RectHV(parentPoint.rect.xmin(),parentPoint.p.y(),
						   parentPoint.rect.xmax(),parentPoint.rect.ymax());
		   }
		  
	   }// add the point to the set (if it is not already in the set)
	   
	   public boolean contains(Point2D p)
	   {
		   if(p == null)
			   throw new java.lang.IllegalArgumentException();
		   if(isEmpty())
			   return false;
		   Node tmp = new Node(p);
		   Node answer = find(tmp,root);
		   if(answer.compareTo(tmp) == 0)
			   return true;
		   else 
			   return false;
	   }// does the set contain point p? 
	   private void drawNode(Node root)
	   {
		   if(root == null)
			   return;
		   StdDraw.setPenRadius(0.02);
           StdDraw.setPenColor(StdDraw.BLACK);
           StdDraw.point(root.p.x(), root.p.y());
           if(root.layer % 2 != 0)
           {
        	   StdDraw.setPenRadius(0.005);
               StdDraw.setPenColor(StdDraw.RED);
               StdDraw.line(root.p.x(), root.rect.ymin(),root.p.x(),root.rect.ymax());
           }
           else
           {
        	   StdDraw.setPenRadius(0.005);
               StdDraw.setPenColor(StdDraw.BLUE);
               StdDraw.line(root.rect.xmin(), root.p.y(),root.rect.xmax(),root.p.y());
           }
           drawNode(root.lb);
           drawNode(root.rt);
	   }
	   public void draw()
	   {
		   drawNode(root);		   
	   }// draw all points to standard draw */
	   private void searchRange(Queue<Point2D> q, Node root, RectHV rect)
	   {
		   if(root == null || !root.rect.intersects(rect))
			   return;
		   if(rect.contains(root.p))
			   q.enqueue(root.p);
		   searchRange(q,root.lb,rect);
		   searchRange(q,root.rt,rect);
	   }
	   public Iterable<Point2D> range(RectHV rect)
	   {
		   if(rect == null)
			   throw new java.lang.IllegalArgumentException();
		   Queue<Point2D> q = new Queue<Point2D>();
		   searchRange(q,root,rect);
		   return q;
	   }// all points that are inside the rectangle (or on the boundary) 
	   private void searchNearest(Node start,Point2D p)
	   {
		   if(start == null||start.rect.distanceSquaredTo(p)>distanceSquared)
			   return;
		   if(start.p.distanceSquaredTo(p)<distanceSquared)
		   {
			   distanceSquared = start.p.distanceSquaredTo(p);
			   nearestPoint = start.p;
		   }
		   if((start.layer%2 != 0 && p.x() < start.p.x())||(start.layer%2 == 0 && p.y() < start.p.y()))
		   {
			   
			   searchNearest(start.lb,p);
			   searchNearest(start.rt,p);	
		   }
		   else if((start.layer%2 != 0 && p.x() >= start.p.x())||(start.layer%2 == 0 && p.y() >= start.p.y()))
		   {
			   searchNearest(start.rt,p);	
			   searchNearest(start.lb,p);			   
		   }
	   }
	   public Point2D nearest(Point2D p)
	   {
		   if(p == null)
			   throw new java.lang.IllegalArgumentException();
		   distanceSquared = 5;
		   nearestPoint = null;
		   searchNearest(root,p);
		   return nearestPoint;
	   }// a nearest neighbor in the set to point p; null if the set is empty 

	   public static void main(String[] args)
	   {
		   // initialize the two data structures with point from file
	        String filename = "test.txt";
	        In in = new In(filename);
	        PointSET brute = new PointSET();
	        KdTree kdtree = new KdTree();
	        while (!in.isEmpty()) {
	            double x = in.readDouble();
	            double y = in.readDouble();
	            Point2D p = new Point2D(x, y);
	            kdtree.insert(p);
	            brute.insert(p);
	        }
	        StdDraw.clear();
	        kdtree.draw();
	        StdDraw.show();
	   }
}

