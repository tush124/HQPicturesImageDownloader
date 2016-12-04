package hqmain;

import gui.HQGUI;
import javax.swing.JLabel;

public class HQMain {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HQGUI guiObject = new HQGUI();
		
		JLabel lblEg = new JLabel("eg: http://hq-pictures.com/thumbnails.php?album=731");
		lblEg.setBounds(124, 58, 377, 16);
		guiObject.getContentPane().add(lblEg);
	}

}
