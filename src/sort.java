
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class sort {
	public static int sort1(Map<String,Integer> words,int Comflag,String Opathname)
	{
		/****************排序*******************/
		File file = new File(Opathname);
		BufferedWriter bw;
		try
		{
		bw = new BufferedWriter(new FileWriter(file,true));
		int []a=new int[Comflag];
		String []b=new String[Comflag];
		for(int i=0;i<Comflag;i++)
		{
			a[i]=-1;
			b[i]= null;
			Iterator<Map.Entry<String,Integer>> entries = words.entrySet().iterator();
			while(entries.hasNext())
			{
				Map.Entry<String, Integer> entry = entries.next();
				if(a[i]==entry.getValue())
				{
					if(entry.getKey().compareTo(b[i])<0)
					{
						b[i]=entry.getKey();
					}
				}
				else if(a[i]<entry.getValue())
				{
					a[i]=entry.getValue();
					b[i]=entry.getKey();
				}
			}
			if(b[i]!=null)
			words.put(b[i], 0);
		}
		/**************输出的文件********************/
		
		
			
			for(int i=0;i<Comflag;i++)
		{
			if(a[i]!=0&&a[i]!=-1)
			bw.write("<"+b[i]+">: "+a[i]+"\r\n");
		}
		bw.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return 0;
	}
}
