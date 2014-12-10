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
	
	private String ENCODE = "UTF-8";  //��ҳ����
	
	public static void main(String[] args){
		RelatedNews test = new RelatedNews();
		//Ҫ�������ҳ
		String url = "http://news.baidu.com/ns?ct=1&rn=20&ie=utf-8&rsv_bp=1&sr=0&cl=2&f=8&prevct=no&tn=news&word=";
		String bufString = "������������ ��ؿ������������ض���Ⱦ";
		try {
			bufString =java.net.URLEncoder.encode(bufString, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		url += bufString;
		//������
		Queue<String> totalLabel = null;
		//��ȡhtml
		String html = test.findHtml(url);
		//����������
		totalLabel = test.HandleHtml(html, "class", "nums");   //�ҵ��ܵ��������
		System.out.println(totalLabel);
		//��ȡ�������
		Queue<String> number = test.HandleHtml(html, "class", "c-more_link");
//		System.out.println(test.HandleHtml(html, "em"));
		//����������ű��⣨���޸� ��������
		Queue<String> result = new LinkedList<String>();
		//����html�ո�
		html = html.replaceAll("\\s+", "");
//		System.out.println(html);
		//������ʽ ƥ��������ű���
		String commentReg = "target=\"_blank\">(<em>(.*?)</em>)+(.*?)<em>(.*?)((</a>)|(</em>))";
		Pattern newPage = Pattern.compile(commentReg);
		Matcher themeMatcher = newPage.matcher(html);
		//��ȡ������ű���
		while(themeMatcher.find()){
			String mm = themeMatcher.group();
			mm = mm.replaceAll("(target=\"_blank\">)|(<em>)|(</em>)|(</a>)", ""); //��һ��������Ҫ������
			if(mm.contains("</h3"))  //ͬ��
				mm = mm.substring(0, mm.indexOf("</h3"));
			result.offer(mm); 
		    mm = null;
		}
		//��ӡ���
		while((!number.isEmpty())&&(!result.isEmpty())){
			System.out.println(result.poll()+"\t"+number.poll());
		}
		while(!number.isEmpty()){
			System.out.println("��ȡ����ȱʧ"+number.poll());
		}
		while(!result.isEmpty()){
			System.out.println(result.poll()+"\t �������0��");
		}
	}

	public String findHtml(String url) {
		String html = null;                 //��ҳhtml
		HttpURLConnection httpUrlConnection;
	    InputStream inputStream;
	    BufferedReader bufferedReader;
	    
		int state = 0;
		URL tt ;
		//�ж�url�Ƿ�Ϊ��Ч����
		try{
			tt = new URL(url);
			httpUrlConnection = (HttpURLConnection) tt.openConnection(); //��������
			state = httpUrlConnection.getResponseCode();
			httpUrlConnection.disconnect();
		}catch (MalformedURLException e) {
          e.printStackTrace();
			System.out.println("������"+url+"�����й��ϣ��Ѿ��޷��������ӣ��޷���ȡ����");
//			return null ;
		} catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
			System.out.println("������\t"+url+"\t���糬�������Ѿ��޷��������ӣ��޷���ȡ����");
//			return null ;
      }
		
		if(state != 200 && state != 401){
			System.out.println(state);
			return null;
		}
  
        try {
        	httpUrlConnection = (HttpURLConnection) new URL(url).openConnection(); //��������
        	httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setUseCaches(true); //ʹ�û���
            httpUrlConnection.connect();           //��������  ���ӳ�ʱ����
        } catch (IOException e) {
        	System.out.println("�����ӷ��ʳ�ʱ...");
        	return null;
        }
  
        try {
            inputStream = httpUrlConnection.getInputStream(); //��ȡ������
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
