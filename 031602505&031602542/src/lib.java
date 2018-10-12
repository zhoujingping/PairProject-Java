
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class lib {
    private String paper;//文本内容

    public lib(String arg0) {
        this.paper = arg0;
    }

    /* 统计行数 */
    public int lineCount() {
        Pattern pattern = Pattern.compile("(Title: |Abstract: )([^\n]*)");
        Matcher matcher = pattern.matcher(paper);
        int count = 0;
        while (matcher.find()) {
            count += 1;
        }
        return count;
    }

    /* 统计字符个数 */
    public int charCount() {
        Pattern pattern = Pattern.compile("(Title: |Abstract: )([^\n]*)");
        Matcher matcher = pattern.matcher(paper);
        int count = 0;
        try{
            while (matcher.find()) {
                count += matcher.group(2).getBytes("utf-8").length;
                //count+=matcher.group(2).length();
            }
        }catch (UnsupportedEncodingException e){

        }
        return count;
    }

    /* 统计单词个数 */
    public int wordCount() {
        Pattern pattern = Pattern.compile("(Title: |Abstract: )([^\n]*)");
        Matcher matcher = pattern.matcher(paper);
        int count = 0;
        String[] words;
        while (matcher.find()) {
            words = matcher.group(2).split("[^A-Za-z0-9]+");
            for (String word : words) {
                if (word.matches("[a-zA-Z]{4}[a-zA-Z0-9]*")) {
                    count++;
                }
            }
        }
        return count;
    }

    /* 统计单词权数 */
    public HashMap<String, Integer> wordMap(int weight) {
        /* 单词权重设置，默认title单词为1 */
        int wordWeight = 1;
        if (weight == 1) wordWeight = 10;

        HashMap<String, Integer> wordFrequency = new HashMap<String, Integer>();
        /* 在Title里的单词
         * 字符串格式Title:  \r\n
         */
        Pattern titlePattern = Pattern.compile("(Title: )([^\n]*)");
        Matcher matcher = titlePattern.matcher(paper);
        String content;
        String[] words;

        /* 匹配Title字符串里的单词 */
        while ((matcher.find())) {
            content = matcher.group(2);
            words = content.split("[^A-Za-z0-9]+");
            for (String word : words) {
                if (word.matches("[a-zA-Z]{4}[a-zA-Z0-9]*")) {
                    word = word.toLowerCase();
                    if (wordFrequency.containsKey(word)) {
                        int temp = wordFrequency.get(word) / wordWeight;
                        wordFrequency.put(word, (temp + 1) * wordWeight);
                    } else {
                        wordFrequency.put(word, wordWeight);
                    }
                }
            }
        }

        /* 匹配Abstract字符串里的单词 */
        Pattern abstractPattern = Pattern.compile("(Abstract: )([^\n]*)");
        matcher = abstractPattern.matcher(paper);
        while (matcher.find()) {
            content = matcher.group(2);
            words = content.split("[^A-Za-z0-9]+");
            for (String word : words) {
                if (word.matches("[a-zA-Z]{4}[a-zA-Z0-9]*")) {
                    word = word.toLowerCase();
                    if (wordFrequency.containsKey(word)) {
                        wordFrequency.put(word, wordFrequency.get(word) + 1);
                    } else {
                        wordFrequency.put(word, 1);
                    }
                }
            }
        }
        //this.words=wordFrequency;
        return wordFrequency;
    }

    /**
     * @param weight
     * @return
     */
    public List<Entry<String, Integer>> wordList(int weight) {
        HashMap<String, Integer> words = wordMap(weight);
        List<Entry<String, Integer>> wordlist = new ArrayList<>(words.entrySet());
        Comparator<Entry<String, Integer>> com = new Comparator<Entry<String, Integer>>() {
            @Override
            public int compare(Entry<String, Integer> arg0, Entry<String, Integer> arg1) {
                // TODO Auto-generated method stub
                if (!arg0.getValue().equals(arg1.getValue())) {
                    return (arg1.getValue().compareTo(arg0.getValue()));
                } else {
                    return (arg0.getKey().compareTo(arg1.getKey()));
                }
            }
        };
        Collections.sort(wordlist, com);
        int size = wordlist.size();
        return wordlist.subList(0, 10 > size ? size : 10);
    }

    /**
     * 词频在n之前的单词
     */
    public List<Entry<String, Integer>> wordList(int weight, int n) {
        HashMap<String, Integer> words = wordMap(weight);
        List<Entry<String, Integer>> wordList =
                new ArrayList<Map.Entry<String, Integer>>(words.entrySet());
        Comparator<Entry<String, Integer>> com = new Comparator<Entry<String, Integer>>() {
            @Override
            public int compare(Entry<String, Integer> arg0, Entry<String, Integer> arg1) {
                // TODO Auto-generated method stub
                if (!arg0.getValue().equals(arg1.getValue())) {
                    return (arg1.getValue().compareTo(arg0.getValue()));
                } else {
                    return (arg0.getKey().compareTo(arg1.getKey()));
                }
            }
        };
        Collections.sort(wordList, com);
        int size = wordList.size();
        if (n > size) {
            return wordList.subList(0, size);
        } else {
            return wordList.subList(0, n);
        }
    }

    public HashMap<String, Integer> phraseMap(int weight, int m) {
        /* 单词权重设置，默认title单词为1 */
        int wordWeight = 1;
        if (weight == 1) wordWeight = 10;

        HashMap<String, Integer> list = new HashMap<>();

        Pattern titlePattern = Pattern.compile("(Title: )([^\n]*)");
        Matcher matcher = titlePattern.matcher(paper);
        String content="";
        String[] words;
        String[] signs;

        int length=0;
        boolean isPhrase = false;
        while (matcher.find()) {
            content = matcher.group(2);
            words = content.split("[^A-Za-z0-9]+");
            signs=content.split("[A-Za-z0-9]+");
            String phrase = "";
            length = words.length;
            for (int i = 0; (i + m) <= length; i++) {
                isPhrase = true;
                for (int j = i; j < i + m; j++) {
                    if (!words[j].matches("[a-zA-Z]{4}[a-zA-Z0-9]*")) {
                        i = j;
                        phrase = "";
                        isPhrase = false;
                        break;
                    }
                    if (j == i + m - 1) {
                        phrase += words[j];
                    } else {
                        phrase += (words[j] + signs[j+1]);
                    }
                }
                if (isPhrase) {
                    phrase = phrase.toLowerCase();
                    if (list.containsKey(phrase)) {
                        int values = list.get(phrase) / wordWeight;
                        list.put(phrase, (values + 1) * wordWeight);
                    } else {
                        list.put(phrase, wordWeight);
                    }
                    phrase = "";
                }
            }
        }
        /* 匹配Abstract字符串里的单词 */
        Pattern abstractPattern = Pattern.compile("(Abstract: )([^\n]*)");
        matcher = abstractPattern.matcher(paper);

        while (matcher.find()) {
            //content = matcher.group(2);
            try{
                content=new String(matcher.group(2).getBytes("UTF-8"));
            }catch (UnsupportedEncodingException e){

            }
            words = content.split("[^A-Za-z0-9]+");
            signs=content.split("[A-Za-z0-9]+");
            String phrase = "";
            length = words.length;
            for (int i = 0; (i + m) <= length; i++) {
                for (int j = i; j < m + i; j++) {
                    isPhrase = true;
                    if (!words[j].matches("[a-zA-Z]{4}[a-zA-Z0-9]*")) {
                        i = j;
                        phrase = "";
                        isPhrase = false;
                        break;
                    }
                    if (j == i + m - 1) {
                        phrase += words[j];
                    } else {
                        phrase += (words[j] + signs[j+1]);
                    }
                }
                if (isPhrase) {
                    phrase = phrase.toLowerCase();
                    if (list.containsKey(phrase)) {
                        list.put(phrase, list.get(phrase) + 1);
                    } else {
                        list.put(phrase, 1);
                    }
                    phrase = "";
                }
            }
        }
        return list;
    }

    public List<Entry<String, Integer>> phraseList(int weight, int m) {
        HashMap<String, Integer> map = phraseMap(weight, m);
        List<Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        Comparator<Entry<String, Integer>> com = new Comparator<Entry<String, Integer>>() {
            @Override
            public int compare(Entry<String, Integer> arg0, Entry<String, Integer> arg1) {
                // TODO Auto-generated method stub
                if (!arg0.getValue().equals(arg1.getValue())) {
                    return (arg1.getValue().compareTo(arg0.getValue()));
                } else {
                    return (arg0.getKey().compareTo(arg1.getKey()));
                }
            }
        };
        Collections.sort(list, com);
        int size = list.size();
        return list.subList(0, 10 > size ? size : 10);
    }

}
