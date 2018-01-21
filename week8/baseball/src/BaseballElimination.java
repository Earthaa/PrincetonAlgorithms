import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.In;
import java.util.HashMap;
import java.util.HashSet;
public class BaseballElimination {
	private int n;
	private HashMap<String,Integer> teamToNumber;
	private HashMap<Integer,String> numberToTeam;
	private  int[] w;
	private  int[] l;
	private int[] r;
	private int[][] g;	
 	public BaseballElimination(String filename)
	{
 		if(filename == null)
 			throw new java.lang.IllegalArgumentException();
		In in = new In(filename);
		n = Integer.parseInt(in.readLine());
		teamToNumber = new HashMap<String,Integer>();
		numberToTeam = new HashMap<Integer,String>();
		w = new int[n];
		l = new int[n];
		r = new int[n];
		g = new int[n][n];
		String[] lines = new String[n];
		lines = in.readAllLines();
		for(int i = 0; i < n; i++)
		{		
			lines[i] = lines[i].trim();
			String[] fields = lines[i].split("\\s+");
			
			teamToNumber.put(fields[0], i);
			numberToTeam.put(i, fields[0]);
			w[i] = Integer.parseInt(fields[1]);
			l[i] = Integer.parseInt(fields[2]);
			r[i] = Integer.parseInt(fields[3]);
			for(int j = 0; j < n; j++)
			{
				if(i == j)
					g[i][j] = 0;
				else
					g[i][j] = Integer.parseInt(fields[4 + j]);
			}
		}		
	}// create a baseball division from given filename in format specified below
	public   int numberOfTeams()
	{
		return n;
	}// number of teams
	public Iterable<String> teams()
	{
		return teamToNumber.keySet();
	}// all teams
	public   int wins(String team)
	{
		if(team == null || !teamToNumber.containsKey(team))
			throw new java.lang.IllegalArgumentException();
		int number = teamToNumber.get(team);
		return w[number];
	}// number of wins for given team
	public int losses(String team)
	{
		if(team == null || !teamToNumber.containsKey(team))
			throw new java.lang.IllegalArgumentException();
		int number = teamToNumber.get(team);
		return l[number];
	}// number of losses for given team
	public int remaining(String team)
	{
		if(team == null || !teamToNumber.containsKey(team))
			throw new java.lang.IllegalArgumentException();
		int number = teamToNumber.get(team);
		return r[number];
	}// number of remaining games for given team
	public  int against(String team1, String team2)
	{
		if(team1 == null || team2 == null || !teamToNumber.containsKey(team1) || !teamToNumber.containsKey(team2))
			throw new java.lang.IllegalArgumentException();
		int number1 = teamToNumber.get(team1);
		int number2 = teamToNumber.get(team2);
		return g[number1][number2];
	}// number of remaining games between team1 and team2
	private int getTeamNumInFlowNet(int Team,int targetTeam)
	{
		if(Team < targetTeam)
			return (n-1)*(n-2)/2 + 1 + Team;
		else
			return (n-1)*(n-2)/2 + Team ;
	}
	private int getTeamNumFromFlowNet(int Team, int targetTeam)
	{
		int answer = Team - (n-1)*(n-2)/2 - 1;
		if(answer < targetTeam)
			return answer;
		else
			return answer + 1;
	}
	private int getTeamNumInAgainstTable(int Team1,int Team2,int targetTeam)
	{
		int offset1 = 0,offset2 = 0;
		if(Team1 > targetTeam)
			offset1 = 1;
		if(Team2 > targetTeam)
			offset2 = 1;
		return (Team1 - offset1)*(n - 2) - (Team1 - offset1) * (Team1 - 1 -offset1)/2 + (Team2 - offset2 - Team1 + offset1) ;
	
	}
	private FlowNetwork getNetwork (int number)
	{
		FlowNetwork flowNetwork = new FlowNetwork(2 + n - 1 + (n-1)*(n-2)/2);
		int source = 0;
		int sink = n + (n-1)*(n-2)/2;
		for(int i = 0; i < n; i++)
		{
			if(i == number)
				continue;
			int flowNetNumber = getTeamNumInFlowNet(i,number);
			FlowEdge toSink = new FlowEdge(flowNetNumber, sink, w[number]+r[number]-w[i]);
			flowNetwork.addEdge(toSink);		
			for(int j = i+1; j < n; j++)
			{
				if(j == number)
					continue;
				int againstTableNumber = getTeamNumInAgainstTable(i, j, number);
				FlowEdge fromSource = new FlowEdge(source, againstTableNumber,g[i][j]);
				FlowEdge toTeam1 = new FlowEdge(againstTableNumber,flowNetNumber,Double.POSITIVE_INFINITY);
				FlowEdge toTeam2 = new FlowEdge(againstTableNumber,getTeamNumInFlowNet(j,number),Double.POSITIVE_INFINITY);
				flowNetwork.addEdge(fromSource);
				flowNetwork.addEdge(toTeam1);
				flowNetwork.addEdge(toTeam2);
			}
		}
		return flowNetwork;
	}
	public  boolean isEliminated(String team)
	{
		if(team == null || !teamToNumber.containsKey(team))
			throw new java.lang.IllegalArgumentException();
		if(n == 1)
			return false;
		int number = teamToNumber.get(team);
		if(n == 2)
		{
			for(int i = 0;i < n; i++)
				if(w[i] > w[number] + r[number])
					return true;
			return false;
		}
		for(int i = 0;i < n; i++)
			if(w[i] > w[number] + r[number])
				return true;
		int source = 0;
		int sink = n + (n-1)*(n-2)/2;
		FlowNetwork flowNetwork = getNetwork(number);
		FordFulkerson maxFlow = new FordFulkerson(flowNetwork,source,sink);
		int capacitySum = 0;
		for(FlowEdge edge:flowNetwork.adj(0))
		{
			capacitySum += edge.capacity();
		}
		if(capacitySum <= maxFlow.value())
			return false;
		else 
			return true;
	}// is given team eliminated?
	public Iterable<String> certificateOfElimination(String team)  // subset R of teams that eliminates given team; null if not eliminated
	{		
		if(!isEliminated(team))
			return null;
		int number = teamToNumber.get(team);
		HashSet<String> answerSet = new HashSet<String>();
		if(n >= 2)
		{
			for(int i = 0;i < n; i++)
				if(w[i] > w[number] + r[number])				
					answerSet.add(numberToTeam.get(i));			
			if(!answerSet.isEmpty())
				return answerSet;
		}
		int source = 0;
		int sink = n + (n-1)*(n-2)/2;
		FlowNetwork flowNetwork = getNetwork(number);
		FordFulkerson maxFlow = new FordFulkerson(flowNetwork,source,sink);
		for(int i = (n-1)*(n-2)/2+1; i < sink; i++)
		{
			if(maxFlow.inCut(i))
			{
				int num = getTeamNumFromFlowNet(i,number);
				answerSet.add(numberToTeam.get(num));
			}
		}
		return answerSet;
	}
	public static void main(String[] args) {
	    BaseballElimination division = new BaseballElimination("teams10.txt");
	    for (String team : division.teams()) {
	        if (division.isEliminated(team)) {
	            StdOut.print(team + " is eliminated by the subset R = { ");
	            for (String t : division.certificateOfElimination(team)) {
	                StdOut.print(t + " ");
	            }
	            StdOut.println("}");
	        }
	        else {
	            StdOut.println(team + " is not eliminated");
	        }
	    }
	}
}
