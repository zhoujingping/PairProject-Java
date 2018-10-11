

import java.io.IOException;

public interface wordCount {
	/**
	 * 返回行数
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	int linesCount(String filepath) throws IOException;
	
	/**
	 * 返回合法单词数
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	int wordsCount(String filepath) throws IOException;
	
	/**
	 * 返回字符数
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	int charsCount(String filepath) throws IOException;
	
	/**
	 * 词频前十的单词
	 * @param filepath
	 * @throws IOException
	 */
	void wordDetail(String filepath) throws IOException;
}
