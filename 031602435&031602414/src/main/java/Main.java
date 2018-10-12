import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import elementCounter.LineCounter;
import elementCounter.charCounter;
import elementCounter.phraseFrequencyCounter;
import elementCounter.wordFrequencyCounter;
import elementCounter.wordNumberCounter;
public class Main {
	public static void main(String[] args) {  
        /* 
         * 这里先取默认值，因为不是所有参数都会被用户提供 
         */  
        String in = " ";
        String o = " ";
        int m = 1;
        int n = 10;
        int w = 1;
        
        long charNum = 0;
        long lineNum = 0;
        long wordNum = 0;
        /* 
         * 设置一个offset变量，用来定位相关信息 
         */  
        int optSetting = 0;  
        for (; optSetting < args.length; optSetting++) {  
            if ("-i".equals(args[optSetting])) {  
                in =  args[++optSetting];
            } else if ("-o".equals(args[optSetting])) {  
            	o =  args[++optSetting];  
            } else if ("-m".equals(args[optSetting])) {  
                m =  Integer.parseInt(args[++optSetting]); 
            } else if ("-n".equals(args[optSetting])) {  
            	n =  Integer.parseInt(args[++optSetting]); 
            } else if ("-w".equals(args[optSetting])) {  
            	w =  Integer.parseInt(args[++optSetting]);  
            }          
        }  
          
        File file = new File(o);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            charCounter charCounter = new charCounter();
            LineCounter lineCounter = new LineCounter();
            wordNumberCounter wordNumberCounter = new wordNumberCounter();
            charNum = charCounter.countChar(in);
            wordNum = wordNumberCounter.countWord(in);
            lineNum = lineCounter.countLine(in);
            //输出字符数
            bufferedWriter.write("characters: " + charNum + "\r\n" + "\r\n");
            //输出单词数
            bufferedWriter.write("words: " + wordNum + "\r\n" + "\r\n");
            //输出行数
            bufferedWriter.write("lines: " + lineNum + "\r\n" + "\r\n");
            
            if (m == 1) {
            	ArrayList<HashMap.Entry<String, Integer>> wordList = new ArrayList<HashMap.Entry<String, Integer>>();
        		HashMap<String, Integer> wordlistMap = new HashMap<String, Integer>();
        		wordlistMap = wordFrequencyCounter.countWordsFrequency(in,0);
        		wordList = wordFrequencyCounter.topFrequentWords(wordlistMap);
            	
            	int size = wordList.size();
                if (size >= n) {
                    for (int i = 0; i < n; i++) {
                        bufferedWriter.write("<" + wordList.get(i).getKey() + ">: "
                                + wordList.get(i).getValue() + "\r\n" + "\r\n");
                    }
                } else {
                    for (HashMap.Entry<String, Integer> map : wordList) {
                        bufferedWriter.write("<" + map.getKey() + ">: " + map.getValue() + "\r\n" + "\r\n");
                    }
                }

			}else if (m > 1) {
				ArrayList<HashMap.Entry<String, Integer>> wordList = new ArrayList<HashMap.Entry<String, Integer>>();
				HashMap<String, Integer> wordlistMap = new HashMap<String, Integer>();
				wordlistMap = phraseFrequencyCounter.countPhraseFrequency(in,0,3);
				wordList = phraseFrequencyCounter.topFrequentPhrases(wordlistMap);
				
				int size = wordList.size();
                if (size >= n) {
                    for (int i = 0; i < n; i++) {
                        bufferedWriter.write("<" + wordList.get(i).getKey() + ">: "
                                + wordList.get(i).getValue() + "\r\n" + "\r\n");
                    }
                } else {
                    for (HashMap.Entry<String, Integer> map : wordList) {
                        bufferedWriter.write("<" + map.getKey() + ">: " + map.getValue() + "\r\n" + "\r\n");
                    }
                }
			}else {
				System.out.println("单词/词组长度不能为负！");
			}

            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
}
        
      
}
