import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
	public static void main(String[] args) throws IOException {
		Parser parser;

        if (args == null || args.length == 0) {
            parser = new Parser();
        } else {
            parser = new Parser(args);
        }

    	HandleContent handleContent = new HandleContent(parser.getI());
    	
		File file = new File(parser.getI());
		File file1 = new File(parser.getO());
		if (file1.exists() && file.isFile()) {
			file1.delete();
		}
		if (!file.exists()) {
			System.out.println(file + "文件没有找到");
			System.exit(0);
		}else if (!file.isFile()) {
			System.out.println("文件读异常");
			System.exit(0);
		}else {
			wordCount count = new lib();
			int linescount = count.linesCount(parser.getI());
		//	int wordscount = count.wordsCount(path);
			int charscount = count.charsCount(handleContent.getHandledContent());
			writeInTxt.writeTxt("characters: " + charscount,parser.getO());
			
			WordsCount wordsCount = new WordsCount(handleContent, parser.getM(), parser.getW());
			int words = wordsCount.getSum();
			String wordStr=words+"";
			writeInTxt.writeTxt("words: " + wordStr,parser.getO());
			writeInTxt.writeTxt("lines: " + linescount,parser.getO());
			
			List<Map.Entry<String, Integer>> mostList = new CalMost().mostWords(wordsCount.getMap(), parser.getN());
			for (Map.Entry<String, Integer> i : mostList) {
				writeInTxt.writeTxt("<"+i.getKey()+">: " + i.getValue(),parser.getO());
            }
			
			
		//	count.wordDetail(parser.getI());
			System.out.println(linescount);
			System.out.println(charscount);
		}
	}
}
