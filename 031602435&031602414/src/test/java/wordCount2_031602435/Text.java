package wordCount2_031602435;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import elementCounter.LineCounter;
import elementCounter.phraseFrequencyCounter;
import elementCounter.wordFrequencyCounter;
import elementCounter.wordNumberCounter;

public class Text {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLineNum1() {
		LineCounter lineCounter  = new LineCounter();
		long linenum =lineCounter.countLine("D:\\java_project\\031602435&031602414\\src\\test\\java\\wordCount2_031602435\\"+"text1.txt");
		System.out.println(linenum);
		assertEquals(2, linenum);
		
	}
	
	@Test
	public void testWordNum1() {
		wordNumberCounter wordNumberCounter = new wordNumberCounter();
		long wordnum =wordNumberCounter.countWord("D:\\java_project\\031602435&031602414\\src\\test\\java\\wordCount2_031602435\\"+"text1.txt");
		System.out.println(wordnum);
		assertEquals(9, wordnum);
		
	}
	
	@Test
	public void testPhraseFrequency1_1() {
		phraseFrequencyCounter phraseFrequencyCounter = new phraseFrequencyCounter();
		int topPhrasenum = 0;
		ArrayList<HashMap.Entry<String, Integer>> wordList = new ArrayList<HashMap.Entry<String, Integer>>();
		HashMap<String, Integer> wordlistMap = new HashMap<String, Integer>();
		wordlistMap = phraseFrequencyCounter.countPhraseFrequency("D:\\java_project\\031602435&031602414\\src\\test\\java\\wordCount2_031602435\\"+"text1.txt",1,3);
		wordList = phraseFrequencyCounter.topFrequentPhrases(wordlistMap);
		topPhrasenum = wordList.get(0).getValue();
		assertEquals(11, topPhrasenum);
		
	}
	
	@Test
	public void testPhraseFrequency1_2() {
		phraseFrequencyCounter phraseFrequencyCounter = new phraseFrequencyCounter();
		int topPhrasenum = 0;
		ArrayList<HashMap.Entry<String, Integer>> wordList = new ArrayList<HashMap.Entry<String, Integer>>();
		HashMap<String, Integer> wordlistMap = new HashMap<String, Integer>();
		wordlistMap = phraseFrequencyCounter.countPhraseFrequency("D:\\java_project\\031602435&031602414\\src\\test\\java\\wordCount2_031602435\\"+"text1.txt",0,3);
		wordList = phraseFrequencyCounter.topFrequentPhrases(wordlistMap);
		topPhrasenum = wordList.get(0).getValue();
		assertEquals(2, topPhrasenum);
		
	}
	
	@Test
	public void testWordFrequency1_1() {
		wordFrequencyCounter wordFrequencyCounter = new wordFrequencyCounter();
		int topWordnum = 0;
		ArrayList<HashMap.Entry<String, Integer>> wordList = new ArrayList<HashMap.Entry<String, Integer>>();
		HashMap<String, Integer> wordlistMap = new HashMap<String, Integer>();
		wordlistMap = wordFrequencyCounter.countWordsFrequency("D:\\java_project\\031602435&031602414\\src\\test\\java\\wordCount2_031602435\\"+"text1.txt",1);
		wordList = wordFrequencyCounter.topFrequentWords(wordlistMap);
		topWordnum = wordList.get(0).getValue();
		System.out.println(System.getProperty("user.dir"));
		assertEquals(11, topWordnum);
		
	}
	
	@Test
	public void testWordFrequency1_2() {
		wordFrequencyCounter wordFrequencyCounter = new wordFrequencyCounter();
		int topWordnum = 0;
		ArrayList<HashMap.Entry<String, Integer>> wordList = new ArrayList<HashMap.Entry<String, Integer>>();
		HashMap<String, Integer> wordlistMap = new HashMap<String, Integer>();
		wordlistMap = wordFrequencyCounter.countWordsFrequency("D:\\java_project\\031602435&031602414\\src\\test\\java\\wordCount2_031602435\\"+"text1.txt",0);
		wordList = wordFrequencyCounter.topFrequentWords(wordlistMap);
		topWordnum = wordList.get(0).getValue();
		assertEquals(2, topWordnum);
		
	}
	
	@Test
	public void testPhraseFrequency2_1() {
		phraseFrequencyCounter phraseFrequencyCounter = new phraseFrequencyCounter();
		int topPhrasenum = 0;
		ArrayList<HashMap.Entry<String, Integer>> wordList = new ArrayList<HashMap.Entry<String, Integer>>();
		HashMap<String, Integer> wordlistMap = new HashMap<String, Integer>();
		wordlistMap = phraseFrequencyCounter.countPhraseFrequency("D:\\java_project\\031602435&031602414\\src\\test\\java\\wordCount2_031602435\\"+"result.txt",1,3);
		wordList = phraseFrequencyCounter.topFrequentPhrases(wordlistMap);
		topPhrasenum = wordList.get(0).getValue();
		assertEquals(97, topPhrasenum);
		
	}
	
	@Test
	public void testPhraseFrequency2_2() {
		phraseFrequencyCounter phraseFrequencyCounter = new phraseFrequencyCounter();
		int topPhrasenum = 0;
		ArrayList<HashMap.Entry<String, Integer>> wordList = new ArrayList<HashMap.Entry<String, Integer>>();
		HashMap<String, Integer> wordlistMap = new HashMap<String, Integer>();
		wordlistMap = phraseFrequencyCounter.countPhraseFrequency(System.getProperty("user.dir")+"\\src\\test\\java\\wordCount2_031602435\\"+"text2.txt",0,3);
		wordList = phraseFrequencyCounter.topFrequentPhrases(wordlistMap);
		topPhrasenum = wordList.get(0).getValue();
		assertEquals(4, topPhrasenum);
		
	}
	
	@Test
	public void testWordFrequency2_1() {
		wordFrequencyCounter wordFrequencyCounter = new wordFrequencyCounter();
		int topWordnum = 0;
		ArrayList<HashMap.Entry<String, Integer>> wordList = new ArrayList<HashMap.Entry<String, Integer>>();
		HashMap<String, Integer> wordlistMap = new HashMap<String, Integer>();
		wordlistMap = wordFrequencyCounter.countWordsFrequency(System.getProperty("user.dir")+"\\src\\test\\java\\wordCount2_031602435\\"+"text2.txt",1);
		wordList = wordFrequencyCounter.topFrequentWords(wordlistMap);
		topWordnum = wordList.get(0).getValue();
		System.out.println(System.getProperty("user.dir"));
		assertEquals(32, topWordnum);
		
	}
	
	@Test
	public void testWordFrequency2_2() {
		wordFrequencyCounter wordFrequencyCounter = new wordFrequencyCounter();
		int topWordnum = 0;
		ArrayList<HashMap.Entry<String, Integer>> wordList = new ArrayList<HashMap.Entry<String, Integer>>();
		HashMap<String, Integer> wordlistMap = new HashMap<String, Integer>();
		wordlistMap = wordFrequencyCounter.countWordsFrequency(System.getProperty("user.dir")+"\\src\\test\\java\\wordCount2_031602435\\"+"text2.txt",0);
		wordList = wordFrequencyCounter.topFrequentWords(wordlistMap);
		topWordnum = wordList.get(0).getValue();
		assertEquals(5, topWordnum);
		
	}

}
