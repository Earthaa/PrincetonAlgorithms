import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.BinaryStdIn;
//import edu.princeton.cs.algs4.StdIn;
//import edu.princeton.cs.algs4.StdOut;
public class MoveToFront {
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode()
    {
    		int R = 256;
    		String myString = BinaryStdIn.readString();
    		//String myString = StdIn.readString();
    		StringBuilder strAlphabet = new StringBuilder();
    		for(char i = 0; i < R; i++)
    		{
    			strAlphabet.append(i);
    		}
    		for(int i = 0; i < myString.length(); i++)
    		{
    			for(char j = 0; j < R; j++)
    			{		
    				if(strAlphabet.charAt(j) == myString.charAt(i))
    				{
    					BinaryStdOut.write(j,8);
    					char tmp = strAlphabet.charAt(j);
    					strAlphabet.deleteCharAt(j);
    					strAlphabet.insert(0, tmp);
    					break;
    				}
    			}
    		}
    		BinaryStdOut.close();
    }
    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode()
    {
    		int R = 256;
		String myString = BinaryStdIn.readString();
		StringBuilder strAlphabet = new StringBuilder();
		for(char i = 0; i < R; i++)
		{
			strAlphabet.append(i);
		}
		for(int i = 0; i < myString.length(); i++)
		{	
			char j = myString.charAt(i);
			BinaryStdOut.write(strAlphabet.charAt(j),8);
			char tmp = strAlphabet.charAt(j);
			strAlphabet.deleteCharAt(j);
			strAlphabet.insert(0, tmp);
		}
		BinaryStdOut.close();
    }
    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args)
    {
    	 	 if      (args[0].equals("-")) encode();
         else if (args[0].equals("+")) decode();
         else throw new IllegalArgumentException("Illegal command line argument");
    }
}