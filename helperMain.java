import java.io.*;
import java.util.ArrayList;

public class helperMain
{
	public static void main(String [] args)
	{
		System.out.println("Welcome to the survey helper tool!");
		read("to.csv","from.csv");
	}
	public static void read(String to, String from)
	{
		ArrayList<String> idNumbers = new ArrayList<String>();
		try
		{
			BufferedReader br1 = new BufferedReader(new FileReader(to));
			String line;
			while((line = br1.readLine())!=null) //this will be to collect the ID's that are pre-existing.
			{
				//System.out.println(line1);
				String[] column = line.split(",");
				idNumbers.add(column[0]);
			}
			br1.close();
			BufferedReader br2 = new BufferedReader(new FileReader(from));

			while((line = br2.readLine())!=null) //traverse the from file
			{
				String[] column = line.split(",");
				int round = -1; //round is part of the survey data being read.
				if(column[1].equalsIgnoreCase("1"))
					round = 1;
				else if(column[1].equalsIgnoreCase("2"))
					round = 2;
				else if(column[1].equalsIgnoreCase("3"))
					round = 3;

				if(IdFound(column[0], idNumbers))
					addDataToExisting(column[0],round,column[2]);
				else
				{
					idNumbers.add(column[0]); //adds the id not found to the list
					//add to the end of the file.
					FileWriter fw = new FileWriter(to,true); //true for append to the end
					if(round == 1)
					{
						fw.write(column[0] + ",,,,," + column[2] + ",,,,,\n");
					}
					else if( round == 2)
					{
						fw.write(column[0] + ",,,,,,,,," + column[2] +",\n");
					}
					else if ( round == 3 )
					{
						fw.write(column[0] + ",,,,,,,,,," + column[2] + "\n");
					}
					fw.close();
				}
			}
			br2.close();
		}
		catch(IOException e) //Exception data
		{
			System.out.println("Houston we had a problem");
			System.out.println(e.getMessage());
		}
		catch(Exception e)
		{
			System.out.println("Houston we had a problem");
			System.out.println(e.getMessage());
		}
	}
	public static void addDataToExisting(String ID, int round, String res)
	{
		try
		{
			File inputFile = new File("to.csv");
			File tempFile = new File("tempfile.txt");
			BufferedReader br1 = new BufferedReader(new FileReader("to.csv"));
			BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
			String line;
			int currLine = 1;
			while((line = br1.readLine())!=null)
			{
				//System.out.println("Working here man: " + line);
				String[] column = line.split(",");
				if(column[0].equalsIgnoreCase(ID)) //found the line I need to modify and mod.
				{
					if(currLine <= 2703) //for letting User know if there are additions to the top of the file
					{ //TODO CHANGE THE LINE EVERY TIME YOU RUN IT
						System.out.println("Added something to line: "+currLine + " ID: " + ID + " Round: "+ round);
					}
					else
					{
						System.out.println("Added something to line: "+ currLine + " but it's not in the top");
					}
					//System.out.println(line + " length: "+ column.length);
					//***** ONLY IMPORTANT COLUMNS ARE 0, 5, 9, 10
					if(round == 1)
					{
						//System.out.println("made it inside the round 1 func\n");

						if(column.length >= 11)
							bw.write(column[0] + "," + 	"," + "," + "," + "," + res + column[6] + "," + column[7] + "," + column[8] + ","+ column[9] +"," + column[10]+"\n");
						else if(column.length >= 10)
							bw.write(column[0] + "," + 	"," + "," + "," + "," + res + column[6] + "," + column[7] + "," + column[8] + ","+ column[9] +","+"\n");
						else
							bw.write(column[0] + "," + 	"," + "," + "," + "," + res + "," + "," +","+ ","+"\n");


						//System.out.println("about to head out of the round 1 func\n");
					}
					else if( round == 2)
					{
						//System.out.println("made it inside the round 2 func\n"); +"\n"

						if(column.length >= 11)
							bw.write(column[0] + "," + column[1] + 	"," + column[2] +	"," + column[3] + "," + column[4] + "," + column[5] + "," + column[6] + "," + column[7] + "," + column[8] + ","+ res +"," + column[10] +"\n");
						else if(column.length >= 9)
							bw.write(column[0] + "," + column[1] + 	"," + column[2] +	"," + column[3] + "," + column[4] + "," + column[5] + "," + column[6] + "," + column[7] + "," + column[8] + ","+ res +","+"\n");
						else if(column.length >= 6)
							bw.write(column[0] + "," + column[1] + 	"," + column[2] +	"," + column[3] + "," + column[4] + "," + column[5] + "," + "," + "," + ","+ res +","+"\n");
						else
							bw.write(column[0] + "," + "," + "," + "," + "," + ","+ "," + "," + "," + ","+ res +",");

						//System.out.println("about to head out of the round 2 func\n");
					}
					else if ( round == 3 )
					{
						//System.out.println("made it inside the round 3 func\n"); //TODO IMPLEMENT THE SYSTEM I DID HERE FOR THE REST
						//BE CAREFUL, CAN'T COPY PASTE.
						if(column.length >= 10)
							bw.write(column[0] + "," + "," + "," + "," + "," + column[5] + "," + "," + "," + "," + column[9] +"," + res+"\n");
						else if(column.length >= 6)
							bw.write(column[0] + "," + "," + "," + "," + "," + column[5] + "," + "," + "," + "," +"," + res+"\n");
						else
							bw.write(column[0] + "," + "," + "," + "," + "," + "," + "," + "," + "," +"," + res+"\n");
						//System.out.println("about to head out of the round 3 func\n");
					}
					//System.out.println("made it out of the round func\n");


				} //end of the outer if statement.
				else
				{
					bw.write(line+"\n");
				}
				currLine++;
			} //end of the while.
			br1.close();
			bw.close();
			tempFile.renameTo(inputFile);
		}
		catch(IOException e) //Exception data
		{
			System.out.println("Houston we had a IO Exception problem");
			System.out.println(e.getMessage());
		}
		catch(Exception e)
		{
			System.out.println("Houston we had a Exception e problem");
			System.out.println(e.getMessage());
		}
	}
	public static boolean IdFound(String ID, ArrayList<String> idNums)
	{
		for( int i = 0; i < idNums.size(); i++)
		{
			if(idNums.get(i).equalsIgnoreCase(ID))
				return true;
		}
		return false;
	}
}
