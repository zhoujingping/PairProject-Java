package cvpr;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	public static void main(String[] args) throws IOException {
		File file1 = new File(".\\result.txt");
		if (file1.exists() && file1.isFile()) {
			file1.delete();
		}
		Document document = Jsoup.connect("http://openaccess.thecvf.com/CVPR2018.py").userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)").get();
		Element main = document.getElementById("content");
		for(int i=0;;i++)
		{
			//#content > dl > dt:nth-child(1) > a
			//#content > dl > dt:nth-child(4)
			//#content > dl > dt:nth-child(7)
			int l = i*3+1;
			//#content > dl > dt:nth-child(2935)
			Elements url = main.select("dl").select("dt:nth-child(" + l + ")").select("a");
			for(Element question:url) {
				String URL = question.attr("abs:href");
				Document document2 = Jsoup.connect(URL).get();
				//#papertitle
				Elements title = document2.select("#papertitle");
				//#abstract
				Elements Abstract = document2.select("#abstract");
				System.out.println(i);
				writeInTxt.writeTxt(String.valueOf(i));
				writeInTxt.writeTxt("Title: " + title.text());
				writeInTxt.writeTxt("Abstract" + Abstract.text());
				writeInTxt.writeTxt("");
			}
		}
	}
}
