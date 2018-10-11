

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class export1 {
	public static int export1(int character,int wordcount,int lines,String Opathname)
	{
		File file = new File(Opathname);
		try {
			BufferedWriter bi = new BufferedWriter(new FileWriter(file));
			bi.append("characters: "+character+"\r\n");
			bi.append("words: "+wordcount+"\r\n");
			bi.append("lines: "+lines+"\r\n");
			bi.close();
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		return 0;
	}
}
