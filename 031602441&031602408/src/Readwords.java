import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Readwords {
	public static Map<String,Integer> read(String pathname,int Wflag,int Numflag,String Opathname)
	{
	File fr = new File(pathname);
	int weight;
	int wordcount=0;
	int lines=0;
	int character=0;
	Map<String,Integer> words = new TreeMap<String,Integer>();
	if(Wflag==1)
	{
		weight=10;
	}
	else weight=1;
	try {
		
		BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(fr)));
		String s="";
		
		Pattern p1 = Pattern.compile("[0-9a-zA-Z]+");//字符串
		Pattern p = Pattern.compile("(^[a-zA-Z]{4}[a-zA-Z0-9]*)");//单词
		Pattern p3 = Pattern.compile("\\s*");//是否是空行
		String s2="";
		
		StringBuffer s4=new StringBuffer();
		Matcher m;
		Matcher m1;
		int START1=1;
		int END1=1;
		int i=0;
		String s1="";
		String re="";
		/*************录单词*****************/
		while((s=bf.readLine())!=null)
		{
			if(s.length()!=0)
			{
				if(s.charAt(0)=='T')//title
				{
					s=s.substring(7);
					lines++;
					character+=s.replaceAll("\r\n","\n").length()+1;
					m = p1.matcher(s);
					while(m.find())
					{
						s4=new StringBuffer();
						s1 = m.group();//s1找到第一个单词
						m1 = p.matcher(s1);
						if(m1.find())//判断单词
						{
							wordcount++;
							s1=s1.toLowerCase();
							s4.append(s1);//将找到第一个单词插入短语中
							s2=s.substring(m.end());//句子中剩下的部分
							Matcher m2 = p1.matcher(s2);//找短语其他成分
							START1=0;
							for(i=1;i<Numflag;i++)//判断短语
							{	
								if(m2.find())
								{
									String s3=m2.group();//短语第二部分
									m1=p.matcher(s3);//判断是否是单词
									if(m1.find())
									{
										END1=m2.end();
										//System.out.println(START1+" "+END1);
										re=s2.substring(START1,END1);
										re=re.toLowerCase();
										//是单词插入短语
										s4.append(re);
										START1=m2.end();
									}
									else 
									{
										break;
									}
								}
								else break;
							}
							if(i==Numflag)//满足短语要求,插入map
							{
								if(words.get(s4.toString())!=null)
								{
									words.put(s4.toString(),words.get(s4.toString())+weight);
									
								}
								else
								{
								words.put(s4.toString(),weight);
								
								}
							}
						}
					}
				}
				else if(s.charAt(0)=='A')//abstract
				{
					s=s.substring(10);//abstract之后
					//m = p3.matcher(s);
					lines++;
					character+=s.replaceAll("\r\n","\n").length()+1;
					m = p1.matcher(s);
					while(m.find())
					{
						
						s4=new StringBuffer();
						s1 = m.group();//s1找到第一个单词
						m1 = p.matcher(s1);
						if(m1.find())//判断单词
						{
							wordcount++;
						s1=s1.toLowerCase();
						s4.append(s1);//将找到第一个单词插入短语中
						s2=s.substring(m.end());//句子中剩下的部分
						
						Matcher m2 = p1.matcher(s2);//找短语其他成分
						START1=0;			
						for(i=1;i<Numflag;i++)//判断短语
						{	
							if(m2.find())
							{
								String s3=m2.group();//短语第二部分			
								m1=p.matcher(s3);//判断是否是单词
								if(m1.find())
								{
									END1=m2.end();		
									//是单词插入短语
									re=s2.substring(START1,END1);
									re=re.toLowerCase();
									s4.append(re);
									START1=m2.end();		
								}
								else 
								{
									break;
								}
							}
							else break;
						}
						if(i==Numflag)//满足短语要求,插入map
						{
							
							if(words.get(s4.toString())!=null)
							{
								words.put(s4.toString(),words.get(s4.toString())+1);
							}
							else
							{
							words.put(s4.toString(),1);
							}
							
						}
						}
					}
				}
			}
		}
		bf.close();
		export1.export1(character, wordcount, lines, Opathname);
		
		}
	catch (UnsupportedEncodingException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	} catch (FileNotFoundException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	} catch (IOException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	return words;
	}
}
