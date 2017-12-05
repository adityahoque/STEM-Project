import java.io.File;
import java.util.*;


public class SimulationRunner 
{

	private static Player Player1 = new Player();
	private static Player Player2 = new Player();
	private static Player Player3 = new Player();
	private static Player Player4 = new Player();
	private static Player Player5 = new Player();
	private static Player Player6 = new Player();
	private static Player Player7 = new Player();
	private static Player Player8 = new Player();
	private static Player Player9 = new Player();
	
	private static Player[] lineup = {Player1, Player2, Player3, 
			Player4, Player5, Player6, 
			Player7, Player8, Player9};
	private static String[] copyLineup={"","","","","","","","",""};
	private static Game simulation;
	private static List<String[]> statsTable;
	private static List<String[]> lineupsTable;
	private static List<String[]> REM;
	private static List<String[]> permutations;
	private static int simTotalRuns;
	private static final int numGames=1000;
	private static String team;
	private static int teamLineupRow;
	private static String teamName;
	private static int currentHighestRuns;
	private static Player[] currentBestLineup ={new Player(),new Player(),new Player(),
			new Player(),new Player(),new Player(),new Player(),new Player(),new Player()};
	private static Scanner console = new Scanner(System.in);
	private static boolean playByPlay=false;
	private static int[] digits = new int[9];
	private static boolean optimizeLineup=false;
	
	public static Player[] getLineup() 
	{
		return lineup;
	}

	public static void setLineup(Player[] lineup) 
	{
		SimulationRunner.lineup = lineup;
	}

	public static void main(String[] args) 
	{
		//for (teamLineupRow=1; teamLineupRow<=30; teamLineupRow++)
		{
			//teamLineupRow=4;
			parseTables();
			setLineupPlayers();
			setPlayerStats();
			playGames();
			outputResults();
			if (optimizeLineup)
				optimizeLineup();
		}
		
		
	}
	public static ArrayList<String[]> parse(String filename)
	{
		ArrayList<String[]> table=new ArrayList<String[]>();
		File file=new File(filename);
		Scanner scan=new Scanner(System.in);
		try
		{
			scan=new Scanner(file);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		String[] vals;
		String val;
		while(scan.hasNext())
		{
			val=scan.nextLine();
			vals=val.split(",");
			table.add(vals);
		//test
		}
		return table;
		
	}
	
	public static void parseTables()
	{
		statsTable=parse("ExpandedStats2016.csv");
		//lineupsTable=parse("Lineups2017.csv");
		REM=parse("RunExpectancyMatrix.csv");
		permutations=parse("LineupPermutations.csv");
	}
	
	public static void setLineupPlayers()
	{
		//for (int i=0; i<9; i++)
		{
			//lineup[i].setName(lineupsTable.get(teamLineupRow)[i+1]);
		}
		
		lineup[0].setName("Dustin Pedroia");
		lineup[1].setName("Xander Bogaerts");
		lineup[2].setName("Mookie Betts");
		lineup[3].setName("Hanley Ramirez");
		lineup[4].setName("Pablo Sandoval");
		lineup[5].setName("Mitch Moreland");
		lineup[6].setName("Andrew Benintendi");
		lineup[7].setName("Jackie Bradley");
		lineup[8].setName("Sandy Leon");
		
	}
	
	public static void setPlayerStats()
	{
		for (int i=0; i<9; i++)
		{
			int k=0;
			while (!statsTable.get(k)[1].equals(lineup[i].getName())&&!statsTable.get(k)[1].equals("Rookie"))
			{
				k++;
			}
			
			if (statsTable.get(k)[1].equals(statsTable.get(k+1)[1]))
			{
				System.out.println(lineup[i].getName()+": Enter 0 if you mean "+statsTable.get(k)[3]+". Enter 1 if you mean "+statsTable.get(k+1)[3]);
				k+=console.nextInt();
			}
			
			if (statsTable.get(k)[1].equals("Rookie"))
			{
				System.out.println("Enter 0 if "+lineup[i].getName()+" is a rookie batter. Enter 1 if he is a pitcher who did not bat during the previous season.");
				k+=console.nextInt();
			}
			
			//System.out.println(lineup[i].getName()+" "+k+" "+statsTable.get(k)[1]);
			lineup[i].setSimulationStatsRow(k);
			if (statsTable.get(k)[10]==null)
			{
				lineup[i].setSingles(0);
			}
			else
				lineup[i].setSingles(Integer.parseInt(statsTable.get(k)[10]));
			
			if (statsTable.get(k)[11]==null)
			{
				lineup[i].setDoubles(0);
			}
			else
				lineup[i].setDoubles(Integer.parseInt(statsTable.get(k)[11]));
			
			if (statsTable.get(k)[12]==null)
			{
				lineup[i].setTriples(0);
			}
			else
				lineup[i].setTriples(Integer.parseInt(statsTable.get(k)[12]));
			
			if (statsTable.get(k)[13]==null)
			{
				lineup[i].setHR(0);
			}
			else
				lineup[i].setHR(Integer.parseInt(statsTable.get(k)[13]));
			
			if (statsTable.get(k)[17]==null)
			{
				lineup[i].setWalks(0);
			}
			else
				lineup[i].setWalks(Integer.parseInt(statsTable.get(k)[17]));
			
			if (statsTable.get(k)[6]==null)
			{
				lineup[i].setPA(0);
			}
			else
				lineup[i].setPA(Integer.parseInt(statsTable.get(k)[6]));
			
			lineup[i].setImpact(0.0);
		}

	}
	
	public static void playGames()
	{
		simTotalRuns=0;
		for (int i=0; i<numGames; i++)
		{
			simulation=new Game();
			for (int j=0; j<9; j++)
			{
				simulation.setSimulationLineup(j,lineup[j]);
			}
			simulation.setPlayByPlay(playByPlay);
			simulation.setREM(REM);
			simulation.runGame();
			simTotalRuns+=simulation.getSimulationRuns();
		}
	}
	
	public static void outputResults()
	{
		System.out.print("This lineup scored "+simTotalRuns+", "+numGames+", ");
		System.out.printf("%.2f", ((double) simTotalRuns/numGames));
		System.out.println();
		for (int i=0; i<9; i++)
		{
			System.out.print(lineup[i].getName()+", ");
			System.out.printf("%.2f", (lineup[i].getImpact()/numGames));
			System.out.println();
		}
		System.out.println();
	}
	
	public static void optimizeLineup()
	{
		currentHighestRuns=simTotalRuns;
		for (int i=0; i<9; i++)
		{
			copyLineup[i]=lineup[i].getName();
		}
		int[] digits = new int[9];
		for (int i=100000000;i<999999999;i++)
		{
			if (differentDigits(i))
			{
				int copy=i;
				System.out.println(copy);
				for (int j=0; j<9; j++)
				{
					digits[j]=copy%10;
					copy/=10;
				}
				for (int m=0; m<9; m++)
				{
					lineup[m].setName(copyLineup[digits[m]-1]);

				}
				setPlayerStats();
				playGames();
				if (simTotalRuns>currentHighestRuns)
				{
					currentHighestRuns=simTotalRuns;
					for (int m=0; m<9; m++)
					{
						currentBestLineup[m].setName(lineup[m].getName());
						currentBestLineup[m].setImpact(lineup[m].getImpact());
					}
						
				}
			}
		}
		System.out.println("OPTIMAL LINEUP:");
		System.out.print(currentHighestRuns+", "+numGames+", ");
		System.out.printf("%.2f", ((double) currentHighestRuns/numGames));
		System.out.println();
		for (int i=0; i<9; i++)
		{
			System.out.print(currentBestLineup[i].getName()+", ");
			System.out.printf("%.2f", (currentBestLineup[i].getImpact()/numGames));
			System.out.println();
		}
	}
	public static boolean differentDigits(int i)
	{
		for (int j=0; j<9; j++)
		{
			digits[j]=i%10;
			i/=10;
		}
		
		for (int j=0; j<9; j++)
		{
			for (int k=j+1; k<9; k++)
			{
				if (digits[j]==digits[k]||digits[j]==0)
				{
					return false;
				}
				
			}
			
		}
		return true;
	}
}
