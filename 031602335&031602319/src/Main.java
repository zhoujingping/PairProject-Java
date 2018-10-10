

import java.io.*;
import java.util.*;

public class Main {

	private int lines = 0;// 行数
	private int characters = 0;// 字符数
	private int words = 0;// 单词数
	private String readFileName = null;// 读入文件名
	private String resultFileName = null;// 输出文件名
	private File f = null;// 读入文件
	private int w = 0;// 权重
	private int m = 0;// 词频统计词组长度
	private int n = 10;// 词频统计输出词组或者单词的个数
	private ArrayList<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>();// 排序好的单词列表

	public static void main(String[] args) {

		Main m = new Main();

		m.readFile(args);
		Lib.Preprocess(m.getF());

		if (m.getM() != 0 && m.getM() != 1) {
			Lib.phraseCount(m.getW(), m.getM());
		} else {
			Lib.wordCount(m.getW());
		}

		Lib.listSort();

		m.characters = Lib.characters;
		m.lines = Lib.lines;
		m.words = Lib.words;
		m.list = Lib.list;

		m.writeFile(m.getResultFileName(), m.getN());

	}

	public String getResultFileName() {
		return resultFileName;
	}

	public File getF() {
		return f;
	}

	public int getN() {
		return n;
	}

	public int getW() {
		return w;
	}

	public int getM() {
		return m;
	}

	// 读入文件

	public void readFile(String[] args) {

		if (!(args.length == 0 && args == null)) {

			int index = 0;
			while (index < args.length) {
				switch (args[index]) {
				case "-i":
					readFileName = args[++index];
					break;
				case "-o":
					resultFileName = args[++index];
					break;
				case "-w":
					w = Integer.parseInt(args[++index]);
					break;
				case "-m":
					m = Integer.parseInt(args[++index]);
					break;
				case "-n":
					n = Integer.parseInt(args[++index]);
					break;
				}
				index++;
			}
			f = new File(readFileName);
			if (!f.exists()) {
				System.out.println(f + "文件不存在");
				System.exit(0);
			}
		}

	}

	// 写入文件

	public void writeFile(String resultFileName, int n) {

		File f = new File(resultFileName);
		FileOutputStream fos = null;
		try {
			f.createNewFile();
			fos = new FileOutputStream(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintStream ps = new PrintStream(fos);
		System.setOut(ps);
		System.out.println("characters: " + characters);
		System.out.println("words: " + words);
		System.out.println("lines: " + lines);

		for (int i = 0; i < n && i < list.size(); i++) {

			System.out.println("<" + list.get(i).getKey() + ">" + ": " + list.get(i).getValue());
		}

	}

}
