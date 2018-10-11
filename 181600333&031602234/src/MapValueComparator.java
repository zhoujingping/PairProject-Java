import java.util.Comparator;
import java.util.Map;

public class MapValueComparator implements Comparator<Map.Entry<String, String>> {

    @Override
    /*负整数:当前对象的值 < 比较对象的值 ， 位置排在前
    * 零:当前对象的值 = 比较对象的值 ， 位置不变
    *正整数:当前对象的值 > 比较对象的值 ， 位置排在后
    */
    public int compare(Map.Entry<String, String> me1, Map.Entry<String, String> me2) {
        int flag=0;
        if(Long.parseLong(me2.getValue()) > Long.parseLong(me1.getValue())){
            flag=1;
        }else if(Long.parseLong(me1.getValue()) > Long.parseLong(me2.getValue())){
            flag=-1;
        }else if(Long.parseLong(me1.getValue()) == Long.parseLong(me2.getValue())){
            flag=me1.getKey().compareTo(me2.getKey());
        }

        return flag;
    }
}