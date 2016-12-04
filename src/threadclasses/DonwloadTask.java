package threadclasses;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.swing.JLabel;

public class DonwloadTask implements Runnable {

	private String imageUrl,imageName,savedFolderPath;
	private static int i = 0;
	private JLabel status;
	private int noOfImages;
	
	
	public DonwloadTask(String imageUrl, String imageName, String savedFolderPath,JLabel status,
			int noOfImages) {
		super();
		this.imageUrl = imageUrl;
		this.imageName = imageName;
		this.savedFolderPath = savedFolderPath;
		this.status = status;
		this.noOfImages = noOfImages;
	}

	@Override
	public void run() {
		
		
		try {
			URL url = new URL(imageUrl);
			
			
		    ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		    FileOutputStream outputStream = new FileOutputStream(savedFolderPath+ "/" +imageName);
		    outputStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		    outputStream.close();
		    i++;
		    status.setText(i + " images downloaded out of " + noOfImages);
		    
		    
			
		} catch (IOException e) {
			status.setText("Promblem while downloading images");
		}

	}

}
