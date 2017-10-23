import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	   private int n;
	   private Node first;
	   private Node last;
	   private class Node
	   {
		   private Item item;
		   private Node next;
		   private Node prev;
	   }
	   public Deque()
	   {
		  first = null;
		  last = null;
		   n = 0;
	   }// construct an empty deque
	   public boolean isEmpty() 
	   {
		  return n == 0;
	   }// is the deque empty?
	   public int size() 
	   {
		   return n;
	   } // return the number of items on the deque
	   public void addFirst(Item item)
	   {
		   if (item == null)
			   throw new IllegalArgumentException();
		   Node oldfirst = first;
		   first = new Node();
		   first.item = item;
		   first.next = oldfirst;
		   if (isEmpty())
			   last = first;
		   else
			   oldfirst.prev = first;
		   n++;
	   }// add the item to the front
	   public void addLast(Item item)
	   {
		   if (item == null)
			   throw new IllegalArgumentException();
		   Node oldlast = last;
		   last = new Node();
		   last.item = item;
		   last.prev = oldlast;
		   if (isEmpty())
			   first = last;
		   else
			   oldlast.next = last;
		   n++;
	   }// add the item to the end
	   public Item removeFirst()
	   {
		   if (isEmpty())
			   throw new NoSuchElementException();
		   Item tmp = first.item;
		   first = first.next;
		   n--;
		   if (isEmpty())
		   {
			   first = null;
			   last = null;
		   }
		   else
			   first.prev = null;
		   return tmp;   
	   }// remove and return the item from the front
	   public Item removeLast() 
	   {
		   if (isEmpty())
			   throw new NoSuchElementException();
		   Item tmp = last.item;
		   last = last.prev;
		   n--;
		   if (isEmpty())	//avoid loitering
		   {
			   first = null;
			   last = null;
		   }
		   else
			   last.next = null;
		   return tmp;
	   }// remove and return the item from the end
	   public Iterator<Item> iterator()
	   {
		   return new DequeIterator();
	   }
	   private class DequeIterator implements Iterator<Item>
	   {
		   private Node current = first;

		@Override
		public boolean hasNext() {return current != null;}
		public void remove() { throw new UnsupportedOperationException();  }
		@Override
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}	   
	   }
	   // return an iterator over items in order from front to end
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
