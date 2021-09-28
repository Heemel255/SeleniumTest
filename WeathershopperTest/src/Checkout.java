import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Checkout extends Thread {
	
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
	
	public void enterDetails(String email, String card, String expiry, String cvc, String zip)
	{
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
		try {
			WebDriverWait wait = new WebDriverWait(wd, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("billing-zip")));
			
			zipBox = wd.findElement(By.id("billing-zip"));
			zipBox.sendKeys(t);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
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
			String[] tArr = splitEveryNChars(t, 4);
			
			for(int i = 0; i < tArr.length; i++)
				cardBox.sendKeys(tArr[i]);
		}
	}
	
	public void setExpireBoxText(String t)
	{
		if((t.length() == 4) && !(t.contains(" "))) {
			
			//separate into 2 elements
			String[] tArr = splitEveryNChars(t, 2);
			
			for(int i = 0; i < tArr.length; i++)
				expireBox.sendKeys(tArr[i]);
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
	
	private String[] splitEveryNChars(String s, int n)
	{
		String[] sArr = new String[s.length() / n];
		Arrays.fill(sArr, "");
		
		int arrInc = 0;
		for(int i = 0; i < s.length(); i++) {
			
			sArr[arrInc] = sArr[arrInc] + s.charAt(i);
			if((i + 1) % n == 0)
				arrInc++;
		}
		
		return sArr;
	}
}

