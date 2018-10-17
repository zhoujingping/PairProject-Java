import java.io.File;
import java.io.IOException;

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
			int charscount = count.charsCount(handleContent.getHandledContent());
			writeInTxt.writeTxt("characters: " + charscount,parser.getO());
			
			WordsCount wordsCount = new WordsCount(handleContent, parser.getM(), parser.getW());
			int wordscount = count.charsCount(handleContent.getHandledContent());
			writeInTxt.writeTxt("words: " + wordscount,parser.getO());
			writeInTxt.writeTxt("lines: " + linescount,parser.getO());
			count.wordDetail(parser.getI(), parser, wordsCount);
		}
	}
}
