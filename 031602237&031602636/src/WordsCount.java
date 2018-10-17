import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordsCount {


    private HashMap<String, Integer> map = new HashMap<>();
    
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
        map = mergeMap(titlesMap, abstractsMap, w);

    }
    /**
     * @param contents the List contents titles or abstracts
     * @param m the number of words
     * @return the map contains word and times
     */
    private HashMap<String, Integer> countContent(List<String> contents, int m) {
        HashMap<String, Integer> map = new HashMap<>();
        String splitRegex = "[\\s+\\p{Punct}]+";
        String splitStartRegex = "^[\\s+\\p{Punct}]+";
        String wordRegex = "^[a-zA-Z]{4,}.*";
        Pattern pattern = Pattern.compile(splitRegex);

        for (String content : contents) {
            String[] temp = content.split(splitRegex);

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
                } else {
                    continue;
                }
                boolean flag = true;
                for (int j = 1; j < m; j++) {
                    if (!temp[i + j].matches(wordRegex)) {
                        flag = false;
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

                if (flag) {
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

    /**
     * @param 
     * @return 前n多
     */
    public static List<Map.Entry<String, Integer>> mostWords(HashMap<String, Integer> map, int n) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(new MapComparator());
        return list.size() < n ? list.subList(0, list.size()) : list.subList(0, n);
    }


    /**
     * 比较大小
     */
    private static class MapComparator implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        	int cmp = o1.getValue() - o2.getValue();
        	return (cmp==0?o1.getKey().compareTo(o2.getKey()):-cmp);
        }
    }
    
}
