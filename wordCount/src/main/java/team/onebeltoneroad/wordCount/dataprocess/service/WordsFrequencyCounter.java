package team.onebeltoneroad.wordCount.dataprocess.service;

import java.io.*;
import java.util.*;

/**
 * 词频计算器，包括计算Title和Abstract中各单词词频，只输出频率最高的n个，n可设定.
 * 频率相同的单词，优先输出字典序靠前的单词.
 *
 * @author xyy
 * @version 2.0 2018/10/11
 * @since 2018/9/11
 */
public class WordsFrequencyCounter {
    /**
     * 读取并计算Title和Abstract词频.
     *
     * @param fileName     文件名
     * @param weightFactor 权重参数
     * @return 各单词词频
     */
    public static HashMap<String, Long> countWordsFrequency(String fileName, int weightFactor) {
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String in = "";
        char temp;
        int state = 0;
        StringBuilder word = new StringBuilder();
        HashMap<String, Long> wordMap = new HashMap<String, Long>(100 * 1024 * 1024);

        //读入文件
        try {
            inputStreamReader = new InputStreamReader(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("WordsFrequencyCounter找不到此文件");
            e.printStackTrace();
        }
        if (inputStreamReader != null) {
            bufferedReader = new BufferedReader(inputStreamReader);
        }
        //计算单词词频
        try {
            while ((in = bufferedReader.readLine()) != null) {
                if (in.contains("Title: ")) {
                    word.setLength(0);
                    int length = in.length();
                    state = 0;
                    for (int i = 7; i < length; i++) {
                        temp = in.charAt(i);
                        //大写字母转为小写字母
                        if ((temp >= 65) && (temp <= 90)) {
                            temp += 32;
                        }
                        //自动机状态转移
                        switch (state) {
                            case 0: {
                                if ((temp >= 97) && (temp <= 122)) {
                                    word.append(temp);
                                    state = 1;
                                }
                                break;
                            }
                            case 1: {
                                if ((temp >= 97) && (temp <= 122)) {
                                    word.append(temp);
                                    state = 2;
                                } else {
                                    word.setLength(0);
                                    state = 0;
                                }
                                break;
                            }
                            case 2: {
                                if ((temp >= 97) && (temp <= 122)) {
                                    word.append(temp);
                                    state = 3;
                                } else {
                                    word.setLength(0);
                                    state = 0;
                                }
                                break;
                            }
                            case 3: {
                                if ((temp >= 97) && (temp <= 122)) {
                                    word.append(temp);
                                    state = 4;
                                } else {
                                    word.setLength(0);
                                    state = 0;
                                }
                                break;
                            }
                            case 4: {
                                if (((temp >= 97) && (temp <= 122)) || ((temp >= '0') && (temp <= '9'))) {
                                    word.append(temp);
                                } else {
                                    if (weightFactor == 1) {
                                        if (wordMap.containsKey(word.toString())) {
                                            wordMap.put(word.toString(), wordMap.get(word.toString()) + 10L);
                                        } else {
                                            wordMap.put(word.toString(), 10L);
                                        }
                                    } else {
                                        if (wordMap.containsKey(word.toString())) {
                                            wordMap.put(word.toString(), wordMap.get(word.toString()) + 1L);
                                        } else {
                                            wordMap.put(word.toString(), 1L);
                                        }
                                    }
                                    word.setLength(0);
                                    state = 0;
                                }
                                break;
                            }
                        }
                    }
                    if (state == 4) {
                        if (weightFactor == 1) {
                            if (wordMap.containsKey(word.toString())) {
                                wordMap.put(word.toString(), wordMap.get(word.toString()) + 10L);
                            } else {
                                wordMap.put(word.toString(), 10L);
                            }
                        } else {
                            if (wordMap.containsKey(word.toString())) {
                                wordMap.put(word.toString(), wordMap.get(word.toString()) + 1L);
                            } else {
                                wordMap.put(word.toString(), 1L);
                            }
                        }
                    }
                } else {
                    if (in.contains("Abstract: ")) {
                        word.setLength(0);
                        int length = in.length();
                        state = 0;
                        for (int i = 10; i < length; i++) {
                            temp = in.charAt(i);
                            //大写字母转为小写字母
                            if ((temp >= 65) && (temp <= 90)) {
                                temp += 32;
                            }
                            //自动机状态转移
                            switch (state) {
                                case 0: {
                                    if ((temp >= 97) && (temp <= 122)) {
                                        word.append(temp);
                                        state = 1;
                                    }
                                    break;
                                }
                                case 1: {
                                    if ((temp >= 97) && (temp <= 122)) {
                                        word.append(temp);
                                        state = 2;
                                    } else {
                                        word.setLength(0);
                                        state = 0;
                                    }
                                    break;
                                }
                                case 2: {
                                    if ((temp >= 97) && (temp <= 122)) {
                                        word.append(temp);
                                        state = 3;
                                    } else {
                                        word.setLength(0);
                                        state = 0;
                                    }
                                    break;
                                }
                                case 3: {
                                    if ((temp >= 97) && (temp <= 122)) {
                                        word.append(temp);
                                        state = 4;
                                    } else {
                                        word.setLength(0);
                                        state = 0;
                                    }
                                    break;
                                }
                                case 4: {
                                    if (((temp >= 97) && (temp <= 122)) || ((temp >= '0') && (temp <= '9'))) {
                                        word.append(temp);
                                    } else {
                                        if (wordMap.containsKey(word.toString())) {
                                            wordMap.put(word.toString(), wordMap.get(word.toString()) + 1L);
                                        } else {
                                            wordMap.put(word.toString(), 1L);
                                        }
                                        word.setLength(0);
                                        state = 0;
                                    }
                                    break;
                                }
                            }
                        }
                        if (state == 4) {
                            if (wordMap.containsKey(word.toString())) {
                                wordMap.put(word.toString(), wordMap.get(word.toString()) + 1L);
                            } else {
                                wordMap.put(word.toString(), 1L);
                            }
                        }
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
        return wordMap;
    }

    /**
     * 求频率最高的n个单词
     *
     * @param wordMap 各单词词频
     * @return 频率最高的n个单词
     */
    public static ArrayList<HashMap.Entry<String, Long>> topFrequentWords(HashMap<String, Long> wordMap) {
        //将Map转换为ArrayList
        ArrayList<HashMap.Entry<String, Long>> wordList =
                new ArrayList<HashMap.Entry<String, Long>>(wordMap.entrySet());
        sort(wordList);
        return wordList;
    }

    private static void sort(ArrayList<HashMap.Entry<String, Long>> wordList) {
        Collections.sort(wordList, new Comparator<HashMap.Entry<String, Long>>() {
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                if (o1.getValue() < o2.getValue()) {
                    return 1;
                } else {
                    if (o1.getValue().equals(o2.getValue())) {
                        if (o1.getKey().compareTo(o2.getKey()) > 0) {
                            return 1;
                        } else {
                            return -1;
                        }
                    } else {
                        return -1;
                    }
                }
            }
        });
    }
}
