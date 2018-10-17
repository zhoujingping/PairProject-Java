package elementCounter;
import java.io.*;
public class LineCounter {
	 public static long countLine(String fileName) {
	        InputStreamReader inputStreamReader = null;
	        BufferedReader bufferedReader = null;
	        String in = null;
	        long lineNum = 0;

	        //读入文件
	        try {
	            inputStreamReader = new InputStreamReader(new FileInputStream(fileName));
	        } catch (FileNotFoundException e) {
	            System.out.println("找不到此文件");
	            e.printStackTrace();
	        }
	        if (inputStreamReader != null) {
	            bufferedReader = new BufferedReader(inputStreamReader);
	        }
	        //计算行数
	        try {
	            while ((in = bufferedReader.readLine()) != null) {
	                if (!in.equals("") && (in.toString().charAt(0) =='T' || in.toString().charAt(0) == 'A') ) lineNum++;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                inputStreamReader.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return lineNum;
	}
}
