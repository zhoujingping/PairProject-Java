package elementCounter;
import java.io.*;
import java.util.*;
public class wordFrequencyCounter {
	public static HashMap<String, Integer> countWordsFrequency(String fileName,int type ) {
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        int in = 0;
        char temp;
        int state = 0;
        int magnification = 1;
        String str = null;
        StringBuilder word = new StringBuilder();
        HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
        
        //读入文件
        try {
            inputStreamReader = new InputStreamReader(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("找不到此文件");
            e.printStackTrace();
        }
        if (inputStreamReader != null) {
            bufferedReader = new BufferedReader(inputStreamReader);
        }
        //计算单词词频
        try {
            while ((str = bufferedReader.readLine()) != null) {
            	state = 0;
            	magnification = 1;
            	if (str.length() > 0) {
            		int i = 0;
            		
                	if (str.charAt(0) == 'T') {
    					i += 7;
    					if (type == 1) {
    						magnification = 10;
    					}
    				}
                	else if (str.charAt(0) == 'A') {
    					i += 10;
    				}
                	
                	for(;i < str.length();i++) {
                		//大写字母转为小写字母
                		temp = str.charAt(i);
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
                                        wordMap.put(word.toString(), wordMap.get(word.toString()) + magnification);
                                    } else {
                                        wordMap.put(word.toString(), magnification);
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
                            wordMap.put(word.toString(), wordMap.get(word.toString()) + magnification);
                        } else {
                            wordMap.put(word.toString(), magnification);
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
     * 求频率最高的10个单词
     *
     * @param wordMap 各单词词频
     * @return 频率最高的10个单词
     */
    public static ArrayList<HashMap.Entry<String, Integer>> topFrequentWords(HashMap<String, Integer> wordMap) {
        //将Map转换为ArrayList
        ArrayList<HashMap.Entry<String, Integer>> wordList =
                new ArrayList<HashMap.Entry<String, Integer>>(wordMap.entrySet());
        sort(wordList);
        return wordList;
    }

    private static void sort(ArrayList<HashMap.Entry<String, Integer>> wordList) {
        Collections.sort(wordList, new Comparator<HashMap.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
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
