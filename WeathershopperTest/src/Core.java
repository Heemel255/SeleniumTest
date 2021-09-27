import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

public class Core {

	public static void main(String[] args) throws InterruptedException 
	{
		System.setProperty("webdriver.chrome.driver", "C:\\selenium\\selenium-java-3.141.59\\chromedriver_win32\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		Scanner sc = new Scanner(System.in);
		
		driver.manage().window().maximize();
		driver.get("https://weathershopper.pythonanywhere.com/");
		/*
		//first page: go to second page based on console input, or do temperature scraping
		String input;
		do {
			System.out.println("Press 1 for moisturizers\nPress 2 for sunscreens\nPress 3 to scrape temperature data");
			input = sc.nextLine();
			
			System.out.println(firstPage(driver, input));
		}while(!(input.equals("1") || input.equals("2")  || input.equals("3")));
		
		
		//either go to second page or measure scraped temperature
		if(input.equals("1") || input.equals("2")) 
			goToOtherPages(driver);
		else
			tempScrape(driver, sc);
		
			
		sc.close();*/
		//changes
		driver.close();
	}
	
	public static String firstPage(WebDriver wd, String choice)
	{
		WebElement nextPageBtn = null;
		String message = null;
		
		if(choice.equals("1")) {
			
			nextPageBtn = wd.findElement((By.xpath("/html/body/div/div[3]/div[1]/a/button")));
			message = "Moisturizer pressed";
		}
		else if(choice.equals("2")) {
			
			nextPageBtn = wd.findElement((By.xpath("/html/body/div/div[3]/div[2]/a/button")));
			message = "Sunscreen pressed";
		}
		
		if(choice.equals("1") || choice.equals("2")) {
			
			nextPageBtn.click();
			return message;
		}
		else if(choice.equals("3")) {
			
			return "Temperature scraping selected";
		}
			
		return "Invalid";
	}
	
	public static void goToOtherPages(WebDriver driver)
	{
		//second page: add first n items to cart, click cart
		ShopPageTest mt = new ShopPageTest(driver);
		
		Random rand = new Random();
		int amt = 1 + rand.nextInt(6);
		
		mt.addToCart(amt);
		
		System.out.println("Cart price is: " + mt.getSumPricesCart(amt));
		
		if(mt.getAmtAdded() > 1)
			System.out.println("First " + mt.getAmtAdded() + " items added to cart");
		else
			System.out.println("First item added");
		
		mt.clickCheckout();
		
		//third page: click checkout, wait for it to load
		driver.findElement((By.xpath("/html/body/div[1]/div[3]/form/button/span"))).click();
		
		
		//checkout box:enter info into boxes, wait for zip then enter into zip box, click submit
		CheckoutTest ct = new CheckoutTest(driver, "test@test.com", "4242424242424242", "1122", "3333", "m1k 1x1");
		System.out.println("Checkout price is " + ct.getCheckoutPrice());
		System.out.println(ct.clickPay());
	}
	
	public static void tempScrape(WebDriver driver, Scanner sc)
	{
		String input;
		boolean inputCorrect = false;
		
		do {
			System.out.println("Press 1 to get lowest temperature\nPress 2 to get highest temperature\nPress 3 to get average temperature");
			input = sc.nextLine();
			inputCorrect = input.equals("1") || input.equals("2")  || input.equals("3");
			if(!inputCorrect)
				System.out.println("Invalid");
		}while(!inputCorrect);
		
		TemperatureScrape ts = new TemperatureScrape(driver);
		
		int totalTempScrapes = 10;
		ts.startScrape(totalTempScrapes);
		
		List<Integer> temps = ts.getTemps();
		System.out.println("Temperatures list:");
		
		for(int i = 0;i < temps.size(); i++)
			System.out.println(temps.get(i));
		
		if(input.equals("1")) {
			System.out.println("Lowest temp: " + ts.getMin());
		}
		else if(input.equals("2")) {
			System.out.println("Highest temp: " + ts.getMax());
		}
		else {
			System.out.println("Average temp: " + ts.getAvg());
		}
	}
}
