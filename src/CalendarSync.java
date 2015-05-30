import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.text.html.HTML;

import ConflictChecker.ConflictChecker;
import Email.SendMailTLS;
import HTMLWriter.HTMLWriter;
import IgnoreListProcessor.ProcessIgnoreList;
import Scraper.AirbnbScraper;
import Scraper.AirbnbScraper2;
import Scraper.AirbnbScraper3;
import Scraper.BookingDotComScraper;
import Scraper.HolidayLettingScraper;
import Scraper.WimduScraper;
import util.ConfigurationUtil;




public class CalendarSync {
	public static void main(String[] args) throws Exception {
//	try{
		//Load Properties
		ConfigurationUtil config = new ConfigurationUtil();
		HTMLWriter html = new HTMLWriter();
		
		
//		config.printConfig();
//		while(true){
			
			//Scrape Properties
			System.out.print("S");
			for (int i =0; i < config.getPropertyList().size() ; i++){
//				if (i != 4) continue;
			
				System.out.print(" " + (i+1));
				html.addToHTML("<h4> Property " + (i+1) +": " + config.getPropertyList().get(i).airbnb_name +"</h4>");

				
				
				
				//scrape Airbnb_Sam
				System.out.print("A");
				AirbnbScraper airbnb = new AirbnbScraper(config.getPropertyList().get(i));
				airbnb.scrape();
				
				//scrape wimdu
				System.out.print("W");
				WimduScraper wimdu = new WimduScraper(config.getPropertyList().get(i));
				wimdu.scrape();
				
				
				//scrape holidayletting
				System.out.print("H");
				HolidayLettingScraper holiday = new HolidayLettingScraper(config.getPropertyList().get(i));
				holiday.scrape();
				
				
				//Booking.com Scraper
				System.out.print("B");
				BookingDotComScraper bookingdotcom = new BookingDotComScraper(config.getPropertyList().get(i));
				bookingdotcom.scrape();
				
				
				//scrape Airbnb_IHK
				System.out.print("A");
				AirbnbScraper2 airbnb2 = new AirbnbScraper2(config.getPropertyList().get(i));
				airbnb2.scrape();
				
			
				//scrape Airbnb_SAR
				System.out.print("A");
				AirbnbScraper3 airbnb3 = new AirbnbScraper3(config.getPropertyList().get(i));
				airbnb3.scrape();
				
				
				//Process Ignore List
				
				ProcessIgnoreList pl = new ProcessIgnoreList();
				pl.processIgnoreList(config.getPropertyList().get(i), i+1);
				
				
				//Checking for conflicts.
				ConflictChecker conflictchecker = new ConflictChecker(config.getPropertyList().get(i),html);
				conflictchecker.checkConflicts();	
				
				
				
				
			}
//			System.exit(0);
			//Output HTML
			html.writeHTMLFile(config.getHtml_output_file(),config);
			System.out.println(" W");
			
//			Thread.sleep(60000);
//			break;
//		}
//	} catch (Exception e){
//		e.printStackTrace();
//	}
}
	}
