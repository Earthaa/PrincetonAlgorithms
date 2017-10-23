
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.*;
public class RandomizedQueue<Item> implements Iterable<Item> {
	   private Item[] q;
	   private int n;
	   private int first;
	   private int last;
	   public RandomizedQueue() 
	   {
		   q = (Item[]) new Object[2];
		   n = 0;
		   first = 0;
		   last = 0;
	   }// construct an empty randomized queue
	   private void resize (int capacity)
	   {
		   assert capacity >= n;
		   Item[] temp = (Item[]) new Object[capacity];
		   for (int i=0; i < n; i++)
		   {
			   temp[i]=q[i];
			   
		   }
		   q = temp;
		   first = 0;
		   last = n;
	   }
	   public boolean isEmpty() {return n == 0;}                // is the randomized queue empty?
	   public int size()   {return n;}                     // return the number of items on the randomized queue
	   public void enqueue(Item item) 
	   {
		   if (item == null)
			   throw new IllegalArgumentException();
		   if (n >= q.length)
			   resize(2 * q.length);
		   
		   q[last++] = item;
		   n++;
	   }// add the item
	   public Item dequeue() //random choose a item and then put the last to this location then delete last
	   {
		   if (n == 0)
			   throw new NoSuchElementException();
		   int random = StdRandom.uniform(first,last);
		   Item tmp = q[random];
		   q[random] = q[last-1];
		   q[--last] = null;
		   n--;
		   if (n <= q.length/4 && q.length>2)
			   resize( q.length/2);
		   return tmp; 
	   }// remove and return a random item
	   public Item sample() 
	   {
		   if (n == 0)
			   throw new NoSuchElementException();
		   int sampleid = StdRandom.uniform(first, last);
		   return q[sampleid];
	   }
	@Override
		public Iterator<Item> iterator() {
			return new RandomizedQueueIterator();
			
		}
		private class RandomizedQueueIterator implements Iterator<Item>
		{
			private int orderid;
			private int[] order = new int[n];
			public RandomizedQueueIterator()
			{
				for (int i=0; i<n; i++)
					order[i]=i;
				StdRandom.shuffle(order);
				orderid = 0;
			}
			@Override
			public boolean hasNext() {
				
				return orderid != n;
			}
			@Override
			public Item next() {
				if (!hasNext())
					throw new NoSuchElementException();
				return q[order[orderid++]];
			}
			public void remove() { throw new UnsupportedOperationException();  }
		}
		public static void main(String[] args) {
		}

}
