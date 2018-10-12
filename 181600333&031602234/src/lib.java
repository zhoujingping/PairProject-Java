/*
import com.siberia.demo.MapValueComparator;
*/

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class lib {
    /*论文编号及其紧跟着的换行符、分隔论文的两个换行符、“Title: ”、“Abstract: ”（英文冒号，后有一个空格）均不纳入考虑范围*/
    /*统计字符数(已完成)*/
    public static long countChar(String filePath) {
        long characters = 0;
        try {
            String encoding = "UTF-8";
            File file = new File(filePath);
            String sb = null;
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                /*读取文件数据*/
                BufferedReader br = new BufferedReader(new FileReader(file));
                while (br.readLine() != null) {
                    try {
                        while ((sb = br.readLine()) != null) {
                            if (sb.length() == 0) {
                                break;
                            }
                            characters += (sb.length() + 1);
                        }
                        characters -= 17;
                        br.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            read.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return characters;
    }

    /*统计单词数(已完成)*/
    public static long countWord(String pathname)   {
        long words = 0;
        try {
            String encoding = "UTF-8";
            File file = new File(pathname);
            if (file.isFile() && file.exists()) {
                StringBuilder sb = new StringBuilder();
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                /*读取文件数据*/
                BufferedReader br = new BufferedReader(new FileReader(file));
                while (br.readLine() != null) {
                    String temp = null;
                    try {
                        while ((temp = br.readLine()) != null) {
                            if (temp.length() == 0) {
                                break;
                            }
                            sb.append(temp);
                            sb.append(" ");//每行结束多读一个空格
                        }
                        words -= 2;
                        br.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            read.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                String info = sb.toString();
                String s[] = info.split("[^a-zA-Z0-9]");
                /*统计单词个数*/
                for (String value : s) {
                    if (value.length() >= 4) {
                        String temp = value.substring(0, 4);
                        temp = temp.replaceAll("[^a-zA-Z]", "");

                        if (temp.length() >= 4) {
                            words++;
                        }
                    }
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return words;
    }

    /*单词\词组词频统计功能(已完成)*/
    public static Map<String, String> countPhraseFrequency(String pathname, long phraseLength,boolean cntByWeight) {
        Map<String, String> map = new HashMap<String, String>();
        boolean flag = false;
        try {
            String encoding = "UTF-8";
            File file = new File(pathname);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                /*读取文件数据*/
                StringBuffer sb = null;
                BufferedReader br1;
                try {
                    br1 = new BufferedReader(new FileReader(file));
                    String temp = br1.readLine();
                    sb = new StringBuffer();
                    while (temp != null) {
                        sb.append(temp);
                        sb.append(" ");//每行结束多读一个空格
                        temp = br1.readLine();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /*读取的内容*/
                String info = null;
                if (sb != null) {
                    info = sb.toString();
                }
                /*保留分隔符*/
                Pattern p =Pattern.compile("[^a-zA-Z0-9]");
                Matcher m = null;
                String s[] = new String[0];
                if (info != null) {
                    m = p.matcher(info);
                    s = info.split("[^a-zA-Z0-9]");
                }
                if(s.length > 0)
                {
                    int count = 0;
                    while(count < s.length)
                    {
                        if(m.find())
                        {
                            s[count] += m.group();
                        }
                        count++;
                    }
                }
                s = replaceNull(s);
                /*统计单词个数*/
                for (int i = 0; i < s.length; i++) {
                    StringBuilder content = new StringBuilder();
                    int cnt=0;
                    for (int j = i; cnt<phraseLength&&j<s.length; j++) {
                        if (s[j].length() >= 4 && !s[j].toLowerCase().equals("title:") && !s[j].toLowerCase().equals("abstract:")) {
                            String temp = s[j].substring(0, 4);
                            temp = temp.replaceAll("[^a-zA-Z]", "");
                            if (temp.length() >= 4) {
                                cnt++;
                                if(cnt==phraseLength){
                                    content.append(s[j].substring(0,s[j].length()-1));
                                }else{
                                    content.append(s[j]);
                                }
                            } else {
                                break;
                            }
                        } else if (s[j].toLowerCase().equals("title:")) {
                            flag = true;
                            break;
                        } else if (s[j].toLowerCase().equals("abstract:")) {
                            flag = false;
                            break;
                        } else if(s[j].matches("[^a-zA-Z0-9]")&&cnt>=1){
                            content.append(s[j]);
                        }else{
                            break;
                        }
                        if (cnt==phraseLength) {
                            String phrase = content.toString();
                            if (flag && cntByWeight) {
                                if (map.containsKey(phrase.toLowerCase())) {//判断Map集合对象中是否包含指定的键名
                                    map.put(phrase.toLowerCase(), Integer.parseInt(map.get(phrase.toLowerCase())) + 10 + "");
                                } else {
                                    map.put(phrase.toLowerCase(), 10 + "");
                                }
                            } else {
                                if (map.containsKey(phrase.toLowerCase())) {//判断Map集合对象中是否包含指定的键名
                                    map.put(phrase.toLowerCase(), Integer.parseInt(map.get(phrase.toLowerCase())) + 1 + "");
                                } else {
                                    map.put(phrase.toLowerCase(), 1 + "");
                                }
                            }
                        }
                    }
                }
                /*map排序*/
                map = sortMapByValue(map);
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return map;
    }

    /*统计行数(已完成)*/
    public static long countLines(String filePath) {
        long lines = 0;
        try {
            String encoding = "UTF-8";
            String temp = null;
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                /*读取文件数据*/
                BufferedReader br = new BufferedReader(new FileReader(file));
                while (br.readLine() != null) {
                    try {
                        while ((temp = br.readLine()) != null) {
                            if (temp.length() == 0) {
                                break;
                            }
                            lines++;
                        }
                        br.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            read.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return lines;

    }

    /*生成新txt(已完成)*/
    public static void printFile(long characters, long words, long lines, Map<String, String> map, String outPathname, int needNum) {
        StringBuilder content = new StringBuilder("");
        int count = 0;
        while (count != 3) {
            switch (count) {
                case 0:
                    content.append("characters:");
                    content.append(characters);
                    break;
                case 1:
                    content.append("words:");
                    content.append(words);
                    break;
                case 2:
                    content.append("lines:");
                    content.append(lines);
                    break;
            }
            count++;
            content.append("\r\n");
        }
        if(!(map==null || map.size()<1))
        {
            Set<String> keys = map.keySet();
            count = 1;
            for (String key : keys) {
                content.append("<").append(key).append(">:").append(map.get(key));
                count++;
                if (count > needNum)
                    break;
                content.append("\r\n");
            }
        }
        System.out.println(content);
        BufferedWriter bw;
        try {
            File file = new File(outPathname);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            bw = new BufferedWriter(fw);
            bw.write(content.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*map用value排序(已完成)*/
    public static Map<String, String> sortMapByValue(Map<String, String> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<String, String> sortedMap = new LinkedHashMap<String, String>();
        List<Map.Entry<String, String>> entryList = new ArrayList<Map.Entry<String, String>>(oriMap.entrySet());
        entryList.sort(new MapValueComparator());

        Iterator<Map.Entry<String, String>> iter = entryList.iterator();
        Map.Entry<String, String> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }

    /*去除数组中所有空值*/
    public static String[] replaceNull(String[] str) {
        //用StringBuffer来存放数组中的非空元素，用“;”分隔
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length; i++) {
            if ("".equals(str[i])) {
                continue;
            }
            sb.append(str[i]);
            if (i != str.length - 1) {
                sb.append(";");
            }
        }
        //用String的split方法分割，得到数组
        str = sb.toString().split(";");
        return str;
    }
}

