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

public class HolidayLettingScraper {
	WebDriver driver;
	PropertyStructure property;
	public HolidayLettingScraper(PropertyStructure property){
		this.property = property;
	}
	
	
	public void scrape() throws InterruptedException, IOException, ParseException{
		if (property.holidayletting_name==null || property.holidayletting_name.equals(""))
			return;
		
		scrapeCalendarData();
		updateAvailablity();
	}


	private void scrapeCalendarData() throws IOException {
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
			String baseUrl = "https://www.holidaylettings.co.uk/";
		    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		    driver.get(baseUrl + "content/availability_calendar");
		    driver.findElement(By.id("ownerId")).clear();
		    driver.findElement(By.id("ownerId")).sendKeys("samiulali@hotmail.com");
		    driver.findElement(By.id("ownerPassword")).clear();
		    driver.findElement(By.id("ownerPassword")).sendKeys("samiul123");
		    driver.findElement(By.xpath("//div[@id='ownerLoginForm']/button")).click();
		    driver.findElement(By.id("calendarContainer"));
		    int randomtry = new Random().nextInt((10 - 5) + 1) + 5;
		    for (int i =0; i <randomtry; i++){
		    	driver.findElement(By.cssSelector("span.ui-selectmenu-status")).click();
			    driver.findElement(By.xpath("//a[contains(text(),'"+property.holidayletting_name+"')]")).click();
			    Thread.sleep(5000);
		    }
		    driver.findElement(By.id("calendarContainer"));
		    printToFile(driver.getPageSource());
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			driver.quit();
		}
	}
	
	private void printToFile(String s) throws IOException{
		String localFileName = "/tmp/holidayletting.data";
		File f = new File(localFileName);
		if (f.exists()) f.delete();
		FileWriter fw = new FileWriter(localFileName);
		PrintWriter pw = new PrintWriter(fw);
		String[] tmp = s.split("\\n");
		for (int i =0; i < tmp.length;i++){
			if (tmp[i].contains("data-cell-date")){
				String p1 = tmp[i].split("class=")[0];
				p1 = p1.split("\"")[1];
				p1 = p1.trim();
				String p2 = tmp[i].split("class=")[1];
				p2 = p2.split(">")[0];
				p2 = p2.replace("\"", "");
				p2 = p2.trim();
				if (p2.equals("ab") || p2.equals("ah") || p2.equals("b") || p2.equals("h"))
					pw.println(p1 + " " + p2);
			}
		}
		pw.close();
		fw.close();
		
	}
	
	private void updateAvailablity() throws IOException, ParseException{
		FileReader fr = new FileReader("/tmp/holidayletting.data");
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		
		while ((line = br.readLine())!= null){
			line = line.split(" ")[0];
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy"); 			
			Date d = dt.parse(line);
			DateTime date = new DateTime(d);
			if (!(date.plusDays(1)).isBefore(System.currentTimeMillis())){
				property.add_HolidayLetting_Availablity(date.toString().split("T")[0]);
			}
		}
		br.close();
		fr.close();
		
	}

}
