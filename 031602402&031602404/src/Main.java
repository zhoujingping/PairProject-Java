import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		String inputFileName="";
		String outputFileName="";
		int w=0;
		int m=-1;
		int n=10;
		for(int i=0;i<args.length;i++) 
		{
			if(args[i].equals("-i"))
			{
				i++;
				inputFileName=args[i];
			}
			else if(args[i].equals("-o")) 
			{
				i++;
				outputFileName=args[i];
			}
			else if(args[i].equals("-w"))
			{
				i++;
				//避免出现不为数字的情况
				try{
					w=Integer.parseInt(args[i]);
				}
				catch(NumberFormatException e) {
					System.out.println("w input wrong");
					e.printStackTrace();
				}
			}
			else if(args[i].equals("-m"))
			{
				i++;
				try{
					m=Integer.parseInt(args[i]);
				}
				catch(NumberFormatException e) {
					System.out.println("m input wrong");
					e.printStackTrace();
				}
				
			}
			else if(args[i].equals("-n"))
			{
				i++;
				try{
					n=Integer.parseInt(args[i]);
				}
				catch(NumberFormatException e) {
					System.out.println("n input wrong");
					e.printStackTrace();
				}
			}
		}
		try {
			FileReader fr=new FileReader(new File(inputFileName));
			File output=new File(outputFileName);
			output.createNewFile();
			FileWriter fw=new FileWriter(output);
			FileParser fp=new FileParser();
			fp.Parser(fr,w,m);
			fp.out(fw, n);
			fr.close();
			fw.close();
			System.out.println("finish");
		} catch (FileNotFoundException e) {
			System.out.println("File don't exist");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("output wrong");
			e.printStackTrace();
		}
		
		
	}

}
