package HTMLWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.text.DateFormatter;

import net.sf.cglib.core.Local;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import util.ConfigurationUtil;
import Email.SendMailTLS;

public class HTMLWriter {
	String message ="";
	
	public HTMLWriter(){
		
	}
	
	public void addToHTML(String s){
		s+= "\n";
		this.message += s;
		
	}
	
	public String getHead() {
		String s= "<!DOCTYPE html>\n";
		s += "<html lang=\"en\">\n";
		s+= "<head>\n";
		s+="<meta charset=\"utf-8\">\n";
	    s+="<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n";
	    s+="<meta name=\"viewport\" content=\"width=device-width, initial-scale=0.8\">\n";
	    s+="<meta name=\"description\" content=\"\">\n";
	    s+="<meta name=\"author\" content=\"\">\n";
	    s+="<link rel=\"icon\" href=\"favicon.ico\">\n";

		s += "<title>Calendar Sync Checker</title>\n";
		s+="<script type=\"text/javascript\">\n";
		s+="  setTimeout(function(){\n";
		s+="    location.reload()\n";
		s+="  },60000)\n";
		s+="</script>\n";

		s+="<title>Calendar Sync Checker</title>\n";
		 
		s+= "</head>\n";
		s+= "<body>\n";
		s+= "<div class=\"container\">";
		s+= "<div class=\"page-header\">";
		s+= "<h1>Calendar Sync Checker V17.0</h1>";
		s+= "<p class=\"lead\">Airbnb, Wimdu, Holiday Letting and Booking.com<br>";
		s+= "Last sync: " + LocalDateTime.now().toString().split("T")[0] + " " +LocalDateTime.now().toString().split("T")[1]+"</p>";
		s+= "</div>";

		
		
		return s;
	}
	
	public String getTail(){
		String s ="      <hr>\n";
		s+="</div> <!-- /container -->";
		s+="<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css\">";
		s+="<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css\">";
		s+="<script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js\">";
		s+="<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js\"></script>";
		s +=  "</body>\n\n";
		s += "</html>\n\n";
		return s;
	}
	
	public String getHTML(){
		message = getHead() + message + getTail();
		return message;
	}
	
	public String getStatus(ConfigurationUtil config){
		String s = "";
		s+= "<h4>Status ";
		
		for (int i =0; i < config.getPropertyList().size(); i++ ){
			if (config.getPropertyList().get(i).conflict_detected){
				s+= "<span class=\"label label-danger\">"+ (i+1)+"</span>";
			} else {
				s+= "<span class=\"label label-success\">"+ (i+1)+"</span>";
			}
		}
		s+="</h4>";
		return s;
	}
	
	public void writeHTMLFile(String s, ConfigurationUtil config) throws IOException{
		FileWriter fw = new FileWriter(s);
		PrintWriter pw = new PrintWriter(fw);
		pw.println(getHead());
		pw.println(getStatus(config));
		pw.println(message);
		pw.println(getTail());
		pw.close();
		fw.close();
		
//		if (!isEmailDisabled() && message.contains("danger")){
//			SendMailTLS.sendMail("Calendars out of Sync!");
////			SendMailTLS.email_enabled = false;
//			disableEmail(true);
//		} 
//		if(!message.contains("danger") && isEmailDisabled()){
//			SendMailTLS.sendMail("Calendars Synced");
//			disableEmail(false);
//		}
		
		
		if (syncChaned()){
			SendMailTLS.sendMail("Calendar Sync Changed");
			writeNewSyncCount();
		}
		
		message="";  
	}
	
	
	private boolean syncChaned() throws IOException{
		
		String [] tmp = message.split(" ");
		int newCount =0;
		for (int i =0; i < tmp.length; i++){
			if (tmp[i].contains("danger")){
				newCount++;
			}
		}
		
		File f = new File("/tmp/sync.count");
		int existingCount = 0; 
				
		if (f.exists()){
			FileReader fr = new FileReader (f);
			BufferedReader br = new BufferedReader (fr);
			String input = ""; 
			input = br.readLine();
			existingCount = Integer.parseInt(input);
			br.close();
			fr.close();
			if (newCount == existingCount)
				return false;
			else 
				return true;
		}
		else {
			return true;
		}
	}
	
	private void writeNewSyncCount() throws IOException{
		FileWriter fw = new FileWriter("/tmp/sync.count");
		PrintWriter pw = new PrintWriter(fw);
		
		String [] tmp = message.split(" ");
		int newCount =0;
		for (int i =0; i < tmp.length; i++){
			if (tmp[i].contains("danger")){
				newCount++;
			}
		}
		
		pw.println(newCount);
		
		pw.close();
		fw.close();
		
	}
	
}
