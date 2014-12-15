package cn.cg.uestc.www;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class SeleniumTest {

	@SuppressWarnings("static-access")
	public static void main(String[] args){
		
		System.getProperties().setProperty("webdriver.chrome.driver", "C:/Users/Administrator/git/RelatedNews/TestHtmlUnit/seleniumjar/chromedriver.exe");
		String url = "http://comment.news.163.com/news_guonei8_bbs/ADF3TNOE0001124J.html";
		WebDriver driver = new ChromeDriver();
//		WebDriver driver = new HtmlUnitDriver();
//		driver.get(url);
		driver.get("http://quan.sohu.com/pinglun/cyqemw6s1/406950999"); 
		try {
			new Thread().sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
        WebElement webElement = driver.findElement(By.xpath("//div[@class='topic-changyan']"));  
//        String test1 = driver.findElement(By.xpath("class=\"topic-hot\"")).getText();
//        System.out.println(webElement.getAttribute("outerHTML"));  
        String test = webElement.getText();
        test = test.replaceAll("\\s+", "");
        String commentReg = "](.*?)回复分享";
		
		Pattern newPage = Pattern.compile(commentReg);
		
		Matcher themeMatcher = newPage.matcher(test);
		while(themeMatcher.find()){
			String mm = themeMatcher.group();
			mm = mm.replaceAll("]|(回复分享)", "");
			System.out.println(mm);
		}
//        System.out.println(webElement.getText()+ "ssssssss");

//        System.getProperties().setProperty("webdriver.chrome.driver", "C:\\Users\\Administrator\\git\\TestHtmlUnit\\chromedriver.exe");  
//        WebDriver webDriver =new ChromeDriver();  
//        webDriver.get("http://list.jd.com/9987-653-655-0-0-0-0-0-0-0-1-1-1-1-1-72-4137-33.html");  
//  
//        WebElement webElement = webDriver.findElement(By.xpath("//div[@id='plist']"));  
//  
//        System.out.println(webElement.getAttribute("outerHTML"));  
//  
//        WebElement li=webElement.findElement(By.xpath("//li[@index='1']"));  
//  
//        String name=li.findElement(By.xpath("//li[@index='1']//div[@class='p-name']/a")).getText();  
//        System.out.println("商品名:"+name);  
//  
//        String price=li.findElement(By.xpath("//li[@index='1']//div[@class='p-price']/strong")).getText();  
//        System.out.println("价格:"+price);  
//  
//        String eva=li.findElement(By.xpath("//li[@index='1']//span[@class='evaluate']/a[@target='_blank']")).getText();  
//        System.out.println("评价:"+eva);  
//  
//        webDriver.close();  
        driver.close();  
        driver.quit();
	}
}
