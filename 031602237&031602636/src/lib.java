

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class lib implements wordCount{
	@Override
	public int linesCount(String filepath) throws IOException {
		int linescount = 0;
        try {
            FileReader fileReader = new FileReader(filepath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
            	System.out.println(line);
                if (line.matches("\\d+")) {
                    
                } else if (line.length() != 0 && !line.matches("\\s+")) {
                    linescount++;
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linescount;
	}

	@Override
	public int wordsCount(String filepath) throws IOException {
		
		return 0;
	}

	@Override
	public int charsCount(String content) throws IOException {
		String regex = "\\p{ASCII}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        int sum = -1;
        while (matcher.find()) {
            sum++;
        }
        return sum;
	}

	@Override
	public void wordDetail(String filepath,Parser parser,WordsCount wordsCount) throws IOException {
		//Êä³öÇ°n
		List<Map.Entry<String, Integer>> mostList = new WordCmp().mostWords(wordsCount.getMap(), parser.getN());
		for (Map.Entry<String, Integer> i : mostList) {
			writeInTxt.writeTxt("<"+i.getKey()+">: " + i.getValue(),parser.getO());
        }
	}

}
