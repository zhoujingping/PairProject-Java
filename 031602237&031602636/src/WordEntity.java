
public class WordEntity implements Comparable<WordEntity>{
		
	private String word;
	private Integer count;
	public WordEntity(String key, Integer value) {
		super();
	    this.word = key;
	    this.count = value;
	}
	public String getKey() {
	    return word;
	}
	public Integer getValue() {
	    return count;
	}
	public void setKey(String key) {
	    this.word = key;
	}
	public void setValue(Integer value) {
	    this.count = value;
	}
	public String toString(Parser parser) {
	    writeInTxt.writeTxt( "<" + word + ">:" + count,parser.getO());
	    return "<" + word + ">:" + count;
	}
	@Override
	public int compareTo(WordEntity O) {
	    int cmp=count.intValue()-O.count.intValue();
	    return (cmp==0?word.compareTo(O.getKey()):-cmp);
	}

}

