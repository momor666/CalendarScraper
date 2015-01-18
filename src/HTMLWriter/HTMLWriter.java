package HTMLWriter;

import java.io.File;
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
		s+= "<h1>Calendar Sync Checker V2.3</h1>";
		s+= "<p class=\"lead\">Checking Airbnb, Wimdu and Holiday Letting</p>";
		s+= "</div>";

		
		
		return s;
	}
	
	public String getTail(){
		String s ="      <hr>\n";
	    s+= "<h5> Last sync: " + LocalDateTime.now().toString().split("T")[0] + " " +LocalDateTime.now().toString().split("T")[1]+"<h5>\n";
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
	
	public void writeHTMLFile(String s) throws IOException{
		FileWriter fw = new FileWriter(s);
		PrintWriter pw = new PrintWriter(fw);
		pw.println(getHead());
		pw.println(message);
		pw.println(getTail());
		pw.close();
		fw.close();
		
		if (SendMailTLS.email_enabled && message.contains("danger")){
			SendMailTLS.sendMail();
			SendMailTLS.email_enabled = false;
		} 
		if(!message.contains("danger")){
			SendMailTLS.email_enabled = true;
		}
		
		message="";  
	}
	
}
