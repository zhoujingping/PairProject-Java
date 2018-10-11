package team.onebeltoneroad.wordCount.dataprocess.entity;

public class WordCounterInfo {
    private String inputFile = "input.txt";
    private String outputFile = "output.txt";
    private int weightFactor = 1;
    private int phraseLength = -1;
    private int wordFrequencyOutNum = 10;

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public int getWeightFactor() {
        return weightFactor;
    }

    public void setWeightFactor(int weightFactor) {
        this.weightFactor = weightFactor;
    }

    public int getPhraseLength() {
        return phraseLength;
    }

    public void setPhraseLength(int phraseLength) {
        this.phraseLength = phraseLength;
    }

    public int getWordFrequencyOutNum() {
        return wordFrequencyOutNum;
    }

    public void setWordFrequencyOutNum(int wordFrequencyOutNum) {
        this.wordFrequencyOutNum = wordFrequencyOutNum;
    }
}