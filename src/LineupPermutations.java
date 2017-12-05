public class LineupPermutations 
{

	public static void main(String[] args) 
	{
		for (int i=190000000; i<200000000; i++)
		{
			if (differentDigits(i))
				System.out.println(i);
		}

	}
	
	public static boolean differentDigits(int i)
	{
		int[] digits = new int[9];
		for (int j=0; j<9; j++)
		{
			digits[j]=i%10;
			i/=10;
		}
		for (int j=0; j<9; j++)
		{
			for (int k=j+1; k<9; k++)
				if (digits[j]==digits[k]||digits[j]==0)
					return false;
		}
		return true;
	}

}
