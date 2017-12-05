import java.util.Arrays;
import java.util.List;
import java.util.Random;
public class Game
{
	private Player[] simulationLineup = new Player[9];
	private int lineupPosition=0;
	private Player batterUp;
	private int outs=0;
	private Random r = new Random();
	private int inning=1;
	private int simulationRuns=0;
	private boolean playByPlay;
	private static List<String[]> REM;
	private Player[] basepath = new Player[4];
	

	public void setREM(List<String[]> REM)
	{
		this.REM=REM;
	}
	
	public List<String[]> getREM()
	{
		return REM;
	}
	public void setPlayByPlay(boolean playByPlay)
	{
		this.playByPlay=playByPlay;
	}
	public boolean getPlayByPlay()
	{
		return playByPlay;
	}
	public Player getSimulationLineup(int i) 
	{
		return simulationLineup[i];
	}

	public void setSimulationLineup(int i, Player batter) 
	{
		this.simulationLineup[i]=batter;
	}

	public int getSimulationRuns() 
	{
		return simulationRuns;
	}

	public void runGame()
	{
		for (int i=0; i<basepath.length; i++)
		{
			basepath[i]=null;
		}
		while (inning<=9)
		{
			while (outs<3)
			{
				nextAB(lineupPosition);
				lineupPosition=(lineupPosition+1)%9;//because it needs to go back to the top of the order 
			}
			inning++;
			outs=0;
			Arrays.fill(basepath, null);
		}
	}
	
	public void nextAB(int lineupPosition)
	{
		double currentRunExpectancy=checkRunExpectancy();
		double currentRuns=simulationRuns;
		batterUp = simulationLineup[lineupPosition];//unnecessary variable?
		int totalPA;
		if (batterUp.getPA()>0)
			totalPA = batterUp.getPA();//unnecessary variable?
		else
			totalPA = 1;
		int outcome = r.nextInt(totalPA);//0 to (totalPA-1) inclusive
		
		if (outcome<batterUp.getSingles())//0 to (singles-1) inclusive 
		{
			hitSingle();
		}
		
		else if (outcome<batterUp.getSingles()+batterUp.getDoubles())//singles-(singles+doubles-1) inclusive
		{
			hitDouble();
		}
		
		else if (outcome<batterUp.getSingles()+batterUp.getDoubles()+batterUp.getTriples())
			//(singles+doubles) to (singles+doubles+triples-1) inclusive
		{
			hitTriple();
		}
		
		else if (outcome<batterUp.getSingles()+batterUp.getDoubles()+batterUp.getTriples()+batterUp.getHR())
		{
			hitHR();
		}
		
		else if (outcome<batterUp.getSingles()+batterUp.getDoubles()+batterUp.getTriples()+batterUp.getHR()+batterUp.getWalks())
		{
			walk();
		}
		
		else
		{
			getOut();
		}
		if (playByPlay)
		{
			System.out.print("Runners on bases: ");
			for (int i=0; i<=3; i++)
			{
				if (basepath[i]!=null)
					System.out.print(i);
			}
			System.out.println();
			System.out.println("There are now "+outs+" outs.");
			if (outs==3)
			{
				System.out.println("Inning "+inning+" is over.");
				System.out.println("There are "+simulationRuns+" runs.");
			}
			System.out.println();
		}
		double newRunExpectancy=checkRunExpectancy();
		double newRuns=simulationRuns;
		double impactOfAB=newRunExpectancy+newRuns-currentRunExpectancy-currentRuns;
		batterUp.setImpact(batterUp.getImpact()+impactOfAB);
	}
	
	public void hitSingle()
	{
		if (playByPlay)
			System.out.println(batterUp.getName()+" hit a single.");
		
		if (basepath[3]!=null)
		{
			simulationRuns++;
			basepath[3]=null;
		}
	
		if (basepath[2]!=null)
		{
			if (r.nextInt(100)+1<=58)
			{
				simulationRuns++;
			}
			else
				basepath[3]=basepath[2];
			basepath[2]=null;
		}
		
		if (basepath[1]!=null)
		{
			if (basepath[3]==null&&r.nextInt(100)+1<=28)
			{
				basepath[3]=basepath[1];
			}
			else
				basepath[2]=basepath[1];
			basepath[1]=null;
		}
		basepath[1]=batterUp;
	}
	
	public void hitDouble()
	{
		if (playByPlay)
			System.out.println(batterUp.getName()+" hit a double.");
		if (basepath[3]!=null)
		{
			simulationRuns++;
			basepath[3]=null;
		}
		
		if (basepath[2]!=null)
		{
			simulationRuns++;
			basepath[2]=null;
		}
		
		if (basepath[1]!=null)
		{
			if (r.nextInt(100)+1<=62)
				simulationRuns++;
			else
				basepath[3]=basepath[1];
			basepath[1]=null;
		}
		basepath[2]=batterUp;
	}
	
	public void hitTriple()
	{
		if (playByPlay)
			System.out.println(batterUp.getName()+" hit a triple.");
		
		if (basepath[3]!=null)
		{
			simulationRuns++;
			basepath[3]=null;
		}
		
		if (basepath[2]!=null)
		{
			simulationRuns++;
			basepath[2]=null;
		}
		
		if (basepath[1]!=null)
		{
			simulationRuns++;
			basepath[1]=null;
		}
		
		basepath[3]=batterUp;
	}
	
	public void hitHR()
	{
		if (playByPlay)
			System.out.println(batterUp.getName()+" hit a home run.");
		
		if (basepath[3]!=null)
		{
			simulationRuns++;
			basepath[3]=null;
		}
		
		if (basepath[2]!=null)
		{
			simulationRuns++;
			basepath[2]=null;
		}
		
		if (basepath[1]!=null)
		{
			simulationRuns++;
			basepath[1]=null;
		}
		
		simulationRuns++; //batter won't be on base
	}
	
	public void walk()
	{
		if (playByPlay)
			System.out.println(batterUp.getName()+" walked.");
		
		if (basepath[1]==null)
		{
			basepath[1]=batterUp;
		}
		
		else if (basepath[2]==null)
		{
			basepath[2]=basepath[1];
			basepath[1]=batterUp;
		}
		
		else if (basepath[3]==null)
		{
			basepath[3]=basepath[2];
			basepath[2]=basepath[1];
			basepath[1]=batterUp;
		}
		
		else
		{
			simulationRuns++;
			basepath[3]=basepath[2];
			basepath[2]=basepath[1];
			basepath[1]=batterUp;
		}
	}
	
	public void getOut()
	{
		if (playByPlay)
			System.out.println(batterUp.getName()+" got out.");
		
		outs++;
	}
	
	public double checkRunExpectancy()
	{
		if (basepath[1]==null&&basepath[2]==null&&basepath[3]==null)
			return (Double.parseDouble(REM.get(1)[outs+1]));
		
		else if (basepath[1]!=null&&basepath[2]==null&&basepath[3]==null)
			return (Double.parseDouble(REM.get(2)[outs+1]));
		
		else if (basepath[1]==null&&basepath[2]!=null&&basepath[3]==null)
			return (Double.parseDouble(REM.get(3)[outs+1]));
		
		else if (basepath[1]!=null&&basepath[2]!=null&&basepath[3]==null)
			return (Double.parseDouble(REM.get(4)[outs+1]));
		
		else if (basepath[1]==null&&basepath[2]==null&&basepath[3]!=null)
			return (Double.parseDouble(REM.get(5)[outs+1]));
		
		else if (basepath[1]!=null&&basepath[2]==null&&basepath[3]!=null)
			return (Double.parseDouble(REM.get(6)[outs+1]));
	
		else if (basepath[1]!=null&&basepath[2]!=null&&basepath[3]==null)
			return (Double.parseDouble(REM.get(7)[outs+1]));
		
		else //(basepath[1]!=null&&basepath[2]!=null&&basepath[3]!=null)
			return (Double.parseDouble(REM.get(8)[outs+1]));
	}
}
