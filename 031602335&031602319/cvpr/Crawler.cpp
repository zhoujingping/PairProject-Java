#include<iostream>
#include<string>
#include"winsock2.h"
#include<fstream>
#include<queue>
#pragma comment(lib, "ws2_32.lib")
using namespace std;
//定义读取网页时缓冲区的初始大小
#define DEFAULT_PAGE_BUF_SIZE 1048576
//用队列存储所有论文网页的URL中的文件路劲（网页内容主要是关于论文的介绍根据此网页提取标头和摘要）
queue<string> Texturl;     
//WSA启动命令，执行成功可调用Windows Sockets API
void startupWSA()
{
	WSADATA wsadata;
	//启动则成功返回0
	if (WSAStartup(MAKEWORD(2, 0), &wsadata) != 0)
	{
		cerr << "WSA启动失败" << endl;
		exit(1);
	}
}
//释放应用程序资源
inline void cleanupWSA()
{
	WSACleanup();
}
//给定一个字符串指针和网页文件路径名（由于专门做2018论文爬取，服务器主机名是固定的）
//通过文件路径获取网页内容存入字符串指针
void GO(char * &response, string source)
{
	string host, resource;
	host = "openaccess.thecvf.com"; //存储2018论文的服务器域名，在该服务器下有2018年论文的所有信息
	resource = source;
	struct hostent * hp = gethostbyname(host.c_str());
	if (hp == NULL)
	{
		//找不到主机域名
		cerr << "can not find address";
		exit(1);
	}
	SOCKET sock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);

	if (sock == -1 || sock == -2) {
		//创建连接端口失败
		cout << "Can not create sock." << endl;
		exit(1);
	}
	SOCKADDR_IN sa;
	sa.sin_family = AF_INET;
	sa.sin_port = htons(80);
	memcpy(&sa.sin_addr, hp->h_addr, 4);
	connect(sock, (SOCKADDR*)&sa, sizeof(sa));
	//http请求命令
	string request = "GET " + resource + " HTTP/1.1\r\nHost:" + host + "\r\nConnection:Close\r\n\r\n";
	//发送请求
	send(sock, request.c_str(), request.size(), 0);
	int m_nContentLength = DEFAULT_PAGE_BUF_SIZE;
	int bytesRead = 0;
	int ret = 1;
	//创建缓冲区大小的字符串指针
	char *pageBuf = (char *)malloc(m_nContentLength);
	char *temp = NULL;
	//缓冲区初始化
	if(pageBuf!=0)
	memset(pageBuf, 0, m_nContentLength);
	while (ret > 0)
	{
		//接收返回的信息，ret为读入的字节数，为0时表示完成读取，连接中止
		if(pageBuf+bytesRead!=0)
		ret = recv(sock, pageBuf + bytesRead, m_nContentLength - bytesRead, 0);
		if (ret > 0)
		{
			//记录读入的总字节数
			bytesRead += ret;
		}
		if (m_nContentLength - bytesRead < 100)
		{
			//将缓冲区扩大两倍,可加快读取速度
			m_nContentLength *= 2;
			temp = (char*)realloc(pageBuf, m_nContentLength);
			if (temp != NULL)
				pageBuf = temp;
		}
	}
	//字符串结束符
	pageBuf[bytesRead] = '\0';
	response = pageBuf;
	closesocket(sock);
}
//去除URL中存在的\r符
string Durl(string turl)
{
	string u;
	const char *urlo = turl.c_str();
	char ab[100];
	int j = 0;
	for (unsigned int k = 0; k < strlen(urlo); k++)
		{
			if (urlo[k] != '\r')
			{
				ab[j] = urlo[k];
				j++;
			}
		}
	ab[j] = '\0';
	u = ab;
	return u;
}
//通过浏览论文首页得到所有论文的URL并存入队列
void GetTexturl()
{
	int q;
	char *response=NULL;
	//2018论文官网网页文件
	string source = "/CVPR2018.py";
	//response返回2018论文官网网页内容
	GO(response, source);
	//所以的论文介绍文件在服务器主机的该目录下，由此可匹配到所有论文的URL地址
	//所以论文的URL地址的文件路劲均以该字符串开头以.html结束
	const char *t = "content_cvpr_2018/html/";
	const char *result = strstr(response, t);
	string turl, gurl;
	while (result)
	{
		const char * nextQ = strstr(result, "\"");
		if (nextQ) {
			char * url = new char[nextQ - result + 1];
			q=sscanf(result, "%[^\"]", url);
			result += strlen(url);
			//以下是论文URL中存在某些出错的处理
			const char *urlp = url;
			const char *nextq = strstr(urlp, "\n");
			if (nextq)
			{
				char * furl = new char[nextq - urlp + 1];
				q=sscanf(urlp, "%[^\n]", furl);
				turl = furl;
				urlp += strlen(furl);
				urlp++;
				const char * next1 = strstr(urlp, "\n");
				if (next1)
				{
					char * furll = new char[next1 - urlp + 1];
					q=sscanf(urlp, "%[^\n]", furll);
					urlp += strlen(furll);
					urlp++;
					gurl = urlp;
					turl += gurl;
					delete[] furll;
				}
				delete[] furl;
			}
			else
			{
				turl = urlp;
			}
			turl = Durl(turl);
			turl = "/" + turl;
			//加入队列
			Texturl.push(turl);
			delete[] url;
		}
		result = strstr(result, t);
	}
	free(response);
}
//通过遍历队列中所以论文URL，得到标头和摘要存入result.txt中
void GetText()
{
	int q;
	//标头的开始匹配字符串
	const char * t = "papertitle\">";
	//摘要的开始匹配字符串
	const char * a = "abstract\" >";
	int n = Texturl.size();
	string u;
	ofstream outfile("result.txt");
	if (!outfile)
	{
		cerr << "open error!" << endl;
		exit(1);
	}
	for (int i = 0; i < n; i++)
	{
		u = Texturl.front();
		//获取论文介绍的网页
		char * response = NULL;
		GO(response, u);
		//在论文介绍网页中提取标头和摘要
		const char * result = strstr(response, t);
		if (result)
		{
			result += strlen(t);
			result++;
			const char * next = strstr(result, "<");
			char * title = new char[next - result + 1];
			q=sscanf(result, "%[^<]", title);
			result = strstr(result, a);
				if (result)
				{
					result += strlen(a);
					result++;
					const char * next2 = strstr(result, "<");
					char * abstract = new char[next2 - result + 1];
					q = sscanf(result, "%[^<]", abstract);
					outfile << i << endl;
					outfile << "Title: " << title << endl;
					outfile << "Abstract: " << abstract << endl;
					if (i != n - 1)
					{
						outfile << endl;
						outfile << endl;
					}
					delete[] abstract;
				}
			delete[] title;
		}
		free(response);
		Texturl.pop();
	}
	outfile.close();
}
//主程序
int main()
{
	startupWSA();
	GetTexturl();
	GetText();
	cleanupWSA();
	system("pause");
	return 0;
}