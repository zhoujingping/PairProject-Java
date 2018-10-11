import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordsCount {


    private HashMap<String, Integer> map = new HashMap<>();
    private int sum = 0;


    /**
     * @return the sum of words
     */
    public int getSum() {
        return sum;
    }

    
    /**
     * @return the HashMap contain words and words's sum
     */
    public HashMap<String, Integer> getMap() {
        return map;
    }


    /**
     * @param handleContent the input
     * @param m the number of words
     * @param w if count word with weight
     */
    public WordsCount(HandleContent handleContent, int m, int w) {

        HashMap<String, Integer> titlesMap = countContent(handleContent.getTitles(), m);
        HashMap<String, Integer> abstractsMap = countContent(handleContent.getAbstracts(), m);
        sum = getWordsSum(handleContent.getHandledContent());
        map = mergeMap(titlesMap, abstractsMap, w);

    }


    /**
     * @param content the file content
     * @return the word's number of the content
     */
    private int getWordsSum(String content) {
        int sum = 0;
        String[] temp = content.split("[\\s+\\p{Punct}]+");
        String countRegex = "^[a-zA-Z]{4,}.*";
        for (String i : temp) {
            if (i.matches(countRegex)) {
                sum++;
//                String lowCase = i.toLowerCase();
//                if (!map.containsKey(lowCase)) {
//                    map.put(lowCase, 1);
//                } else {
//                    int num = map.get(lowCase);
//                    map.put(lowCase, num + 1);
//                }
            }
        }
        return sum;
    }


    /**
     * @param contents the List contents titles or abstracts
     * @param m the number of words
     * @return the map contains word and times
     */
    private HashMap<String, Integer> countContent(List<String> contents, int m) {
        HashMap<String, Integer> map = new HashMap<>();
//        String splitRegex = "[\\s+\\p{Punct}]+";
        String splitRegex = "[\\s+\\p{Punct}]+";
        String splitStartRegex = "^[\\s+\\p{Punct}]+";
        String wordRegex = "^[a-zA-Z]{4,}.*";
        Pattern pattern = Pattern.compile(splitRegex);

        for (String content : contents) {
            String[] temp = content.split(splitRegex);
//            Arrays.asList(temp).forEach(System.out::println);

            List<String> splits = new ArrayList<>();
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                splits.add(matcher.group());
            }

            boolean isSplitStart = content.matches(splitStartRegex);

            for (int i = 0; i < temp.length - m + 1; i++) {
                StringBuilder stringBuilder = new StringBuilder();

                if (temp[i].matches(wordRegex)) {
                    stringBuilder.append(temp[i]);
//                    System.out.println(temp[i]);
                } else {
                    continue;
                }
                boolean isContinue = true;
                for (int j = 1; j < m; j++) {
                    if (!temp[i + j].matches(wordRegex)) {
                        isContinue = false;
                        break;
                    } else {
                        if (isSplitStart) {
                            stringBuilder.append(splits.get(i + j));
                        } else {
                            stringBuilder.append(splits.get(i + j - 1));
                        }
                        stringBuilder.append(temp[i + j]);
                    }
                }

                if (isContinue) {
                    String words = stringBuilder.toString().toLowerCase();
                    if (!map.containsKey(words)) {
                        map.put(words, 1);
                    } else {
                        int num = map.get(words);
                        map.put(words, num + 1);
                    }
                }

            }

        }
        return map;
    }


    /**
     * @param titlesMap the titles map
     * @param abstractsMap the abstracts map
     * @param w if count the word with weight
     * @return merged map
     */
    private HashMap<String, Integer> mergeMap(HashMap<String, Integer> titlesMap, HashMap<String, Integer> abstractsMap, int w) {
        int weight = 1;
        if (w == 1) {
            weight = 10;
        }
        HashMap<String, Integer> map = new HashMap<>();
        for (Map.Entry<String, Integer> entry : titlesMap.entrySet()) {
            map.put(entry.getKey(), entry.getValue() * weight);
        }
        for (Map.Entry<String, Integer> entry : abstractsMap.entrySet()) {
            if (!map.containsKey(entry.getKey())) {
                map.put(entry.getKey(), entry.getValue());
            } else {
                int num = map.get(entry.getKey());
                map.put(entry.getKey(), num + entry.getValue());
            }
        }
        return map;
    }

}
