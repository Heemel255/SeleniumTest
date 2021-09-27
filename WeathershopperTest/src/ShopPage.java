import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ShopPage {
	
	private WebDriver wd;
	private List<WebElement> buttons;
	private int amt;
	
	private List<WebElement> prices;
	
	public ShopPage(WebDriver wd)
	{
		this.wd = wd;
		
		buttons = wd.findElements(By.cssSelector("[class='btn btn-primary'"));
		
		prices = wd.findElements(By.xpath("//*[contains(text(),'Price:')]"));
	}
	
	public int getSumPricesCart()
	{
		String[] s = new String[amt];
		int sum = 0;
		
		for(int i = 0; i < s.length; i++) {
			
			s[i] = prices.get(i).getText();
			//regex to get rid of non digits
			s[i] = s[i].replaceAll("\\D+","");
			sum += Integer.parseInt(s[i]);
		}
		
		return sum;
	}
	
	//add first n items to cart
	public void addToCart(int amt)
	{
		this.amt = amt;
		
		for(int i = 0; i < amt; i++) {
			
			buttons.get(i).click();
		}
	}
	
	public int getAmtAdded()
	{
		return amt;
	}
	
	public void clickCheckout() 
	{
		wd.findElement(By.xpath("/html/body/nav/ul/button")).click();
	}
}
