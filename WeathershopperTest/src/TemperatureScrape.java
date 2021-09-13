import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TemperatureScrape {
	//save temperature n refreshes to int array
	//get lowest temperature every n refreshes
	//get highest temperature every n refreshes
	//get average temperature every n refreshes
	
	private WebDriver wd;
	
	private List<Integer> temps;
	
	public TemperatureScrape(WebDriver wd)
	{
		this.wd = wd;
		
		temps = new ArrayList<Integer>();
	}
	
	
	public int getMin() 
	{
		int min = temps.get(0);
		
		for(int i = 1; i < temps.size(); i++) {
			
			if(temps.get(i) < min)
				min = temps.get(i);
		}
		
		return min;
	}
	
	public int getMax()
	{
		int max = temps.get(0);
		
		for(int i = 1; i < temps.size(); i++) {
			
			if(temps.get(i) > max)
				max = temps.get(i);
		}
		
		return max;
	}
	
	public int getAvg()
	{
		int sum = 0;
		
		for(int i = 0; i < temps.size(); i++)
			sum += temps.get(i);
		
		return sum / temps.size();
	}
	
	public List<Integer> getTemps()
	{
		return temps;
	}
	
	public void startScrape(int refreshTimes)
	{
		temps = new ArrayList<Integer>();
		temps.clear();
		
		for(int i = 0; i < refreshTimes; i++) {
			
			String t = wd.findElement(By.id("temperature")).getText();
			
			temps.add(tempGetRidOfCelcius(t));
			wd.navigate().refresh();
		}
		
		//get last page 
		String t = wd.findElement(By.id("temperature")).getText();
		
		temps.add(tempGetRidOfCelcius(t));
	}
	
	private int tempGetRidOfCelcius(String t)
	{
		//first 2 characters return the temperature number
		String s = t.charAt(0) + "";
		
		if(Character.isDigit(t.charAt(1))) {
			
			s = s + t.charAt(1);
		}
		
		return Integer.parseInt(s);
	}
}
