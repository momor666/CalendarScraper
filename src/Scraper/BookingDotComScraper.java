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
		    driver.findElement(By.id("loginname")).sendKeys("1280132");
		    driver.findElement(By.id("password")).clear();
		    driver.findElement(By.id("password")).sendKeys("samiul123");
		    driver.findElement(By.name("login")).click();
//		    driver.quit();
		} catch (Exception e){
			e.printStackTrace();
			driver.quit();
			throw new Exception();
		} 
	}
}
