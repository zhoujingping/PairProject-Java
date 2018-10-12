import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

public class FileParser {
	private int charNum;
	private int line;
	private int wordNum;
	private Map<String,Integer> mp;
	private Queue<Integer> del;
	private StringBuilder cizu;
	private StringBuffer temp;
	public int getCharNum() {
		return charNum;
	}
	public int getLine() {
		return line;
	}
	public int getWordNum() {
		return wordNum;
	}
	public boolean isLetter(int ch) {
		if(ch>=(int)'a' && ch<=(int)'z') {
			return true;
		}
		if(ch>=(int)'A' && ch<=(int)'Z') {
			return true;
		}
		return false;
	}
	public boolean isDigit(int ch) {
		if(ch>=(int)'0' && ch<=(int)'9') {
			return true;
		}
		return false;
	}
	FileParser(){
		charNum=0;
		line=0;
		wordNum=0;
		mp=new HashMap<String,Integer>();
		del= new LinkedList<Integer>();
		temp=new StringBuffer();
		cizu=new StringBuilder();
	}
	public void Parser(FileReader f,int w,int m) {
		int ch;
		int state=0;
		int val1=1;
		int val2=1;
		int pos=0;
		int space=0;
		if(w==1)val1=10;
		boolean isWord=true;
		boolean start=false;
		BufferedReader br=new BufferedReader(f);
		try {
			while((ch= br.read()) != -1) {
				if(!isLetter(ch)&&!isDigit(ch)) {
					charNum++;
					continue;
				}
				else {
					break;
				}
			}
			temp.append((char)ch);
			while((ch=br.read())!=-1) {
				if(ch>255)continue;
				//编号
				if(state==0) {
					if(isDigit(ch)) {
						temp.append((char)ch);
					}
					else {
						int no=Integer.parseInt(temp.toString());
						//System.out.println(no);
						line=(no+1)*2;
						state=1;
						temp.setLength(0);
					}
				}
				//读取Title：
				else if(state==1) {
					if(ch==' ') {
						cizu.setLength(0);
						del.clear();
						state=2;
						start=false;
						temp.setLength(0);
					}
				}
				//非字母数字
				else if(state==2) {
					charNum++;
					if(ch=='\n') {
						//under windows , delete the '\t'
						charNum--;
						state=4;
					}
					if(isLetter(ch)||isDigit(ch)) {
						
						if(m>1&&start) {
							cizu.append(temp.toString());
							del.offer(temp.length());
							
						}
						temp.setLength(0);
						isWord=true;
						pos=0;
						state=3;
						if(isDigit(ch)) {
							isWord=false;
						}
						if(ch>='A'&&ch<='Z') {
							ch-='A'-'a';
						}
					}
					temp.append((char)ch);
				}
				//字母数字
				else if(state==3) {
					charNum++;
					if(ch>='A'&&ch<='Z') {
						ch-='A'-'a';
					}
					if(ch=='\n') {
						charNum--;
						state=4;
					}
					if((!isLetter(ch)&&!isDigit(ch))) {
						//not a word ,clear the cizu and del
						if(!isWord||temp.length()<4) {
							cizu.setLength(0);
							del.clear();
						}
						//add word,if the queue is reach m,then add to map
						else {
							wordNum++;
							start=true;
							//add this word
							cizu.append(temp.toString());
							//add this size
							del.offer(temp.length());
							
							if(del.size()==m*2-1) {
								if(mp.containsKey(cizu.toString())) {
									int val=mp.get(cizu.toString())+val1;
									mp.put(cizu.toString(), val);
								}
								else {
									
									mp.put(cizu.toString(), val1);
								}
								int size=del.poll();
								if(m>1) {
									size+=del.poll();
								}
								cizu.delete(0, size);
							}
							
						}
						if(state==4) {
							
						}
						else {
							temp.setLength(0);
							state=2;
						}
						
					}
					temp.append((char)ch);
					if(pos<4&&isDigit(ch)) {
						isWord=false;
					}
					pos++;
				}
				//读取Abstract：
				else if(state==4) {
					if(ch==' ') {
						cizu.setLength(0);
						del.clear();
						temp.setLength(0);
						state=5;
						start=false;
						space=0;
					}
				}
				//非字母数字
				else if(state==5) {
					charNum++;
					if(ch=='\n') {
						//under windows , delete the '\t'
						charNum--;
						state=7;
					}
					if(isLetter(ch)||isDigit(ch)) {
						if(m>1&&start) {
							cizu.append(temp.toString());
							del.offer(temp.length());
						}
						temp.setLength(0);;
						isWord=true;
						pos=0;
						state=6;
						if(isDigit(ch)) {
							isWord=false;
						}
						if(ch>='A'&&ch<='Z') {
							ch-='A'-'a';
						}
					}
					temp.append((char)ch);
				}
				//字母数字
				else if(state==6) {
					charNum++;
					if(ch>='A'&&ch<='Z') {
						ch-='A'-'a';
					}
					if(ch=='\n') {
						charNum--;
						state=7;
					}
					if((!isLetter(ch)&&!isDigit(ch))) {
						//not a word ,clear the cizu and del
						if(!isWord||temp.length()<4) {
							cizu.setLength(0);
							del.clear();
						}
						//add word,if the queue is reach m,then add to map
						else {
							wordNum++;
							start=true;
							//add this word
							cizu.append(temp.toString());
							//add this size
							del.offer(temp.length());
							if(del.size()==m*2-1) {
								if(mp.containsKey(cizu.toString())) {
									int val=mp.get(cizu.toString())+val2;
									mp.put(cizu.toString(), val);
								}
								else {
									mp.put(cizu.toString(), val2);
								}
								int size=del.poll();
								if(m>1) {
									size+=del.poll();
								}
								cizu.delete(0, size);
							}
							
						}
						if(state==7) {
							
						}
						else {
							temp.setLength(0);
							state=5;
						}
						
					}
					temp.append((char)ch);
					if(pos<4&&isDigit(ch)) {
						isWord=false;
					}
					pos++;
				}
				//行间两空行
				else if(state==7) {
					if(ch=='\n') {
						space++;
					}
					if(space==2) {
						state=0;
						temp.setLength(0);
					}
				}
			}
			if(state==3) {
				//not a word ,clear the cizu and del
				if(!isWord||temp.length()<4) {
					cizu.setLength(0);
					del.clear();
				}
				//add word,if the queue is reach m,then add to map
				else {
					wordNum++;
					start=true;
					//add this word
					cizu.append(temp.toString());
					//add this size
					del.offer(temp.length());
					if(del.size()==m*2-1) {
						if(mp.containsKey(cizu.toString())) {
							int val=mp.get(cizu.toString())+val1;
							mp.put(cizu.toString(), val);
						}
						else {
							
							mp.put(cizu.toString(), val1);
						}
						int size=del.poll();
						if(m>1) {
							size+=del.poll();
						}
						cizu.delete(0, size);
					}
				}
			}
			else if(state==6) {
				//not a word ,clear the cizu and del
				if(!isWord||temp.length()<4) {
					cizu.setLength(0);
					del.clear();
				}
				//add word,if the queue is reach m,then add to map
				else {
					wordNum++;
					start=true;
					//add this word
					cizu.append(temp.toString());
					//add this size
					del.offer(temp.length());
					if(del.size()==m*2-1) {
						if(mp.containsKey(cizu.toString())) {
							int val=mp.get(cizu.toString())+val2;
							mp.put(cizu.toString(), val);
						}
						else {
							mp.put(cizu.toString(), val2);
						}
						int size=del.poll();
						if(m>1) {
							size+=del.poll();
						}
						cizu.delete(0, size);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("read wrong");
			e.printStackTrace();
		}
	}
	public void out(FileWriter fw,int n) {
		try {
			fw.write("characters: ");
			fw.write(new Integer(charNum).toString());
			fw.write("\r\nwords: ");
			fw.write(new Integer(wordNum).toString());
			fw.write("\r\nlines: ");
			fw.write(new Integer(line).toString()+"\r\n");
			if(n>mp.size()) {
				n=mp.size();
			}
			List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(mp.entrySet());
			java.util.Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
	            public int compare(Entry<String, Integer> o1,Entry<String, Integer> o2) {
	                if(o1.getValue()!=o2.getValue()) {
	                	return o2.getValue().compareTo(o1.getValue());
	                }
	                else {
	                	return o1.getKey().compareTo(o2.getKey());
	                }
	            }
	        });
			for(int i=0;i<n;i++) {
				Map.Entry<String,Integer> ent=list.get(i);
				String key=ent.getKey().toString();
				Integer value=ent.getValue();
				fw.write("<"+key+">: "+value.toString()+"\r\n");
			}
			fw.flush();
		} catch (IOException e) {
			System.out.println("write wrong");
			e.printStackTrace();
		}
		
	}
}

