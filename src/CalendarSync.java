import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;

import ConflictChecker.ConflictChecker;
import Scraper.AirbnbScraper;
import Scraper.HolidayLettingScraper;
import Scraper.WimduScraper;
import util.ConfigurationUtil;




public class CalendarSync {
	public static void main(String[] args) throws MalformedURLException, IOException, ParseException{
		//Load Properties
		ConfigurationUtil config = new ConfigurationUtil();
//		config.printConfig();
		
		//Scrape Properties
		for (int i =0; i < config.getPropertyList().size() ; i++){
			System.out.println("Property " + (i+1));
			//scrape Airbnb
			AirbnbScraper airbnb = new AirbnbScraper(config.getPropertyList().get(i));
			airbnb.scrape();
			//scrape wimdu
			WimduScraper wimdu = new WimduScraper(config.getPropertyList().get(i));
			wimdu.scrape();
			//scrape holidayletting
			HolidayLettingScraper holidayletting = new HolidayLettingScraper(config.getPropertyList().get(i));
			
			//
			ConflictChecker conflictchecker = new ConflictChecker(config.getPropertyList().get(i));
			conflictchecker.checkConflicts();
		}
	}
}
