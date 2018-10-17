import java.io.*;
import java.util.List;
import java.util.Map;

public class Main {
    /* 输入文件 */
    static String inputFile = null;
    /* 输出文件 */
    static String outputFile = null;
    /* 权重 */
    static int weight = -1;
    /* 词组长度 */
    static int phraseLength = -1;
    /* 词频 */
    static int frequencyNumber = -1;

    /**
     * @param args
     */
    public static void main(String[] args) {
        int len;
        if ((len = args.length) != 0) {
            for (int i = 0; i < len; i += 2) {
                if (i + 1 == len) {
                    System.out.printf("error\n");
                    System.exit(1);
                }
                switch (args[i]) {
                    case "-i":
                        inputFile = args[i + 1];
                        break;
                    case "-o":
                        outputFile = args[i + 1];
                        break;
                    case "-w":
                        weight = Integer.parseInt(args[i + 1]);
                        break;
                    case "-m":
                        phraseLength = Integer.parseInt(args[i + 1]);
                        break;
                    case "-n":
                        frequencyNumber = Integer.parseInt(args[i + 1]);
                        break;
                    default:
                        System.out.println("error:输入有误");
                        System.exit(1);
                        break;
                }
            }
        } else {
            System.out.println("error");
            System.exit(1);
        }
        try {
            /* 读取论文文件 */
            String paper = readFile(inputFile);
            lib l = new lib(paper);
            /* 字符个数 */
            int charCount = l.charCount();
            /* 行数 */
            int lineCount = l.lineCount();
            /* 单词个数 */
            int wordCount = l.wordCount();

            List<Map.Entry<String, Integer>> list = null;
            /* 没有-m -n参数都不出现 */
            if (frequencyNumber == -1 && phraseLength == -1) {
                list = l.wordList(weight);
                /* 只出现-n参数 */
            } else if (frequencyNumber != -1 && phraseLength == -1) {
                list = l.wordList(weight, frequencyNumber);
                /* 出现-m参数 */
            } else {
                list = l.phraseList(weight, phraseLength);
            }

            writeFileAndPrint(charCount, lineCount, wordCount, list, outputFile);

        } catch (IOException e) {
            System.out.println("IOException:" + e.getMessage());
        }
    }

    /**
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String readFile(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath),"UTF-8"));
        int charTemp;

        StringBuilder stringBuilder = new StringBuilder();

        while ((charTemp = bufferedReader.read()) != -1) {
            stringBuilder.append((char) charTemp);
        }
        bufferedReader.close();

        //String string=new String(stringBuilder.toString().getBytes(),"UTF-8");
        //return string;
        return stringBuilder.toString();
    }

    /**
     * @param charCount
     * @param lineCount
     * @param wordCount
     * @param list
     * @param outputFile
     * @throws IOException
     */
    public static void writeFileAndPrint(int charCount, int lineCount,
                                         int wordCount, List<Map.Entry<String, Integer>> list, String outputFile) throws IOException {
        File outFile = new File(outputFile);
        //rintWriter printWriter = new PrintWriter(new FileWriter(outFile));
        PrintWriter printWriter=new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
        printWriter.println("characters: " + charCount);
        printWriter.println("words: " + wordCount);
        printWriter.println("lines: " + lineCount);

        for (Map.Entry<String, Integer> l : list) {
            printWriter.println("<" + l.getKey() + ">: " + l.getValue());
        }
        printWriter.flush();
        printWriter.close();
    }
}
