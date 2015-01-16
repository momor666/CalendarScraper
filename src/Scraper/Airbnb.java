package Scraper;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Airbnb {
	WebDriver driver;
	int timeout =30;
	public void runScraper(){
		//create driver
//		ProfilesIni profilesIni = new ProfilesIni();
//		FirefoxProfile profile = profilesIni.getProfile("default");
//		profile.setAssumeUntrustedCertificateIssuer(false);
		this.driver = new FirefoxDriver();
		
		//get to right page
		driver.get("https://www.airbnb.co.uk/rooms");
		
		//wait till log in element found
		try{
			(new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {

				public Boolean apply(WebDriver d) {
					if (driver.findElement(By.linkText("Log In")) != null)
						return true;
					else
						return false;
				}
			});
			} catch (Exception e){
				e.printStackTrace();
				return;
			}
			driver.findElement(By.linkText("Log In")).click();
			
			//wait till signin-email element found
			try{
				(new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {

					public Boolean apply(WebDriver d) {
						if (driver.findElement(By.id("signin_email")) != null)
							return true;
						else
							return false;
					}
				});
				} catch (Exception e){
					e.printStackTrace();
				}
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			//input username and password
			driver.findElement(By.id("signin_email")).clear();
		    driver.findElement(By.id("signin_email")).sendKeys("samiulali@hotmail.com");
		    driver.findElement(By.id("signin_password")).clear();
		    driver.findElement(By.id("signin_password")).sendKeys("samiul123");
		    driver.findElement(By.id("user-login-btn")).click();
		    
		    
//		    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		    
		   
				
		  //wait till log in element found
			try{
				(new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {

					public Boolean apply(WebDriver d) {
						if (driver.findElement(By.linkText("Your Listings")) != null)
							return true;
						else
							return false;
					}
				});
				} catch (Exception e){
					e.printStackTrace();
					return;
				}
				driver.findElement(By.linkText("Your Listings")).click();
				
				
			    // find view_cal to click
				try{
					(new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {

						public Boolean apply(WebDriver d) {
							if (driver.findElement(By.id("view_cal")) != null)
								return true;
							else
								return false;
						}
					});
					} catch (Exception e){
						e.printStackTrace();
					}			
					driver.findElement(By.id("view_cal")).click();
			
					driver.findElement(By.id("calendar_grid"));
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					// get HTML content
					System.out.println(driver.getPageSource());
//					System.out.println(driver.findElement(By.tagName("body")).getText());
		
		
		
	}
}
