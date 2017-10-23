import java.util.Iterator;
import edu.princeton.cs.algs4.*;
public class Permutation {
	public static void main(String[] args) {		
		int n = Integer.parseInt(args[0]);
		RandomizedQueue<String> test= new RandomizedQueue<String>();
		String str;
		
		while(!StdIn.isEmpty())
		{
			str = StdIn.readString();
			test.enqueue(str);
		}
		Iterator<String> item = test.iterator();
		for (int i=1; i<=n; i++)
		{
			StdOut.println(item.next());
		}
	}

}
