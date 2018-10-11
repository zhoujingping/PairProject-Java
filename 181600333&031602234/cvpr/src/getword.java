import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.jsoup.nodes.Element;
import java.io.File;
import java.io.PrintWriter;
public class getword {

    //定义地址
    private static String url = "http://openaccess.thecvf.com/CVPR2018.py";

    public static void main(String[] args) {
        int number=0;
        int i=0;
        String code=geturlcode(url);
        String code1=null;
        //将源代码转换为Doc
        Document doc= Jsoup.parse(code);
        //筛选子网页连接
        Elements ele=(doc).select("dt[class=ptitle]");
        number=ele.size();
        File f=new File("result.txt");
        try {
            PrintWriter output = new PrintWriter(f);
        for(Element link : ele){
            String test="http://openaccess.thecvf.com/";
            link=link.child(1);
            test=test+link.attr("href");
            String text =null;
            //读取子网页内容
            text=geturlcode(test);
            text=gettext(text);
                output.print(i);
                i++;
                output.print("\r\n");
                output.print(text);
        }
        output.close();
        }catch (Exception e) {
            System.out.println("写文件错误");
        }
        System.out.println("成功");

    }
    //筛选标题与摘要
    public static String gettext(String code)
    {
        String a,b,c;
        Document doc=Jsoup.parse(code);
        Elements ele1=(doc).select("div[id=papertitle]");
        a=ele1.text();
        a=a.replace("/n","");
        Elements ele2=(doc).select("div[id=abstract]");
        b=ele2.text();
        b=b.replace("/n","");
        c="Title: "+a+"\r\n"+"Abstract: "+b+"\r\n"+"\r\n"+"\r\n";
        return c;
    }

    public static String geturlcode(String url) {
        //定义url
        URL newurl = null;
        //定义连接
        URLConnection urlcon = null;
        //定义输入
        InputStream input = null;
        //定义读取
        InputStreamReader reader = null;
        //定义输出
        BufferedReader breader = null;
        StringBuilder code = new StringBuilder();
        try {
            //获取地址
            newurl = new URL(url);
            //获取连接
            urlcon = newurl.openConnection();
            //获取输入
            input = urlcon.getInputStream();
            //读取输入
            reader = new InputStreamReader(input);
            //输出
            breader = new BufferedReader(reader);
            String temp = null;
            while ((temp = breader.readLine()) != null) {
                code.append(temp + "/n");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code.toString();
    }
}