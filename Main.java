import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class pachong {
	public static void main(String []args)throws IOException
	{
		List<String> list = new ArrayList<String>();
		File file = new File("C:\\Users\\Administrator\\Desktop\\result.txt");
		OutputStream out = null;
		Writer writer = new FileWriter(file, true);
		if(!file.exists())
		{
			try {
				file.createNewFile();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		try {
			out = new FileOutputStream(file);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		String url="http://openaccess.thecvf.com/CVPR2018.py";
		Document doc = Jsoup.connect(url).get();
		Elements links=doc.getElementsByClass("ptitle").select("a");
		for(Element link:links)
		{
			String url2 ="http://openaccess.thecvf.com/"+link.attr("href");
			list.add(url2);
		}
		for(int i=1;i<=list.size();i++)
		{
			String num = String.valueOf(i-1)+"\r\n";
			doc = Jsoup.connect(list.get(i-1)).get();
			Element link=doc.getElementById("papertitle");
			String Title="Title: "+link.text()+"\r\n";
			link=doc.getElementById("abstract");
			String Abstract="Abstract: "+link.text()+"\r\n";
			String sum = num + Title + Abstract + "\r\n\r\n";
			System.out.println(sum);
	        		writer.write(sum);
		}
		writer.close();
		
	}
}
