import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Count {
    private static String content = new String();
    private static String fileOutAddress = new String();
    private static Map ma = new HashMap();
    private static List<HashMap.Entry<String, Integer>> words = null;

    public static List<HashMap.Entry<String, Integer>> getWords() {
        return words;
    }
    public static String getContent() { return content; }
    public static String getFileOutAddress() { return fileOutAddress; }

    public Count(File fileIn , String outAddress) {
        try{
            //读取文件
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(fileIn),"utf8"));

            StringBuffer contents = new StringBuffer();
            int byte_char = -1;
            //开始依次读取字节码
            while ((byte_char = bf.read()) >= 0) {
                contents.append((char)byte_char);
            }
            content = contents.toString();
            fileOutAddress = outAddress;
            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取论文数量
    public  static int getNum(){
        String[] contents = content.split("\r\n\r\n");
        int num = contents.length;
        return num;
    }
    //获取字符数
    public static int charactersCount(){
        String clearcontent = content.replaceAll("\r\n","\n");
        int charactersNum = clearcontent.length();
        //字符数减去不计入的数量
        charactersNum = charactersNum - getNum() * 21 + 2;
        return charactersNum;
    }
    public static int lineCount(){
        Boolean flag = false;
        int linenum = 0;
        int i = 0;
        for (;i<content.length();i++){
            if(content.charAt(i) != '\r' && content.charAt(i) != '\n' && content .charAt(i) != ' ' ){
                flag = true;
            }else if(content.charAt(i) == '\n'){
                if(flag){
                    linenum++;
                    flag = false;
                }
            }
        }
        if(flag){
            linenum++;
        }
        linenum = linenum - getNum();
        return linenum;
    }
    //单词统计
    public static int wordCount(String con ,int aNum){
        int weightT = 0;
        if(con == "weight1"){
            weightT = 10;
        }else {
            weightT = 1;
        }
        int wordNum = 0;
        String[] contents = content.split("\r\n\r\n");
        int i = 0;
        for (;i < contents.length;i++){
            String[] conts = contents[i].split("\r\n");
            String title = conts[1].trim().substring(7);
//            System.out.println(title);
            wordNum =  strCount(wordNum,weightT,title,aNum);
            String abs   = conts[2].trim().substring(10);
            wordNum = strCount(wordNum,1,abs,aNum);
        }
//        System.out.println(wordNum);
        if(!ma.isEmpty()) words = Sort(ma);
        return wordNum;
    }
    public static int strCount(int wordNum,int weight,String str,int aNum){
        String regex = "[^0-9A-Za-z]";
        String[] contns = null;
        String contentString = str.toLowerCase().replaceAll(regex, "|");
        contns = contentString.split("\\|");
        int i = 0;
        int[] wordnumS  = new int[500];
        int wnum = 0;
        //单词切割后进行判断单词
        for (; i < contns.length; i++) {
            if (contns[i].length() >= 4) {
                if (Character.isLetter(contns[i].charAt(0))) {
                    if (Character.isLetter(contns[i].charAt(1))) {
                        if (Character.isLetter(contns[i].charAt(2))) {
                            if (Character.isLetter(contns[i].charAt(3))) {
                                wordNum++;
                                if(aNum == 0){
                                    ma = Maps(ma,contns[i],weight);
                                }else {
                                    wordnumS[wnum] = i;
                                    wnum++;
                                }
                            }
                        }
                    }
                }
            }
        }
        Boolean flag = false;
        if(aNum > 0){
            if(aNum <=  wnum){
                for(int j = 0;j < wnum + 1 - aNum;j++){
                    //0到1
                    flag = false;
                    String s = new String();
                    for (int k = j ; k < j + aNum; k++ ){
                        if(wordnumS[k] ==  wordnumS[j] + k - j){
//                            s +=  contns[wordnumS[j]];
                            s +=  contns[wordnumS[k]];
                            if(k != j + aNum -1) {
                                s += "[\\w\\W]";
                            }else {
                                flag = true;
                            }
                        }
                    }
                    if (flag) {
                        Pattern r = Pattern.compile(s);
                        Matcher m = r.matcher(str.toLowerCase());
                        if(m.find()) {
                            ma = Maps(ma , m.group() , weight);
                        }
                    }
                }
            }
        }

        return wordNum;

    }
    //输出
    public static void getResult(String type,int aNum,int oNum) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(getFileOutAddress());

        //获取字符数
        int charactersNum = charactersCount();

        //获取单词
        int wordNum = wordCount(type,aNum);

        //获取有效行数
        int lineNum = lineCount();

        List<HashMap.Entry<String, Integer>> m = getWords();

        String result = "characters:" + charactersNum + "\r\n" +
                "words:" + wordNum      + "\r\n" +
                "lines:" + lineNum      + "\r\n" ;
//        System.out.println(m.get(0).getKey());
        int j = 0;
        String t = new String();
        if(m != null){
            if(m.size()!=0){
                for(;((j < oNum)&&(j<m.size()));j++){
                    t = "<"+ m.get(j).getKey() + ">:" + m.get(j).getValue();
                    result += t + "\r\n";
                }
            }
        }
        fileOut.write(result.getBytes());
        fileOut.close();

    }
    //更新字典的函数
    public static Map Maps(Map m, String s,int weight){
        if(m.containsKey(s)){
            int n = (int)m.get(s);
            n = n + weight;
            m.put(s,n);
        }else{
            m.put(s,weight);
        }
        return m;
    }
    //单词排序
    public static List<HashMap.Entry<String, Integer>> Sort(Map m){
        List<HashMap.Entry<String, Integer>> wordList = new ArrayList<HashMap.Entry<String, Integer>>(m.entrySet());

        Comparator<Map.Entry<String, Integer>> com = new Comparator<Map.Entry<String, Integer>>(){

            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if(o1.getValue()==o2.getValue())
                    return o1.getKey().compareTo(o2.getKey());//字典序
                return o2.getValue()-o1.getValue();//从大到小
            }
        };
        wordList.sort(com);
        return wordList;
    }


}
