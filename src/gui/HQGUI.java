package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import classes.ContextMenuMouseListener;

import threadclasses.DownloadImages;
import threadclasses.GetImageInfo;

import javax.swing.JButton;
import javax.swing.JFileChooser;

public class HQGUI extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtEnterUrl;
	private JTextField textFieldLocation;
	private JTextField txtNosOfFile;
	
	private String savedImageFolder = "./";
	private JTextField textFieldStartPage;
	private JTextField textFieldEndPage;
	private int numOfPages,numOfImages,startPage,endPage;
	
	public HQGUI() {
		InitGUI();
	}
	
	private void InitGUI() {
		
		setTitle("HQ-pictures.com Image Downloader");
		setSize(550, 480);
		setLocationRelativeTo(null);
		txtEnterUrl = new JTextField();
		txtEnterUrl.setBounds(126, 19, 377, 35);
		txtEnterUrl.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Enter Url (http://)");
		lblNewLabel.setBounds(12, 13, 108, 47);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().setLayout(null);
		getContentPane().add(txtEnterUrl);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Status");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(12, 141, 98, 47);
		getContentPane().add(lblNewLabel_1);
		
		JLabel labelStatus = new JLabel("-----------");
		labelStatus.setHorizontalAlignment(SwingConstants.CENTER);
		labelStatus.setBounds(120, 147, 383, 35);
		getContentPane().add(labelStatus);
		
		JButton btnGetImgesInfo = new JButton("Get Imges info");
		btnGetImgesInfo.setBounds(179, 82, 160, 35);
		getContentPane().add(btnGetImgesInfo);
		
		JButton btnStartDownload = new JButton("Start Download");
		btnStartDownload.setBounds(363, 341, 140, 55);
		getContentPane().add(btnStartDownload);
		
		JButton btnChooseLocation = new JButton("Choose location");
		btnChooseLocation.setBounds(12, 210, 140, 30);
		getContentPane().add(btnChooseLocation);
		
		textFieldLocation = new JTextField();
		textFieldLocation.setText("Default Location is root folder");
		textFieldLocation.setBounds(164, 208, 339, 35);
		getContentPane().add(textFieldLocation);
		textFieldLocation.setColumns(10);
		
		JLabel lblMadeByLinkedin = new JLabel("Made by linkedin@sharmatushar124");
		lblMadeByLinkedin.setHorizontalAlignment(SwingConstants.CENTER);
		lblMadeByLinkedin.setBounds(120, 409, 296, 16);
		getContentPane().add(lblMadeByLinkedin);
		
		txtNosOfFile = new JTextField();
		txtNosOfFile.setHorizontalAlignment(SwingConstants.CENTER);
		txtNosOfFile.setText("5");
		txtNosOfFile.setBounds(260, 350, 48, 41);
		getContentPane().add(txtNosOfFile);
		txtNosOfFile.setColumns(10);
		
		JLabel lblNosOfFile = new JLabel("Nos. of file to be Download at a time");
		lblNosOfFile.setBounds(26, 362, 222, 16);
		getContentPane().add(lblNosOfFile);
		
		JLabel lblStartPage = new JLabel("Start page");
		lblStartPage.setBounds(52, 286, 80, 16);
		getContentPane().add(lblStartPage);
		
		JLabel lblEndPage = new JLabel("End Page");
		lblEndPage.setBounds(284, 286, 56, 16);
		getContentPane().add(lblEndPage);
		
		textFieldStartPage = new JTextField();
		textFieldStartPage.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldStartPage.setText("1");
		textFieldStartPage.setBounds(143, 277, 116, 35);
		getContentPane().add(textFieldStartPage);
		textFieldStartPage.setColumns(10);
		
		textFieldEndPage = new JTextField();
		textFieldEndPage.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldEndPage.setBounds(365, 277, 116, 35);
		getContentPane().add(textFieldEndPage);
		textFieldEndPage.setColumns(10);
		
		
		
		btnGetImgesInfo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				labelStatus.setText("Please wait.....");
				String url = txtEnterUrl.getText();
				
				//getting image  
				
				GetImageInfo imageInfo = new GetImageInfo(url);
				Thread threadImageInfo = new Thread(imageInfo);
				threadImageInfo.start();
				
				try {
					threadImageInfo.join();
					
				} catch (Exception e1) {
					labelStatus.setText("Something went wrong!! please check Url.");
					
				}
				
				
				numOfImages = imageInfo.getNumOfImages();
				numOfPages = imageInfo.getNumOfPages();
				
				//setting label and field status here
				labelStatus.setText(imageInfo.getStatus());
				textFieldEndPage.setText(numOfPages+"");
			}
		});
		
		btnChooseLocation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileDiaLog = new JFileChooser();
				fileDiaLog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				int returnValue = fileDiaLog.showOpenDialog(null);
				
				if(returnValue == JFileChooser.APPROVE_OPTION){
					
					savedImageFolder = fileDiaLog.getSelectedFile().getAbsolutePath();
					textFieldLocation.setText(savedImageFolder);
				}
				
			}
		});
		
		btnStartDownload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				startPage = Integer.parseInt(textFieldStartPage.getText());
				endPage = Integer.parseInt(textFieldEndPage.getText());
				
				if(startPage > 0 && startPage <= endPage && endPage <= numOfPages)
				{
					DownloadImages downloadImage = new DownloadImages(txtEnterUrl.getText(), savedImageFolder, startPage, endPage, Integer.parseInt(txtNosOfFile.getText()), labelStatus);
					Thread threadDownLoad = new Thread(downloadImage);
					threadDownLoad.start();
					
					try {
						threadDownLoad.join();
					} catch (InterruptedException e1) {
						labelStatus.setText("Something went wrong with downloading");
					}
				}
				else
					labelStatus.setText("Wrong page number entered");
				
			}
		});
		
		//mouse listener
		txtEnterUrl.addMouseListener(new ContextMenuMouseListener());
		
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
