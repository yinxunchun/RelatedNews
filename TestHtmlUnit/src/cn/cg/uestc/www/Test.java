package cn.cg.uestc.www;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class Test {

	public static void main(String[] args) throws IOException{
		
		String url = "http://news.baidu.com/ns?ct=1&rn=20&ie=utf-8&rsv_bp=1&sr=0&cl=2&f=8&prevct=no&tn=news&word=%E5%8C%97%E4%BA%AC%E5%86%8D%E9%81%87%E9%9B%BE%E9%9C%BE%20%E5%A4%9A%E5%9C%B0%E7%A9%BA%E6%B0%94%E8%B4%A8%E9%87%8F%E9%99%8D%E8%87%B3%E9%87%8D%E5%BA%A6%E6%B1%A1%E6%9F%93&qq-pf-to=pcqq.c2c";
		
		String refer="http://news.baidu.com/ns?ct=1&rn=20&ie=utf-8&rsv_bp=1&sr=0&cl=2&f=8&prevct=no&tn=news&word=北京再遇雾霾 多地空气质量降至重度污染";
        URL link=new URL(refer); 
        WebClient wc=new WebClient();
        WebRequest request=new WebRequest(link); 
        request.setCharset("UTF-8");
//        request.setProxyHost("120.120.120.x");
//        request.setProxyPort(8080);
//        request.setAdditionalHeader("Referer", refer);//设置请求报文头里的refer字段
        ////设置请求报文头里的User-Agent字段
//        request.setAdditionalHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
        //wc.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
        //wc.addRequestHeader和request.setAdditionalHeader功能应该是一样的。选择一个即可。
        //其他报文头字段可以根据需要添加
        wc.getCookieManager().setCookiesEnabled(true);//开启cookie管理
        wc.getOptions().setJavaScriptEnabled(true);//开启js解析。对于变态网页，这个是必须的
        wc.getOptions().setCssEnabled(true);//开启css解析。对于变态网页，这个是必须的。
        wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
        wc.getOptions().setThrowExceptionOnScriptError(false);
        wc.getOptions().setTimeout(10000);
        //设置cookie。如果你有cookie，可以在这里设置
        Set<Cookie> cookies = new HashSet();
        Iterator<Cookie> i = cookies.iterator();
        while (i.hasNext()) 
        {
            wc.getCookieManager().addCookie(i.next());
        }
        //准备工作已经做好了
        HtmlPage page=null;
        page = wc.getPage(request);
        if(page==null)
        {
            System.out.println("采集 "+url+" 失败!!!");
            return ;
        }
        String content=page.asText();//网页内容保存在content里
        if(content==null)
        {
            System.out.println("采集 "+url+" 失败!!!");
            return ;
        }else
        	System.out.println(content);
        //搞定了
//        CookieManager CM = wc.getCookieManager(); //WC = Your WebClient's name
//        Set<Cookie> cookies_ret = CM.getCookies();//返回的Cookie在这里，下次请求的时候可能可以用上啦。
	
	
	
	}
}
