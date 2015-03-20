package Scraper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import Property.PropertyStructure;

public class BookingDotComScraper {
	WebDriver driver;
	PropertyStructure property;
	public BookingDotComScraper(PropertyStructure property){
		this.property = property;
		
	}
	
	
	public void scrape() throws Exception{
		if (property.bookingDotComPropertyId == null || property.bookingDotComPropertyId.equals("")){
			return;
		}
		scrapeCalendarData();
//		updateAvailablity();
	}


	private void scrapeCalendarData() throws Exception {
		try{
			
			
			ProfilesIni profilesIni = new ProfilesIni();
			FirefoxProfile profile = profilesIni.getProfile("default");
			profile.setAssumeUntrustedCertificateIssuer(false);
			final File firefoxPath = new File(System.getProperty(
	                "lmportal.deploy.firefox.path", "/usr/bin/firefox"));

			if (firefoxPath.exists()){
				String Xport = System.getProperty(
		                "lmportal.xvfb.id", ":1");
		        FirefoxBinary firefoxBinary = new FirefoxBinary(firefoxPath);
		        firefoxBinary.setEnvironmentProperty("DISPLAY", Xport);
		        this.driver =  new FirefoxDriver(firefoxBinary, profile);
			} else{
				this.driver =  new FirefoxDriver(profile);	
			}
			
	//		this.driver = new HtmlUnitDriver();
			String baseUrl = "https://admin.booking.com/";
		    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		    driver.get(baseUrl + "/hotel/hoteladmin/");
		    driver.findElement(By.id("loginname")).clear();
		    driver.findElement(By.id("loginname")).sendKeys(property.bookingDotComPropertyId);
		    driver.findElement(By.id("password")).clear();
		    driver.findElement(By.id("password")).sendKeys("samiul123");
		    driver.findElement(By.name("login")).click();
		       
		    
		    Thread.sleep(5000);
		    driver.findElement(By.xpath("//div[@id='content_wrapper']/nav/ul/li[2]/a")).click();
		    
		    for (int i =0; i < 15; i ++){
		    	Thread.sleep(3000);
			    driver.findElement(By.xpath("//div[@id='mini-calendars']/a[2]/span")).click();	
		    }
		    
		    Thread.sleep(5000);
		    
		    FileWriter fw = new FileWriter("/tmp/booking.out");
		    PrintWriter pw = new PrintWriter(fw);
		    
		    String[] data = driver.findElement(By.id("calendar")).getAttribute("innerHTML").split("\n");
		    for (int i =0; i < data.length; i++){
		    	if (data[i].contains("closed") || data[i].contains("soldout")){
		    		property.add_Bookingdotcom_Availablity((data[i].substring(data[i].indexOf("date-") + 5, data[i].indexOf("date-") + 15)));
		    	}
		    	if (data[i].contains("fully_booked&quot;:1,")){
		    		property.add_Bookingdotcom_Availablity((data[i].substring(data[i].indexOf("date-") + 5, data[i].indexOf("date-") + 15)));
		    		pw.println((data[i].substring(data[i].indexOf("date-") + 5, data[i].indexOf("date-") + 15)));
		    	}
		    }
		    
		    pw.close();
		    fw.close();
		    
		    
		    driver.quit();
		} catch (Exception e){
			e.printStackTrace();
			driver.quit();
			throw new Exception();
		} 
	}
}
