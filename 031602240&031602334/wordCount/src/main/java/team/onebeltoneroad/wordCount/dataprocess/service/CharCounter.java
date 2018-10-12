package team.onebeltoneroad.wordCount.dataprocess.service;

import java.io.*;

/**
 * 字符计数器，包括计算Title和Abstract内总字符数.
 *
 * @author xyy
 * @version 2.0 2018/10/11
 * @since 2018/9/11
 */
public class CharCounter {
    /**
     * 计算Title和Abstract内总字符数.
     *
     * @param fileName 文件名
     * @return 总字符数
     */
    public static long countChar(String fileName) {
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String in = "";
        long charNum = 0;

        //读入文件
        try {
            inputStreamReader = new InputStreamReader(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("CharCounter找不到此文件");
            e.printStackTrace();
        }
        if (inputStreamReader != null) {
            bufferedReader = new BufferedReader(inputStreamReader);
        }
        //计算字符数
        try {
            while ((in = bufferedReader.readLine()) != null) {
                if (in.contains("Title: ")) {
                    charNum += in.replaceAll("\r\n", "\n").length() - 7;
                } else {
                    if (in.contains("Abstract: ")) {
                        charNum += in.replaceAll("\r\n", "\n").length() - 10;
                    }
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
