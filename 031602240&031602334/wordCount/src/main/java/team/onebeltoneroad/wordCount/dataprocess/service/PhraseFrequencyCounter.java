package team.onebeltoneroad.wordCount.dataprocess.service;

import team.onebeltoneroad.wordCount.dataprocess.entity.PhraseInfo;
import team.onebeltoneroad.wordCount.dataprocess.entity.WordInfo;

import java.io.*;
import java.util.*;

/**
 * 词频计算器，包括计算Title和Abstract中各词组词频，只输出频率最高的n个，n可设定.
 * 频率相同的词组，优先输出字典序靠前的词组.
 *
 * @author xyy
 * @version 2.0 2018/10/11
 * @since 2018/9/11
 */
public class PhraseFrequencyCounter {
    private static ArrayDeque<WordInfo> wordsDeque = new ArrayDeque<>();
    private static PhraseInfo phraseInfo = new PhraseInfo();

    /**
     * 读取并计算Title和Abstract词组词频.
     *
     * @param fileName     文件名
     * @param weightFactor 权重参数
     * @param phraseLength 词组长度
     * @return 各词组词频
     */
    public static HashMap<String, Long> countPhraseFrequency(String fileName, int weightFactor, int phraseLength) {
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String in = "";
        char temp;
        int state = 0;
        int startSubscript = 0;
        int endSubscript = 0;
        HashMap<String, Long> phraseMap = new HashMap<String, Long>(100 * 1024 * 1024);

        //读入文件
        try {
            inputStreamReader = new InputStreamReader(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("PhraseFrequencyCounter找不到此文件");
            e.printStackTrace();
        }
        if (inputStreamReader != null) {
            bufferedReader = new BufferedReader(inputStreamReader);
        }
        //计算单词词频
        try {
            while ((in = bufferedReader.readLine()) != null) {
                if (in.contains("Title: ")) {
                    wordsDeque.clear();
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
                                    startSubscript = i;
                                    state = 1;
                                }
                                break;
                            }
                            case 1: {
                                if ((temp >= 97) && (temp <= 122)) {
                                    state = 2;
                                } else {
                                    wordsDeque.clear();
                                    state = 0;
                                }
                                break;
                            }
                            case 2: {
                                if ((temp >= 97) && (temp <= 122)) {
                                    state = 3;
                                } else {
                                    wordsDeque.clear();
                                    state = 0;
                                }
                                break;
                            }
                            case 3: {
                                if ((temp >= 97) && (temp <= 122)) {
                                    endSubscript = i;
                                    state = 4;
                                } else {
                                    wordsDeque.clear();
                                    state = 0;
                                }
                                break;
                            }
                            case 4: {
                                if (((temp >= 97) && (temp <= 122)) || ((temp >= '0') && (temp <= '9'))) {
                                    endSubscript = i;
                                } else {
                                    if (constructPhrase(startSubscript, endSubscript, phraseLength)) {
                                        StringBuilder phrase = new StringBuilder();
                                        int start = phraseInfo.getStartSubscript();
                                        int end = phraseInfo.getEndSubscript();
                                        char tempc;
                                        for (int j = start; j <= end; j++) {
                                            tempc = in.charAt(j);
                                            if ((tempc >= 65) && (tempc <= 90)) {
                                                tempc += 32;
                                            }
                                            phrase.append(tempc);
                                        }
                                        if (weightFactor == 1) {
                                            if (phraseMap.containsKey(phrase.toString())) {
                                                phraseMap.put(phrase.toString(), phraseMap.get(phrase.toString()) + 10L);
                                            } else {
                                                phraseMap.put(phrase.toString(), 10L);
                                            }
                                        } else {
                                            if (phraseMap.containsKey(phrase.toString())) {
                                                phraseMap.put(phrase.toString(), phraseMap.get(phrase.toString()) + 1L);
                                            } else {
                                                phraseMap.put(phrase.toString(), 1L);
                                            }
                                        }
                                    }
                                    state = 0;
                                }
                                break;
                            }
                        }
                    }
                    if (state == 4) {
                        if (constructPhrase(startSubscript, endSubscript, phraseLength)) {
                            StringBuilder phrase = new StringBuilder();
                            int start = phraseInfo.getStartSubscript();
                            int end = phraseInfo.getEndSubscript();
                            char tempc;
                            for (int j = start; j <= end; j++) {
                                tempc = in.charAt(j);
                                if ((tempc >= 65) && (tempc <= 90)) {
                                    tempc += 32;
                                }
                                phrase.append(tempc);
                            }
                            if (weightFactor == 1) {
                                if (phraseMap.containsKey(phrase.toString())) {
                                    phraseMap.put(phrase.toString(), phraseMap.get(phrase.toString()) + 10L);
                                } else {
                                    phraseMap.put(phrase.toString(), 10L);
                                }
                            } else {
                                if (phraseMap.containsKey(phrase.toString())) {
                                    phraseMap.put(phrase.toString(), phraseMap.get(phrase.toString()) + 1L);
                                } else {
                                    phraseMap.put(phrase.toString(), 1L);
                                }
                            }
                        }
                    }
                } else {
                    if (in.contains("Abstract: ")) {
                        wordsDeque.clear();
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
                                        startSubscript = i;
                                        state = 1;
                                    }
                                    break;
                                }
                                case 1: {
                                    if ((temp >= 97) && (temp <= 122)) {
                                        state = 2;
                                    } else {
                                        wordsDeque.clear();
                                        state = 0;
                                    }
                                    break;
                                }
                                case 2: {
                                    if ((temp >= 97) && (temp <= 122)) {
                                        state = 3;
                                    } else {
                                        wordsDeque.clear();
                                        state = 0;
                                    }
                                    break;
                                }
                                case 3: {
                                    if ((temp >= 97) && (temp <= 122)) {
                                        endSubscript = i;
                                        state = 4;
                                    } else {
                                        wordsDeque.clear();
                                        state = 0;
                                    }
                                    break;
                                }
                                case 4: {
                                    if (((temp >= 97) && (temp <= 122)) || ((temp >= '0') && (temp <= '9'))) {
                                        endSubscript = i;
                                    } else {
                                        if (constructPhrase(startSubscript, endSubscript, phraseLength)) {
                                            StringBuilder phrase = new StringBuilder();
                                            int start = phraseInfo.getStartSubscript();
                                            int end = phraseInfo.getEndSubscript();
                                            char tempc;
                                            for (int j = start; j <= end; j++) {
                                                tempc = in.charAt(j);
                                                if ((tempc >= 65) && (tempc <= 90)) {
                                                    tempc += 32;
                                                }
                                                phrase.append(tempc);
                                            }
                                            if (phraseMap.containsKey(phrase.toString())) {
                                                phraseMap.put(phrase.toString(), phraseMap.get(phrase.toString()) + 1L);
                                            } else {
                                                phraseMap.put(phrase.toString(), 1L);
                                            }
                                        }
                                        state = 0;
                                    }
                                    break;
                                }
                            }
                        }
                        if (state == 4) {
                            if (constructPhrase(startSubscript, endSubscript, phraseLength)) {
                                StringBuilder phrase = new StringBuilder();
                                int start = phraseInfo.getStartSubscript();
                                int end = phraseInfo.getEndSubscript();
                                char tempc;
                                for (int j = start; j <= end; j++) {
                                    tempc = in.charAt(j);
                                    if ((tempc >= 65) && (tempc <= 90)) {
                                        tempc += 32;
                                    }
                                    phrase.append(tempc);
                                }
                                if (phraseMap.containsKey(phrase.toString())) {
                                    phraseMap.put(phrase.toString(), phraseMap.get(phrase.toString()) + 1L);
                                } else {
                                    phraseMap.put(phrase.toString(), 1L);
                                }
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
        return phraseMap;
    }

    /**
     * 求频率最高的n个单词
     *
     * @param phraseMap 各单词词频
     * @return 频率最高的n个单词
     */
    public static ArrayList<HashMap.Entry<String, Long>> topFrequentPhrases(HashMap<String, Long> phraseMap) {
        //将Map转换为ArrayList
        ArrayList<HashMap.Entry<String, Long>> phraseList =
                new ArrayList<HashMap.Entry<String, Long>>(phraseMap.entrySet());
        sort(phraseList);
        return phraseList;
    }

    private static boolean constructPhrase(int startSubscript, int endSubscript, int phraseLength) {
        WordInfo wordInfo = new WordInfo();
        wordInfo.setStartSubscript(startSubscript);
        wordInfo.setEndSubscript(endSubscript);

        wordsDeque.addLast(wordInfo);
        if (wordsDeque.size() == phraseLength) {
            phraseInfo.setStartSubscript(wordsDeque.getFirst().getStartSubscript());
            phraseInfo.setEndSubscript(wordsDeque.getLast().getEndSubscript());
            wordsDeque.removeFirst();
            return true;
        }
        return false;
    }

    private static void sort(ArrayList<HashMap.Entry<String, Long>> phraseList) {
        Collections.sort(phraseList, new Comparator<HashMap.Entry<String, Long>>() {
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
