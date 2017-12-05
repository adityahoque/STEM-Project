import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVParserGeneral {
	public static void main(String[] args){
		parse("test.csv");
		
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
		for (String[] a:table)
		{
			for (String b:a)
			{
				System.out.print(b+" ");
			}
			System.out.println();
		}
		System.out.println(table.get(1)[1]);
		return table;
		
	}
}
