package team.onebeltoneroad.wordCount;

import team.onebeltoneroad.wordCount.dataprocess.entity.WordCounterInfo;
import team.onebeltoneroad.wordCount.dataprocess.service.*;
import team.onebeltoneroad.wordCount.dataprocess.tool.CommandParser;
import team.onebeltoneroad.wordCount.dataprocess.tool.FilePrinter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * 主函数类，包括提交计数任务、打印结果.
 *
 * @author xyy
 * @version 2.0 2018/10/12
 * @since 2018/9/11
 */
public class Main {
    public static void main(final String[] args) {

        final WordCounterInfo wordCounterInfo = new WordCounterInfo();
        CommandParser.parseCommadLine(args, wordCounterInfo);

        ExecutorService executor = Executors.newCachedThreadPool();

        //计算字符数
        Future<Long> futureChar = executor.submit(new Callable<Long>() {
            public Long call() {
                return CharCounter.countChar(wordCounterInfo.getInputFile());
            }
        });

        //计算单词数
        Future<Long> futureWord = executor.submit(new Callable<Long>() {
            public Long call() {
                return WordCounter.countWord(wordCounterInfo.getInputFile());
            }
        });

        //计算行数
        Future<Long> futureLine = executor.submit(new Callable<Long>() {
            public Long call() {
                return LineCounter.countLine(wordCounterInfo.getInputFile());
            }
        });

        //计算单词词频
        if (wordCounterInfo.getPhraseLength() == -1) {
            Future<ArrayList<HashMap.Entry<String, Long>>> futureWordFrequnency = executor.submit(
                    new Callable<ArrayList<HashMap.Entry<String, Long>>>() {
                        public ArrayList<HashMap.Entry<String, Long>> call() {
                            return WordsFrequencyCounter.topFrequentWords(
                                    WordsFrequencyCounter.countWordsFrequency(
                                            wordCounterInfo.getInputFile(), wordCounterInfo.getWeightFactor()));
                        }
                    });

            //输出至文件
            try {
                FilePrinter.printToFile(wordCounterInfo.getOutputFile(), futureChar.get(),
                        futureWord.get(), futureLine.get(), futureWordFrequnency.get(), wordCounterInfo);
                executor.shutdown();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //计算词组词频
        else {
            Future<ArrayList<HashMap.Entry<String, Long>>> futurePhraseFrequnency = executor.submit(
                    new Callable<ArrayList<HashMap.Entry<String, Long>>>() {
                        public ArrayList<HashMap.Entry<String, Long>> call() {
                            return PhraseFrequencyCounter.topFrequentPhrases(
                                    PhraseFrequencyCounter.countPhraseFrequency(
                                            wordCounterInfo.getInputFile(), wordCounterInfo.getWeightFactor(), wordCounterInfo.getPhraseLength()));
                        }
                    });

            //输出至文件
            try {
                FilePrinter.printToFile(wordCounterInfo.getOutputFile(), futureChar.get(),
                        futureWord.get(), futureLine.get(), futurePhraseFrequnency.get(), wordCounterInfo);
                executor.shutdown();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
