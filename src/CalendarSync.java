import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;

import javax.swing.text.html.HTML;

import ConflictChecker.ConflictChecker;
import Email.SendMailTLS;
import HTMLWriter.HTMLWriter;
import Scraper.AirbnbScraper;
import Scraper.HolidayLettingScraper;
import Scraper.WimduScraper;
import util.ConfigurationUtil;




public class CalendarSync {
	public static void main(String[] args) {
	try{
		//Load Properties
		ConfigurationUtil config = new ConfigurationUtil();
		HTMLWriter html = new HTMLWriter();
		
		
//		config.printConfig();
		while(true){
			//Scrape Properties
			System.out.print("Running");
			for (int i =0; i < config.getPropertyList().size() ; i++){
				System.out.print(".");
				html.addToHTML("<h4> Property " + (i+1) +": " + config.getPropertyList().get(i).airbnb_name +"</h4>");
				//scrape Airbnb
				AirbnbScraper airbnb = new AirbnbScraper(config.getPropertyList().get(i));
				airbnb.scrape();
				//scrape wimdu
				WimduScraper wimdu = new WimduScraper(config.getPropertyList().get(i));
				wimdu.scrape();
				
				//scrape holidayletting
				HolidayLettingScraper holidayletting = new HolidayLettingScraper(config.getPropertyList().get(i));
				
				//Checking for conflicts.
//				html.addToHTML("Checking Airbnb and Wimdu");
				ConflictChecker conflictchecker = new ConflictChecker(config.getPropertyList().get(i),html);
				conflictchecker.checkConflicts();	
			
			}
			//Output HTML
			html.writeHTMLFile(config.getHtml_output_file());
			System.out.println("Waiting");
			Thread.sleep(30000);
		}
	} catch (Exception e){
		e.printStackTrace();
	}
}}
