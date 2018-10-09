import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        boolean cntByWeight=false;
        int needNum=10;
        int phraseLength=1;
        String inPathname = "e:\\test1.txt";
        String outPathname = "e:\\result.txt";
        for(int i=0;i<args.length;i+=2){
            switch(args[i]){
                /*-w 参数设定是否采用不同权重计数*/
                case "-w":
                    if(Integer.parseInt(args[i+1])==1){
                        System.out.println("采用权重计数。");
                        cntByWeight=true;
                    }else{
                        System.out.println("不采用权重计数。");
                        cntByWeight=false;
                    }
                    break;
                /*-i 参数设定读入文件的存储路径*/
                case "-i":
                    inPathname=args[i+1];
                    System.out.println("读入文件路径为"+inPathname+"。");
                    break;
                /*-o 参数设定生成文件的存储路径*/
                case "-o":
                    outPathname=args[i+1];
                    System.out.println("生成文件路径为"+outPathname+"。");
                    break;
                /*-m 参数设定统计的词组长度*/
                /*使用词组词频统计功能时，不再统计单词词频，而是统计词组词频，但不影响单词总数统计*/
                /*未出现 -m 参数时，不启用词组词频统计功能，默认对单词进行词频统计*/
                case "-m":
                    phraseLength=Integer.parseInt(args[i+1]);
                    System.out.println("采用词组词频统计功能，词组长度为"+phraseLength+"。");
                    break;
                /*-n 参数设定输出的单词数量*/
                /*未出现 -n 参数时，不启用自定义词频统计输出功能，默认输出10个*/
                case "-n":
                    needNum=Integer.parseInt(args[i+1]);
                    System.out.println("输出的单词/词组数量为"+needNum+"。");
                    break;

            }
        }
        Map<String, String> map = new HashMap<String, String>();
        ExecutorService executor = Executors.newCachedThreadPool();
        //计算字符数
        String finalInPathname = inPathname;
        Future<Long> futureChar = executor.submit(() -> lib.countChar(finalInPathname));
        //计算单词数
        Future<Long> futureWord = executor.submit(() -> lib.countWord(finalInPathname));
        //计算行数
        Future<Long> futureLine = executor.submit(() -> lib.countLines(finalInPathname));
        //词频统计
        map = lib.countPhraseFrequency(inPathname,phraseLength,cntByWeight);
        /*输出结果*/
        try {
            lib.printFile(futureChar.get(), futureWord.get(),  futureLine.get(), map,outPathname,needNum);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("completed");
    }
}
