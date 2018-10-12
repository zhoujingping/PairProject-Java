import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetContent {
    private static String baseUrl = "http://openaccess.thecvf.com/";
    private static Map authors = new HashMap();
    private static List<String>   urls = new ArrayList<String>();
    private static List<String>   titles = new ArrayList<String>();
    private static List<String>   abstracts = new ArrayList<String>();
    private static List<String>   authorss = new ArrayList<String>();


    public static void main(String[] args) throws IOException {
        File fileIn = new File("./cvpr/reptile.txt");

        String content = new String();
        try{
            //读取文件
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(fileIn),"utf8"));

            StringBuffer contents = new StringBuffer();
            int byte_char = -1;
            //开始依次读取字节码
            while ((byte_char = bf.read()) >= 0) {
                contents.append((char)byte_char);
            }
            content = contents.toString();
//            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fileOut = new FileOutputStream("./cvpr/result.txt");
//        FileOutputStream fileOut = new FileOutputStream("./cvpr/author.csv");
//          getAuthor(content);
        getTitle(content);
        getAbstract(urls);
        String result = "";
        for (int i = 0 ;i<titles.size();i++){
            result += i + "\r\n"
                    +"Title: " + titles.get(i).trim()+"\r\n"
                    +"Abstract: " + abstracts.get(i).trim();
            if(i != titles.size()-1){
                result +=  "\r\n\r\n";
            }
        }
        fileOut.write(result.getBytes());
        fileOut.close();
        System.out.println("Done");
    }
    //获取作者
    public static void getAuthor(String content){
        String[] conts = content.trim().split("<dt [^>]*?>[\\w\\W]*?<\\/dt>");
        int i = 0 ;
        int authorNum = 0;
//        String cont = new String();
        for(i = 1;i < 3 ; i++){
            Pattern r = Pattern.compile("<form id=[^>]*?>[\\w\\W]*?<\\/form>");
            Matcher m = r.matcher(conts[i]);
            while ( true ){
                if(m.find()){
                    String author = outHtml(m.group()).trim();
                    author = author.replace(",","");
                    System.out.println(author);
//                    cont += author + "\r\n";
//                    if(authors.containsKey(author)){
//                        int n = (int)authors.get(author);
//                        System.out.println("重复:" + author + " id 在: "  +  n);
//                    }else{
//                        authors.put(author,authorNum);
//                        authorNum++;
//                    }
                } else {
                    break;
                }
            }

        }
//        return cont;
    }
    //获取title和url
    public static void getTitle(String content){
        Pattern r = Pattern.compile("<dt [^>]*?>[\\w\\W]*?<\\/dt>");
        Matcher m = r.matcher(content);
        while ( true ) {
            if (m.find()) {
                Pattern r2 = Pattern.compile("<a [^>]*?>[\\w\\W]*?<\\/a>");
                Matcher m2 = r2.matcher(m.group(0));
                //输出标题
                if (m2.find()) {
                    String url = GetContent.match(m2.group(0), "a", "href");
//                      System.out.println(url);
                    urls.add(url);
                    //筛除html标签
                    String title = outHtml(m2.group(0));
                    titles.add(title);
//                      System.out.println(title);
                }
            } else {
                break;
            }
        }
    }
    //获取摘要
    public  static void getAbstract(List<String> urls){
        int i = 0;
        for(;i < urls.size() ;i++){
            String cont = getHtmltoTxt(baseUrl+urls.get(i));
            Pattern r = Pattern.compile("<div id=\"abstract\" [^>]*?>[\\w\\W]*?<\\/div>");
            Matcher m = r.matcher(cont);
            if(m.find()){
                //筛除html标签
                String  abstr  =m.group(0).replaceAll("</?[^>]+>", "");
                abstracts.add(abstr);
            }

        }
    }
    //获取html标签里面的摸个属性值
    public static String  match(String source, String element, String attr) {
        String result = new String();
        String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"].*?>";
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            String r = m.group(1);
            result = r;
        }
        return result;
    }
    public static String  getHtmltoTxt(String htmlUrl){
        String cont = new String();
        try {
            // 接收字节输入流
            InputStream is;
            URL temp = new URL(htmlUrl);
            // 这个地方需要加入头部 避免大部分网站拒绝访问
            // 这个地方是容易忽略的地方所以要注意
            URLConnection uc = temp.openConnection();
            // 因为现在很大一部分网站都加入了反爬虫机制 这里加入这个头信息
            uc.addRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 "
                            + "(Windows NT 10.0; WOW64) "
                            + "AppleWebKit/537.36"
                            + " (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
            is = temp.openStream();
            // 为字节输入流加入缓冲
            BufferedInputStream bis = new BufferedInputStream(is);
            StringBuffer contents = new StringBuffer();
            int byte_char = -1;
            //开始依次读取字节码
            while ((byte_char = bis.read()) >= 0) {
                contents.append((char)byte_char);
            }
            cont = contents.toString();
            bis.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cont;
    }
    //筛除html标签
    public static String outHtml(String cont){
        String  con =cont.replaceAll("</?[^>]+>", "");
        return con;
    }

}
