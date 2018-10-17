package cvpr;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class clawler {
	public Map<String, String> getPaperInfo() {
		Map<String, String> paperInfo = new HashMap<String, String>();
		
		
		ArrayList<String> htmlLinks = new ArrayList<String>();
		String head = "http://openaccess.thecvf.com/";//html链接前半部分
		
		try
		{
			Document doc = null ;
			doc = Jsoup.connect("http://openaccess.thecvf.com/CVPR2018.py#").get();
		    //Document document = Jsoup.parse(new File("C:/Users/zkpkhua/Desktop/yiibai-index.html"), "utf-8");
		    Elements links = doc.select("dt a[href]");  
		    for (Element link : links) 
		    {

		        	//System.out.println("link : " + link.attr("href")); 
		        	htmlLinks.add(head+link.attr("href"));//得到html链接
		        	//System.out.println(head+link.attr("href"));
		    }
		} 
		catch (IOException e) 
		{
		    e.printStackTrace();
		}
		
		try
		{
			for (String htmlLink : htmlLinks) {
				Document doc = null ;
				doc = Jsoup.connect(htmlLink).get();
				Elements titleInfo = doc.select("div#papertitle"); 
				Elements abstractInfo = doc.select("div#abstract"); 
				//System.out.println(titleInfo.text());
				paperInfo.put(titleInfo.text(), abstractInfo.text());
			} 
		   
		} 
		catch (IOException e) 
		{
		    e.printStackTrace();
		}
		return paperInfo;
	}
	
	public void writeResult() throws Exception {
		Map<String, String> paperInfo = new HashMap<String, String>();
		clawler clawler = new clawler();
		paperInfo = clawler.getPaperInfo();
		String path = System.getProperty("user.dir")+"\\src\\main\\java\\cvpr\\result.txt";
		File file = new File(path);
		StringBuilder result = new StringBuilder("");
		int count = 0;
		FileWriter filewriter = new FileWriter(file.getAbsoluteFile());
		//System.out.println("absolutely path:"+file.getAbsolutePath());
		BufferedWriter bufferedWriter = new BufferedWriter(filewriter);
		
		
		for (Entry<String, String> entry : paperInfo.entrySet()) {
			result.append(count);
			count++;
			result.append("\r\n");
			result.append("Title:"+entry.getKey());
			result.append("\r\n");
			result.append("Abstract:"+entry.getValue());
			result.append("\r\n");
			result.append("\r\n");
			result.append("\r\n");
			//System.out.println("title:  "+entry.getKey());
			//System.out.println("abstract:  "+entry.getValue());
		}
		
		
		
		bufferedWriter.write(result.toString());
		
		bufferedWriter.close();
	}
	

}
