package team.onebeltoneroad.wordCount.datacrawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * FileName: Crawl.java
 * 这个类爬取类CVPR2018官网上的论文标题及简介.
 *
 * @author Ding
 * @version 1.0
 */
public class Crawl {
    public static void main(String[] args) {
        //网站主页url
        String url = "http://openaccess.thecvf.com/CVPR2018.py";
        getContent(url);
    }

    /**
     * 生成论文标题及简介文档.
     *
     * @param URL 数据来源的URL
     */
    public static void getContent(String URL) {
        try {
            //定义输出文件地址
            File resultFile = new File("/Users/wenyiqian/Documents/la/result.txt");
            //定义写字符流
            BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile));
            //从URL加载Document
            Document doc = Jsoup.connect(URL)
                    //取消获取相应内容大小限制
                    .maxBodySize(0)
                    //设置超时时间
                    .timeout(600000)
                    .get();
            //ptitle类
            Elements paper = doc.select("[class=ptitle]");
            //ptitle类中带有href属性的a元素
            Elements links = paper.select("a[href]");
            //论文计数
            long count = 0;
            for (Element link : links) {
                //论文页url
                String url = link.absUrl("href");
                Document paperDoc = Jsoup.connect(url)
                        .maxBodySize(0)
                        .timeout(600000)
                        .get();
                Elements paperTitle = paperDoc.select("[id=papertitle]");
                //获取论文标题
                String title = paperTitle.text();
                Elements paperAbstract = paperDoc.select("[id=abstract]");
                //获取论文简介
                String abstracts = paperAbstract.text();
                writer.write(count + "\r\n");
                writer.write("Title: " + title + "\r\n");
                writer.write("Abstract: " + abstracts + "\r\n" + "\r\n" + "\r\n");
                count++;
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}