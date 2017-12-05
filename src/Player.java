public class Player 
{
	private int simulationPA;
	private double simulationRunsAdded;
	private int PA;
	private int singles;
	private int doubles;
	private int triples;
	private int HR;
	private int walks;
	private int base;
	private int simulationStatsRow;
	private String name;
	private Double impact=0.0;
	
	
	public double getImpact()
	{
		return impact;
	}
	public void setImpact(double impact)
	{
		this.impact=impact;
	}
	public int getWalks()
	{
		return walks;
	}
	
	public void setWalks(int walks)
	{
		this.walks=walks;
	}
	
	public String getName()
	{
		return name;	
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	public int getSimulationStatsRow()
	{
		return simulationStatsRow;
	}
	public void setSimulationStatsRow(int simulationStatsRow)
	{
		this.simulationStatsRow = simulationStatsRow;
	}
	public int getSimulationPA() 
	{
		return simulationPA;
	}
	public void setSimulationPA(int simulationPA) 
	{
		this.simulationPA = simulationPA;
	}
	public double getSimulationRunsAdded() 
	{
		return simulationRunsAdded;
	}
	public void setSimulationRunsAdded(double simulationRunsAdded) 
	{
		this.simulationRunsAdded = simulationRunsAdded;
	}
	public int getPA() 
	{
		return PA;
	}
	public void setPA(int pA) 
	{
		PA = pA;
	}
	public int getSingles() 
	{
		return singles;
	}
	public void setSingles(int singles) 
	{
		this.singles = singles;
	}
	public int getDoubles() 
	{
		return doubles;
	}
	public void setDoubles(int doubles) 
	{
		this.doubles = doubles;
	}
	public int getTriples() 
	{
		return triples;
	}
	public void setTriples(int triples) 
	{
		this.triples = triples;
	}
	public int getHR() 
	{
		return HR;
	}
	public void setHR(int hR) 
	{
		HR = hR;
	}
	public int getBase() 
	{
		return base;
	}
	public void setBase(int base) 
	{
		this.base = base;
	}
	
}
