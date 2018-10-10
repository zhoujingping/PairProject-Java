

import java.io.*;
import java.util.*;

public class Lib {

	static int lines = 0;// 行数
	static int words = 0;// 单词数
	static int characters = 0;
	static ArrayList<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>();// 排序好的单词列表
	static ArrayList<String> titleList = new ArrayList<String>();// 存取单词列表
	static ArrayList<String> abstractList = new ArrayList<String>();// 存取单词列表
	static HashMap<String, Integer> hashMap = new HashMap<String, Integer>();// 用于储存词组或单词

	// 预处理，计算出行数以及字符数，将Title和Abstract中的单词分开存放
	public static void Preprocess(File f) {

		BufferedReader br = null;
		String str = null;

		try {
			br = new BufferedReader(new FileReader(f));
			while ((str = br.readLine()) != null) {

				if (str.matches("[0-9]+")) {

					str = br.readLine();
					str = str.replaceFirst("Title: ", "");

					// 处理Title中的字符
					do {
						if (!str.trim().isEmpty()) {
							Lib.lines++;// 计算行数
						}

						Lib.characters++;// 把每行后的换行符加上
						Lib.characters += str.length();

						Lib.titleList.add(str);

					} while ((str = br.readLine()) != null && !str.matches("Abstract: [\\s\\S]*"));

					if (str != null) {
						// 处理Abstract中的字符
						str = str.replaceFirst("Abstract: ", "");
						do {
							if (!str.trim().isEmpty()) {
								Lib.lines++;// 计算行数
							}
							Lib.characters++;// 把每行后的换行符加上
							Lib.characters += str.length();

							Lib.abstractList.add(str);

						} while ((str = br.readLine()) != null && !str.matches(""));
					} else {
						System.out.println("格式错误！");
						System.exit(0);
					}
					if (str != null) {
						if ((str = br.readLine()) != null && str.matches(""))
							continue;
						else {
							break;
						}

					} else {
						Lib.characters--;
						break;
					}
				}

			}

			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void listSort() {

		list = new ArrayList<Map.Entry<String, Integer>>(hashMap.entrySet());

		// 排序

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
				int re = e2.getValue() - e1.getValue();
				return (re == 0) ? e1.getKey().compareTo(e2.getKey()) : re;

			}

		});
	}

	public static void wordCount(int w) {

		int incre = (w == 1 ? 10 : 1);
		String[] wordArray = null;

		// 处理Title

		for (int i = 0; i < titleList.size(); i++) {

			wordArray = titleList.get(i).split("\\s*[^a-zA-Z0-9]+");
			for (String word : wordArray) {
				if (word.matches("[a-zA-Z]{4,}[a-zA-Z0-9]*")) {
					Lib.words++;
					word = word.toLowerCase();

					if (hashMap.containsKey(word)) {
						hashMap.put(word, hashMap.get(word) + incre);// 计算单词出现次数
					} else {
						hashMap.put(word, incre);
					}
				}
			}
		}

		// 处理Abstract

		for (int i = 0; i < abstractList.size(); i++) {

			wordArray = abstractList.get(i++).split("\\s*[^a-zA-Z0-9]+");
			for (String word : wordArray) {
				if (word.matches("[a-zA-Z]{4,}[a-zA-Z0-9]*")) {
					Lib.words++;
					word = word.toLowerCase();

					if (hashMap.containsKey(word)) {
						hashMap.put(word, hashMap.get(word) + incre);// 计算单词出现次数
					} else {
						hashMap.put(word, incre);
					}
				}
			}
		}

	}

	public static void phraseCount(int w, int m) {

		String[] wordArray = null;// 单词数组
		String[] sepArray = null;// 分隔符数组
		StringBuffer sb = new StringBuffer();
		int count = 0;
		int incre = (w == 1 ? 10 : 1);
		int tmp = 0;
		int wALength = 0;

		for (int p = 0; p < titleList.size(); p++) {

			tmp = 0;
			count = 0;
			wordArray = titleList.get(p).split("\\s*[^a-zA-Z0-9]+");
			sepArray = titleList.get(p).split("[a-zA-Z0-9]+");

			// 若以分隔符开头 合并时以第二个分隔符开始
			if (titleList.get(p).matches("\\s*[^a-zA-Z0-9]+[\\s\\S]*")) {
				tmp = 1;
			}

			for (int i = 0; i < wordArray.length; i++) {

				if (wordArray[i].matches("[a-zA-Z]{4,}[a-zA-Z0-9]*")) {
					Lib.words++;
					count++;
				} else {
					if (count >= m) {
						int tmpCount = count - m + 1;
						for (int j = 0; j < tmpCount; j++) {
							sb.delete(0, sb.length());
							sb.append(wordArray[i - count + j]);
							for (int k = 1, l = i - count + 1 + j; k < m; k++, l++) {

								sb.append(sepArray[l - tmp]).append(wordArray[l]);

							}
							String phrase = sb.toString().toLowerCase();
							if (hashMap.containsKey(phrase)) {
								hashMap.put(phrase, hashMap.get(phrase) + incre);
							} else {
								hashMap.put(phrase, incre);
							}
						}
					}
					count = 0;
				}
			}

			if (count >= m) {
				int tmpCount = count - m + 1;
				wALength = wordArray.length;
				for (int j = 0; j < tmpCount; j++) {
					sb.delete(0, sb.length());
					sb.append(wordArray[wALength - count + j]);
					for (int k = 1, l = wALength - count + 1 + j; k < m; k++, l++) {
						sb.append(sepArray[l - tmp]).append(wordArray[l]);

					}
					String phrase = sb.toString().toLowerCase();
					if (hashMap.containsKey(phrase)) {
						hashMap.put(phrase, hashMap.get(phrase) + incre);
					} else {
						hashMap.put(phrase, incre);
					}
				}
			}
		}

		// 处理Abstract
		for (int p = 0; p < abstractList.size(); p++) {

			count = 0;
			wordArray = abstractList.get(p).split("\\s*[^a-zA-Z0-9]+");
			sepArray = abstractList.get(p).split("[a-zA-Z0-9]+");

			for (int i = 0; i < wordArray.length; i++) {

				if (wordArray[i].matches("[a-zA-Z]{4,}[a-zA-Z0-9]*")) {
					Lib.words++;
					count++;
				} else {
					if (count >= m) {
						int tmpCount = count - m + 1;
						for (int j = 0; j < tmpCount; j++) {
							sb.delete(0, sb.length());
							sb.append(wordArray[i - count + j]);
							for (int k = 1, l = i - count + 1 + j; k < m; k++, l++) {
								sb.append(sepArray[l - tmp]).append(wordArray[l]);
							}
							String phrase = sb.toString().toLowerCase();
							if (hashMap.containsKey(phrase)) {
								hashMap.put(phrase, hashMap.get(phrase) + 1);
							} else {
								hashMap.put(phrase, 1);
							}
						}
					}
					count = 0;
				}
			}

			if (count >= m) {
				int tmpCount = count - m + 1;
				int WALength = wordArray.length;
				for (int j = 0; j < tmpCount; j++) {
					sb.delete(0, sb.length());
					sb.append(wordArray[WALength - count + j]);
					for (int k = 1, l = WALength - count + 1 + j; k < m; k++, l++) {
						sb.append(sepArray[l - tmp]).append(wordArray[l]);
					}
					String phrase = sb.toString().toLowerCase();
					if (hashMap.containsKey(phrase)) {
						hashMap.put(phrase, hashMap.get(phrase) + 1);
					} else {
						hashMap.put(phrase, 1);
					}
				}
			}
		}

	}

}
