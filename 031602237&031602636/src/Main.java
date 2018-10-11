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
		File file1 = new File(".\\result.txt");
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
<<<<<<< HEAD
			int linescount = count.linesCount(parser.getI());
=======
		//	int linescount = count.linesCount(path);
>>>>>>> 88baa88c2a684785f65ed86f85c6378c40d29a23
		//	int wordscount = count.wordsCount(path);
			int charscount = count.charsCount(handleContent.getHandledContent());
			writeInTxt.writeTxt("characters: " + charscount);
		//	writeInTxt.writeTxt("words: " + wordscount);
<<<<<<< HEAD
			writeInTxt.writeTxt("lines: " + linescount);
		//	count.wordDetail(parser.getI());
			System.out.println(linescount);
=======
		//	writeInTxt.writeTxt("lines: " + linescount);
		//	count.wordDetail(parser.getI());
		//	System.out.println(linescount);
>>>>>>> 88baa88c2a684785f65ed86f85c6378c40d29a23
			System.out.println(charscount);
		}
	}
}
