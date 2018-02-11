import java.util.Arrays;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
public class CircularSuffixArray {
   private String myString;
   private indexClass[] index;
   private class indexClass implements Comparable<indexClass>
   {
	   private int index;
	   indexClass(int i)
	   {
		   index = i;
	   }
	@Override
	public int compareTo(indexClass that) 
	{
		int i = this.index;
		int j = that.index;	
		do
		{
			if(myString.charAt(i) != myString.charAt(j))
			{
				if(myString.charAt(i) < myString.charAt(j))
					return -1;
				else
					return 1;
			}
			i = (i + 1) % length();
			j = (j + 1) % length();
		}while(i != this.index);		
		return 0;
	}
   }
   public CircularSuffixArray(String s)
   {
	   if(s == null)
		   throw new java.lang.IllegalArgumentException();
	   myString = new String(s);
	   index = new indexClass[length()];
	   for(int i = 0; i < length(); i++)
	   {
		   index[i] = new indexClass(i);
	   }
	   Arrays.sort(index);
   }
   // circular suffix array of s
   public int length()
   {
	   return myString.length();
   }// length of s
   public int index(int i)
   {
	   if(i >= myString.length() || i < 0)
		   throw new java.lang.IllegalArgumentException();
	   return index[i].index;
   }// returns index of ith sorted suffix
   public static void main(String[] args)
   {
	   String test = BinaryStdIn.readString();
	   CircularSuffixArray array = new CircularSuffixArray(test);
	   while(!BinaryStdIn.isEmpty())
	   {
		   int index = BinaryStdIn.readInt();
		   BinaryStdOut.write(array.index(index));
	   }
   }// unit testing (required)
}