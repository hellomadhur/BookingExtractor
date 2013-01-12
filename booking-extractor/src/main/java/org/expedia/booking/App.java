package org.expedia.booking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String url = "http://www.booking.com";
		SaveScript(url);
	}
	
	private static void SaveScript(String url){
		
		PrintWriter pw = null;
		File output = new File("booking.txt");
		
		/*--------- Initializing the WebDriver, loading website and waiting for an element to load---------*/
		WebDriver driver = new FirefoxDriver();
    	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    	driver.get(url);
    	WebDriverWait wait = new WebDriverWait(driver, 30);
    	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("logo-not-document-write")));
    	String viewSource = driver.getPageSource();
    	String[] lines = viewSource.split("\n");
    	ArrayList<String> listLines = new ArrayList<String>(Arrays.asList(lines));
    		
    	boolean include=false;
    	String line;
    	Iterator<String> itLine = listLines.iterator();
    	while(itLine.hasNext()){
    		
    		line = itLine.next();
    		if(line.contains("pb_social : \"stopped\"")){
    			include = true;
    		}
    		if(line.contains("booking.env.map_more_params")){
    			include = false;
    			continue;
    			
    		}
    		if(include == false){
    			itLine.remove();
    		}
    		
    	}
    	try {
			pw = new PrintWriter(output);
			for(String outputLine : listLines){
	    		pw.write(outputLine + "\n");
	    	}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}finally{
			pw.close();
		}
    	
    	driver.close();
	}

}
