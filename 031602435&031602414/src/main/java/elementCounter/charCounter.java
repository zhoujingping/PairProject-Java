package elementCounter;
import java.io.*;
public class charCounter {
	public static long countChar(String fileName) {
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        int in = 0;
        long charNum = 0;
        String str = null;
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
        //计算字符数
        try {
        	while ((str = bufferedReader.readLine()) != null) {
	
				
                if(str.length() > 0) {
                	int i = 0;
                	if (str.charAt(0) == 'T') {
    					i += 7;
    				}
                	else if (str.charAt(0) == 'A') {
    					i += 10;
    				}
                	charNum += str.length() - i;
                }
            	
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
        return charNum;
}
}
