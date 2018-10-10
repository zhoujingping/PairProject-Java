import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GetContentMain {
    public static void main(String[] args) throws IOException {
        String url = "http://openaccess.thecvf.com/CVPR2018.py";
        // 这是将首页的信息存入到一个html文件中 为了后面分析html文件里面的信息做铺垫
        File dest = new File("./cvpr/reptile.txt");
        // 字节输出流
        FileOutputStream fos = new FileOutputStream(dest);
        String cont = GetContent.getHtmltoTxt(url);
        fos.write(cont.getBytes());
//        System.out.println(cont);
        fos.close();
    }
}
