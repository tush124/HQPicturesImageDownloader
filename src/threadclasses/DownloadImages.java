package threadclasses;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JLabel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DownloadImages implements Runnable{
	private String url;
	private String savedImageFolder;
	private int startPage,endPage,numberOfFiles;
	
	private JLabel status;
	
	private ArrayList<String> imageUrls = new ArrayList<>();
	private ArrayList<String> imageNames = new ArrayList<>();

	public DownloadImages(String url, String savedImageFolder, int startPage, int endPage, int numberOfFiles,
			JLabel status) {
		super();
		this.url = url;
		this.savedImageFolder = savedImageFolder;
		this.startPage = startPage;
		this.endPage = endPage;
		this.numberOfFiles = numberOfFiles;
		this.status = status;
	}

	@Override
	public void run() {
		
		getAllImageUrl();
		status.setText("Images downloading started...");
		ExecutorService pool = Executors.newFixedThreadPool(numberOfFiles);
		
		for (int i = 0; i < imageUrls.size();i++) {
			
			pool.submit(new DonwloadTask(imageUrls.get(i),imageNames.get(i),savedImageFolder,status,imageUrls.size()));
			
			
		}
		
	}

	private void getAllImageUrl() {
		
		try {
			
			for(int i = startPage; i <= endPage ; i++){
				
				URL urlForParsing = new URL(url+"&page="+i);
				Document doc = Jsoup.parse(urlForParsing,0);
				
				Elements elementImage = doc.select("img[src].thumbnail");
				
				for (org.jsoup.nodes.Element element : elementImage) {
					String[] imageUrl = element.attr("src").split("(thumb_)");
					imageUrls.add("http://hq-pictures.com/" + imageUrl[0] + imageUrl[1]);
					imageNames.add(imageUrl[1]);
				}
				
				
			}
		} catch (IOException e) {
			status.setText("executor is shuting down abruptly");
			
		}
		
	}
	
	
	

}
