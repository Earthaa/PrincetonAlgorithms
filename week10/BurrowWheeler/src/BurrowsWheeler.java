import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
public class BurrowsWheeler {
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform()
    {
    		String s = BinaryStdIn.readString();
    		CircularSuffixArray suffixArray = new CircularSuffixArray(s);
    		int first = 0;
    		for(int i = 0; i < suffixArray.length(); i++)
    		{
    			if(suffixArray.index(i) == 0)
    				first = i;
    		}
    		BinaryStdOut.write(first);
    		for(int i = 0; i < suffixArray.length(); i++)
    			BinaryStdOut.write(s.charAt((suffixArray.length()+suffixArray.index(i) - 1)%suffixArray.length()));
    		BinaryStdOut.close();
    } 
    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform()
    {
    		int R = 256;
    		int first = BinaryStdIn.readInt();
    		String code = BinaryStdIn.readString();
    		int[] next = new int[code.length()];
    		char[] startChar = new char[code.length()];
    		int[] from = new int[R];//from the index to make next array which can also be used to make startChar array
    		int[] count = new int[R];
    		for(int i = 0; i < code.length(); i++)
    		{
    			count[code.charAt(i)]++;
    		}
    		int accumulate = 0;
    		for(char i = 0; i < R; i++)
    		{
    			if(count[i] != 0)
    			{
    				from[i] = accumulate;
    				for(int j = 0; j < count[i]; j++)
    				{
    					startChar[from[i] + j] = i;
    				}
    				accumulate += count[i];   				
    			}
    		}
    		for(int i = 0; i < code.length(); i++)
    		{
    			char codeChar = code.charAt(i);
    			next[from[codeChar]] = i;
    			from[codeChar] ++;
    		}
    		for(int i = 0; i < code.length(); i++)
    		{
    			BinaryStdOut.write(startChar[first],8);    			
    			first = next[first];
    		}
    		BinaryStdOut.close(); 		
    }
    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args)
    {	 
    	 	 if      (args[0].equals("-")) transform();
         else if (args[0].equals("+")) inverseTransform();
         else throw new IllegalArgumentException("Illegal command line argument");
    }
}