import java.util.*;

public class WordCmp{

    /**
     * @param 
     * @return 前n多
     */
    public List<Map.Entry<String, Integer>> mostWords(HashMap<String, Integer> map, int n) {
        // convert HashMap to list
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        // sort by value then by key
        list.sort(new MapComparator());
        return list.size() < n ? list.subList(0, list.size()) : list.subList(0, n);
    }


    /**
     * 比较大小
     */
    private class MapComparator implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        	int cmp = o1.getValue() - o2.getValue();
        	return (cmp==0?o1.getKey().compareTo(o2.getKey()):-cmp);
        }
    }
    

}
