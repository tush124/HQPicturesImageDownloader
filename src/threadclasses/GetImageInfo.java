package threadclasses;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public class GetImageInfo implements Runnable {

	private String urlForParsing;
	private String status;
	private int numOfPages;
	private int numOfImages;
	
	
	
	public GetImageInfo(String urlForParsing) {
		super();
		this.urlForParsing = urlForParsing;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			URL url = new URL(urlForParsing);
			org.jsoup.nodes.Document doc = Jsoup.parse(url, 0);
			
			
			Elements numberOfpagesDoc = doc.select(".tableh1");
			
			status = numberOfpagesDoc.last().text();
			
			numOfPages = Integer.parseInt(status.split("[ ]")[3]);
			numOfImages = Integer.parseInt(status.split("[ ]")[0]);
			
			
							
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			status = "Please check the URL entered";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			status = "No internet Connection.";
		}
		
	}

	public String getUrlForParsing() {
		return urlForParsing;
	}

	public String getStatus() {
		return status;
	}

	public int getNumOfPages() {
		return numOfPages;
	}

	public int getNumOfImages() {
		return numOfImages;
	}
	
	
	

}
