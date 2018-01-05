import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
   private WordNet myWordNet = null;
   public Outcast(WordNet wordnet)
   {
	   myWordNet = wordnet;
   }// constructor takes a WordNet object
   public String outcast(String[] nouns) 
   {
	   int[] distance = new int[nouns.length];
	   for(int i=0;i<nouns.length;i++)
	   {
		   for(int j = 0;j<nouns.length;j++)
			   distance[i]+=myWordNet.distance(nouns[i], nouns[j]);		   
	   }
	   int max = 0;
	   int flag = 0;
	   for(int i=0;i<nouns.length;i++)
	   {
		   if(distance[i] > max)
		   {
			   max = distance[i];
			   flag = i;
		   }
	   }
	   return nouns[flag];
   }// given an array of WordNet nouns, return an outcast
   public static void main(String[] args)
   {
	   WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
	   Outcast outcast = new Outcast(wordnet);
	   In in = new In("outcast7.txt");
	   String[] nouns = in.readAllStrings();
	   StdOut.println(outcast.outcast(nouns));
	    
   }// see test client below
}