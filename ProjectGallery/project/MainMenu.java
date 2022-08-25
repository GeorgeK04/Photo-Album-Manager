import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import javax.swing.JEditorPane;
import javax.swing.JTable;

public class MainMenu {

	private JFrame frmAlbumCreator;

	// Launch the application:
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.frmAlbumCreator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	// Create the application.
	public MainMenu() 
	{
		initialize();
	}


	//Initialize the contents of the frame.
	private void initialize() 
	{
		frmAlbumCreator = new JFrame();
		frmAlbumCreator.setTitle("Album Creator");
		frmAlbumCreator.getContentPane().setBackground(new Color(204, 255, 255));
		frmAlbumCreator.setBounds(100, 100, 676, 373);
		frmAlbumCreator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAlbumCreator.getContentPane().setLayout(null);
		
		JButton btnViewAlbum = new JButton("VIEW ALBUMS");
		btnViewAlbum.setFont(new Font("Roboto", Font.BOLD, 12));
		
		btnViewAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				GalleryWindow.main(null);
			}
		});
		btnViewAlbum.setForeground(Color.BLACK);
		btnViewAlbum.setBackground(new Color(50, 217, 153));
		btnViewAlbum.setBounds(46, 43, 129, 31);
		frmAlbumCreator.getContentPane().add(btnViewAlbum);
		
		JLabel labelViewAlbum = new JLabel("");
		labelViewAlbum.setIcon(new ImageIcon("C:\\Users\\AceTsak\\Desktop\\view (2).png"));
		labelViewAlbum.setFont(new Font("Roboto", Font.PLAIN, 12));
		labelViewAlbum.setBounds(193, 31, 43, 49);
		frmAlbumCreator.getContentPane().add(labelViewAlbum);
		
		JButton btnAddAlbum = new JButton("ADD / DELETE");
		btnAddAlbum.setFont(new Font("Roboto", Font.BOLD, 12));
		btnAddAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				CreateNew.main(null);
			}
		});
		btnAddAlbum.setForeground(Color.BLACK);
		btnAddAlbum.setBackground(new Color(50, 217, 153));
		btnAddAlbum.setBounds(46, 98, 129, 31);
		frmAlbumCreator.getContentPane().add(btnAddAlbum);
		
		JLabel labelAddAlbum = new JLabel("");
		labelAddAlbum.setIcon(new ImageIcon("C:\\Users\\AceTsak\\Desktop\\newAlb.png"));
		labelAddAlbum.setFont(new Font("Roboto", Font.PLAIN, 12));
		labelAddAlbum.setBounds(187, 88, 51, 49);
		frmAlbumCreator.getContentPane().add(labelAddAlbum);
		
		JLabel labelTEI = new JLabel("");
		labelTEI.setIcon(new ImageIcon("C:\\Users\\AceTsak\\Desktop\\unnamed.png"));
		labelTEI.setBounds(248, 11, 403, 137);
		frmAlbumCreator.getContentPane().add(labelTEI);
		
		JLabel labelInfo = new JLabel("ALBUM MANAGER\r\n");
		labelInfo.setFont(new Font("Roboto", Font.BOLD, 15));
		labelInfo.setHorizontalAlignment(SwingConstants.CENTER);
		labelInfo.setBounds(397, 137, 155, 14);
		frmAlbumCreator.getContentPane().add(labelInfo);
		
		JLabel labelInfo1 = new JLabel("\u03B3\u03B9\u03B1 \u03C4\u03BF \u03B5\u03C1\u03B3\u03B1\u03C3\u03C4\u03B7\u03C1\u03B9\u03B1\u03BA\u03CC \u03BA\u03BF\u03BC\u03BC\u03AC\u03C4\u03B9 \u03C4\u03BF\u03C5 \u03BC\u03B1\u03B8\u03AE\u03BC\u03B1\u03C4\u03BF\u03C2:");
		labelInfo1.setHorizontalAlignment(SwingConstants.CENTER);
		labelInfo1.setFont(new Font("Roboto", Font.PLAIN, 12));
		labelInfo1.setBounds(350, 150, 260, 14);
		frmAlbumCreator.getContentPane().add(labelInfo1);
		
		JLabel labelInfo2 = new JLabel("\u039C\u0395\u0398\u039F\u0394\u039F\u039B\u039F\u0393\u0399\u0391 \u03A0\u03A1\u039F\u0393\u03A1\u0391\u039C\u039C\u0391\u03A4\u0399\u03A3\u039C\u039F\u03A5");
		labelInfo2.setHorizontalAlignment(SwingConstants.CENTER);
		labelInfo2.setFont(new Font("Roboto", Font.BOLD, 12));
		labelInfo2.setBounds(348, 175, 260, 14);
		frmAlbumCreator.getContentPane().add(labelInfo2);
		
		JLabel labelInfo3 = new JLabel("\u0388\u03C4\u03BF\u03C2 2020 - 2021");
		labelInfo3.setHorizontalAlignment(SwingConstants.CENTER);
		labelInfo3.setFont(new Font("Roboto", Font.PLAIN, 12));
		labelInfo3.setBounds(347, 188, 260, 14);
		frmAlbumCreator.getContentPane().add(labelInfo3);
		
		JLabel lbln = new JLabel("\u03A6\u03BF\u03B9\u03C4\u03B7\u03C4\u03AD\u03C2");
		lbln.setHorizontalAlignment(SwingConstants.CENTER);
		lbln.setFont(new Font("Roboto", Font.BOLD | Font.ITALIC, 12));
		lbln.setBounds(395, 211, 155, 14);
		frmAlbumCreator.getContentPane().add(lbln);
		
		JLabel lbln_1 = new JLabel("\u03A4\u03A3\u0391\u039A\u039D\u0391\u039A\u0397\u03A3 \u0391\u03A7\u0399\u039B\u039B\u0395\u0391\u03A3 - \u0391\u0395\u039C 4497");
		lbln_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbln_1.setFont(new Font("Roboto", Font.PLAIN, 12));
		lbln_1.setBounds(359, 230, 233, 14);
		frmAlbumCreator.getContentPane().add(lbln_1);
		
		JLabel lbln_1_1 = new JLabel("\u039A\u0391\u03A1\u0391\u03A0\u0399\u039B\u0399\u0391\u03A6\u0397\u03A3 \u0393\u0395\u03A9\u03A1\u0393\u0399\u039F\u03A3 - \u0391\u0395\u039C 4679");
		lbln_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbln_1_1.setFont(new Font("Roboto", Font.PLAIN, 12));
		lbln_1_1.setBounds(359, 247, 233, 14);
		frmAlbumCreator.getContentPane().add(lbln_1_1);
		
		JLabel lbln_1_1_1 = new JLabel("\u03A0\u0391\u039B\u039F\u03A5\u039A\u03A4\u03A3\u039F\u0393\u039B\u039F\u03A5 \u039C\u0395\u039B\u0395\u03A4\u0397\u03A3  - \u0391\u0395\u039C 4636");
		lbln_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbln_1_1_1.setFont(new Font("Roboto", Font.PLAIN, 12));
		lbln_1_1_1.setBounds(359, 265, 233, 14);
		frmAlbumCreator.getContentPane().add(lbln_1_1_1);
		
		JLabel lbln_1_1_1_1 = new JLabel("\u039A\u039F\u03A5\u03A4\u03A1\u039F\u039C\u0391\u039D\u039F\u03A3 \u039A\u03A9\u039D\u03A3\u03A4\u0391\u039D\u03A4\u0399\u039D\u039F\u03A3 - \u0391\u0395\u039C 4634");
		lbln_1_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbln_1_1_1_1.setFont(new Font("Roboto", Font.PLAIN, 12));
		lbln_1_1_1_1.setBounds(351, 282, 247, 14);
		frmAlbumCreator.getContentPane().add(lbln_1_1_1_1);
		
		JButton btnAlbumManager = new JButton("ALBUM MANAGER");
		btnAlbumManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				AlbumManager.main(null);
			}
		});
		btnAlbumManager.setForeground(Color.BLACK);
		btnAlbumManager.setFont(new Font("Roboto", Font.BOLD, 10));
		btnAlbumManager.setBackground(new Color(50, 217, 153));
		btnAlbumManager.setBounds(46, 149, 129, 31);
		frmAlbumCreator.getContentPane().add(btnAlbumManager);
		
		JLabel labelManager = new JLabel("");
		labelManager.setIcon(new ImageIcon("C:\\Users\\AceTsak\\Desktop\\list1.png"));
		labelManager.setBounds(170, 141, 76, 49);
		frmAlbumCreator.getContentPane().add(labelManager);
		
		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				 if (JOptionPane.showConfirmDialog(frmAlbumCreator,"Do you really want to exit the application?","TEI Album Creator",
				            JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
				            System.exit(0);
			}
		});
		btnExit.setForeground(Color.BLACK);
		btnExit.setFont(new Font("Roboto", Font.BOLD, 10));
		btnExit.setBackground(new Color(50, 217, 153));
		btnExit.setBounds(58, 281, 100, 31);
		frmAlbumCreator.getContentPane().add(btnExit);
	}
}
