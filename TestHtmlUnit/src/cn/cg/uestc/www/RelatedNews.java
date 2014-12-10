package cn.cg.uestc.www;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.NodeList;

public class RelatedNews {
	
	private String ENCODE = "UTF-8";  //网页编码
	
	public static void main(String[] args){
		RelatedNews test = new RelatedNews();
		//要处理的网页
		String url = "http://news.baidu.com/ns?ct=1&rn=20&ie=utf-8&rsv_bp=1&sr=0&cl=2&f=8&prevct=no&tn=news&word=";
		String bufString = "北京再遇雾霾 多地空气质量降至重度污染";
		try {
			bufString =java.net.URLEncoder.encode(bufString, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		url += bufString;
		//总条数
		Queue<String> totalLabel = null;
		//获取html
		String html = test.findHtml(url);
		//保存总条数
		totalLabel = test.HandleHtml(html, "class", "nums");   //找到总的相关新闻
		System.out.println(totalLabel);
		//获取相关新闻
		Queue<String> number = test.HandleHtml(html, "class", "c-more_link");
//		System.out.println(test.HandleHtml(html, "em"));
		//保存相关新闻标题（待修改 不完整）
		Queue<String> result = new LinkedList<String>();
		//处理html空格
		html = html.replaceAll("\\s+", "");
//		System.out.println(html);
		//正则表达式 匹配相关新闻标题
		String commentReg = "target=\"_blank\">(<em>(.*?)</em>)+(.*?)<em>(.*?)((</a>)|(</em>))";
		Pattern newPage = Pattern.compile(commentReg);
		Matcher themeMatcher = newPage.matcher(html);
		//获取相关新闻标题
		while(themeMatcher.find()){
			String mm = themeMatcher.group();
			mm = mm.replaceAll("(target=\"_blank\">)|(<em>)|(</em>)|(</a>)", ""); //进一步处理不需要的乱码
			if(mm.contains("</h3"))  //同上
				mm = mm.substring(0, mm.indexOf("</h3"));
			result.offer(mm); 
		    mm = null;
		}
		//打印结果
		while((!number.isEmpty())&&(!result.isEmpty())){
			System.out.println(result.poll()+"\t"+number.poll());
		}
		while(!number.isEmpty()){
			System.out.println("获取新闻缺失"+number.poll());
		}
		while(!result.isEmpty()){
			System.out.println(result.poll()+"\t 相关新闻0条");
		}
	}

	public String findHtml(String url) {
		String html = null;                 //网页html
		HttpURLConnection httpUrlConnection;
	    InputStream inputStream;
	    BufferedReader bufferedReader;
	    
		int state = 0;
		URL tt ;
		//判断url是否为有效连接
		try{
			tt = new URL(url);
			httpUrlConnection = (HttpURLConnection) tt.openConnection(); //创建连接
			state = httpUrlConnection.getResponseCode();
			httpUrlConnection.disconnect();
		}catch (MalformedURLException e) {
          e.printStackTrace();
			System.out.println("该连接"+url+"网络有故障，已经无法正常链接，无法获取新闻");
//			return null ;
		} catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
			System.out.println("该连接\t"+url+"\t网络超级慢，已经无法正常链接，无法获取新闻");
//			return null ;
      }
		
		if(state != 200 && state != 401){
			System.out.println(state);
			return null;
		}
  
        try {
        	httpUrlConnection = (HttpURLConnection) new URL(url).openConnection(); //创建连接
        	httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setUseCaches(true); //使用缓存
            httpUrlConnection.connect();           //建立连接  链接超时处理
        } catch (IOException e) {
        	System.out.println("该链接访问超时...");
        	return null;
        }
  
        try {
            inputStream = httpUrlConnection.getInputStream(); //读取输入流
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, ENCODE)); 
            String string;
            StringBuffer sb = new StringBuffer();
            while ((string = bufferedReader.readLine()) != null) {
            	sb.append(string);
            	sb.append("\n");
            }
            html = sb.toString();
        } catch (IOException e) {
//            e.printStackTrace();
        }
//        System.out.println(html);
		return html;
	}
	
public Queue<String> HandleHtml(String html, String one) {
		
		NodeFilter filter = new HasAttributeFilter(one);
		Queue<String> buf = new LinkedList<String>();
		try{
			Parser parser = Parser.createParser(html, ENCODE);
			NodeList nodes = parser.extractAllNodesThatMatch(filter);
   		
			if(nodes!=null) {
				for (int i = 0; i < nodes.size(); i++) {
					Node textnode1 = (Node) nodes.elementAt(i);
					buf.offer(textnode1.toPlainTextString());
				}
			}
		}catch(Exception e){
		   
		   
		}
		return buf ;
	}

	@SuppressWarnings("null")
	public Queue<String> HandleHtml(String html, String one, String two) {
		NodeFilter filter = new HasAttributeFilter(one,two);
		Queue<String> buf = new LinkedList<String>();
		try{
			Parser parser = Parser.createParser(html, ENCODE);
			NodeList nodes = parser.extractAllNodesThatMatch(filter);
   		
			if(nodes!=null) {
				for (int i = 0; i < nodes.size(); i++) {
					Node textnode1 = (Node) nodes.elementAt(i);
					buf.offer(textnode1.toPlainTextString());
				}
			}
		}catch(Exception e){
 
		}
		return buf ;
	}
	
}
