
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Checkout {
	
	private WebDriver wd;
	
	private WebElement emailBox;
	private WebElement cardBox;
	private WebElement expireBox;
	private WebElement cvcBox;
	private WebElement payButton;
	private WebElement zipBox;
	
	public Checkout(WebDriver wd)
	{
		init(wd);
	}
	
	public Checkout(WebDriver wd, String email, String card, String expiry, String cvc, String zip)
	{
		init(wd);
		
		setEmailBoxText(email);
		setCardBoxText(card);
		setExpireBoxText(expiry);
		setCvcBoxText(cvc);
		setZip(zip);
	}
	
	private void init(WebDriver wd)
	{
		this.wd = wd;
		
		wd.switchTo().frame(wd.findElement(By.xpath("/html/body/iframe")));
		emailBox = wd.findElement(By.id("email"));
		cardBox = wd.findElement(By.id("card_number"));
		expireBox = wd.findElement(By.id("cc-exp"));
		cvcBox = wd.findElement(By.id("cc-csc"));
		payButton = wd.findElement(By.id("submitButton"));
	}
	
	
	public void setZip(String t)
	{
		WebDriverWait wait = new WebDriverWait(wd, 5);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("billing-zip")));
		
		zipBox = wd.findElement(By.id("billing-zip"));
		zipBox.sendKeys(t);
	}
	
	
	public String clickPay()
	{
		payButton.click();
		return "Payment successful";
	}
	public void setEmailBoxText(String t)
	{
		emailBox.sendKeys(t);
	}
	public void setCardBoxText(String t)
	{
		if((t.length() == 16) && !(t.contains(" "))) {
			
			//separate into 4 elements
			List<String> tList = new ArrayList<String>();
			
			for(int i = 0; i < t.length(); i+=4)
				tList.add(t.substring(i, Math.min(t.length(), i + 4)));
			
			//enter into textbox 4 times
			for(int i = 0; i < tList.size(); i++)
				cardBox.sendKeys(tList.get(i));
		}
	}
	public void setExpireBoxText(String t)
	{
		if((t.length() == 4) && !(t.contains(" "))) {
			
			//separate into 2 elements
			List<String> tList = new ArrayList<String>();
			
			for(int i = 0; i < t.length(); i+=2)
				tList.add(t.substring(i, Math.min(t.length(), i + 2)));
			
			//enter into textbox 2 times
			for(int i = 0; i < tList.size(); i++)
				expireBox.sendKeys(tList.get(i));
		}
	}
	public void setCvcBoxText(String t)
	{
		cvcBox.sendKeys(t);
	}
	
	public int getCheckoutPrice()
	{
		WebElement w = wd.findElement(By.xpath("//*[contains(text(),'Pay INR')]"));
		String s = w.getText();
		//regex to get rid of non digits
		s = s.replaceAll("\\D+","");
		//last 2 characters are removed because as they are after decimal
		String s2 = s.substring(0, s.length() - 2);
		return Integer.parseInt(s2);
	}
}

