import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

public class Core {

	public static void main(String[] args) throws InterruptedException 
	{
		System.setProperty("webdriver.chrome.driver", "C:\\selenium\\selenium-java-3.141.59\\chromedriver_win32\\chromedriver.exe");
		
		new Thread(run1()).start();
		new Thread(run2()).start();
		new Thread(run3()).start();
		new Thread(run4()).start();
		new Thread(run5()).start();
	}
	
	public static Runnable run1()
	{
		Runnable r = new Runnable() {
			@Override
			public void run() {
				WebDriver driver = new ChromeDriver();
				init(driver);
				
				goToPages(driver, firstPage(driver, 1), Thread.currentThread().getName());
				driver.close();
			}
		};
		
		return r;
	}
	
	public static Runnable run2()
	{
		Runnable r = new Runnable() {
			@Override
			public void run() {
				WebDriver driver = new ChromeDriver();
				init(driver);
				
				goToPages(driver, firstPage(driver, 2), Thread.currentThread().getName());
				driver.close();
			}
		};
		
		return r;
	}
	
	public static Runnable run3()
	{
		Runnable r = new Runnable() {
			@Override
			public void run() {
				WebDriver driver = new ChromeDriver();
				init(driver);
				
				tempScrape(driver, 1, Thread.currentThread().getName());
				driver.close();
			}
		};
		
		return r;
	}
	
	public static Runnable run4()
	{
		Runnable r = new Runnable() {
			@Override
			public void run() {
				WebDriver driver = new ChromeDriver();
				init(driver);
				
				tempScrape(driver, 2, Thread.currentThread().getName());
				driver.close();
			}
		};
		
		return r;
	}
	
	public static Runnable run5()
	{
		Runnable r = new Runnable() {
			@Override
			public void run() {
				WebDriver driver = new ChromeDriver();
				init(driver); 
				
				tempScrape(driver, 3, Thread.currentThread().getName());
				driver.close();
			}
		};
		
		return r;
	}
	
	public static void init(WebDriver driver) 
	{
		driver.manage().window().maximize();
		driver.get("https://weathershopper.pythonanywhere.com/");
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
	}
	
	public static WebElement firstPage(WebDriver wd, int choice)
	{
		WebElement nextPageBtn = null;
		
		if(choice == 1) {
			
			nextPageBtn = wd.findElement((By.xpath("/html/body/div/div[3]/div[1]/a/button")));
		}
		else if(choice == 2) {
			
			nextPageBtn = wd.findElement((By.xpath("/html/body/div/div[3]/div[2]/a/button")));
		}
			
		return nextPageBtn;
	}
	
	public static void goToPages(WebDriver driver, WebElement fp, String threadName)
	{
		//first page
		fp.click();
		
		//second page: add first n items to cart, click cart
		ShopPage mt = new ShopPage(driver);
		
		Random rand = new Random();
		int amt = 1 + rand.nextInt(6);
		
		mt.addToCart(amt);
		
		int cartPrice = mt.getSumPricesCart();
		System.out.println(threadName + ": Cart price is: " + cartPrice);
		
		if(mt.getAmtAdded() > 1)
			System.out.println(threadName + ": First " + mt.getAmtAdded() + " items added to cart");
		else
			System.out.println(threadName + ": First item added");
		
		mt.clickCheckout();
		
		//third page: click checkout
		driver.findElement((By.xpath("/html/body/div[1]/div[3]/form/button/span"))).click();
		
		
		//checkout box:enter info into boxes, wait for zip then enter into zip box, click submit
		Checkout ct = new Checkout(driver, "test@test.com", "4242424242424242", "1122", "3333", "m1k 1x1");
		
		int checkoutPrice = ct.getCheckoutPrice();
		System.out.println(threadName + ": Checkout price is " + checkoutPrice);
		
		if(checkoutPrice == cartPrice)
			System.out.println(threadName + ": Cart price and checkout price is the same");
		else
			System.out.println(threadName + ": !Cart price and checkout price is the not the same!");
			
		System.out.println(threadName + ": " + ct.clickPay());
	}
	
	public static void tempScrape(WebDriver driver, int choice, String threadName)
	{
		
		TemperatureScrape ts = new TemperatureScrape(driver);
		
		int totalTempScrapes = 10;
		ts.startScrape(totalTempScrapes);
		
		List<Integer> temps = ts.getTemps();
		
		for(int i = 0;i < temps.size(); i++)
			System.out.print(threadName + ": " + temps.get(i) + "c | ");
		
		System.out.println();
		switch(choice) {
			case 1:
				System.out.println(threadName + ": Lowest temp: " + ts.getMin());
				break;
			case 2:
				System.out.println(threadName + ": Highest temp: " + ts.getMax());
				break;
			case 3:
				System.out.println(threadName + ": Average temp: " + ts.getAvg());
				break;
			default:
				System.out.println("N/A");
				break;
		}
	}
}
