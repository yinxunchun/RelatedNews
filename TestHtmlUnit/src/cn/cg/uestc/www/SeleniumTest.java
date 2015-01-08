package cn.cg.uestc.www;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class SeleniumTest {

	@SuppressWarnings("static-access")
	public static void main(String[] args){
		
		System.getProperties().setProperty("webdriver.chrome.driver", "C:/Users/xunchun/git/RelatedNews/TestHtmlUnit/seleniumjar/chromedriver.exe");
		String url = "http://comment.news.163.com/news_guonei8_bbs/ADF3TNOE0001124J.html";
		WebDriver driver = new ChromeDriver();
//		WebDriver driver = new HtmlUnitDriver();
//		driver.get(url);http://quan.sohu.com/pinglun/cyqemw6s1/406950999
		driver.get("http://news.sohu.com/20141224/n407249418.shtml"); 
		try {
			new Thread().sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//class="topic-degree"   //div[@class='topic-changyan']
		WebElement webElement;
		try{
			webElement = driver.findElement(By.xpath("//div[@class='f12']"));
		}catch(NoSuchElementException e){
			return;
		}finally{
	        driver.close();  
	        driver.quit();
		}
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

        driver.close();  
        driver.quit();
	}
}
