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
		String s = "<html>\n";
		s+= "<head>";
		s+="<script type=\"text/javascript\">";
		s+="  setTimeout(function(){";
		s+="    location.reload()";
		s+="  },10000)";
		s+="</script>";
		s+= "</head>";
		s+= "<body>\n";
		return s;
	}
	
	public String getTail(){
		String s ="";
		s+= "<h4> Synchronisation Checked at:" + LocalDateTime.now() +"<h4>\n";
		s +=  "</body>\n";
		s += "</html>\n";
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
		
		if (SendMailTLS.email_enabled && message.contains("Out of Sync")){
			SendMailTLS.sendMail();
			SendMailTLS.email_enabled = false;
		} 
		if(!message.contains("Out of Sync")){
			SendMailTLS.email_enabled = true;
		}
		
		
		
		message="";  
	}
	
}
