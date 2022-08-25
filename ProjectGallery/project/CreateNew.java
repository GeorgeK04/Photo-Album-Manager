import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class CreateNew {

	private JFrame frmAlbumManager;
	private JTextField albumNameTxt;
	static int record;
	static PreparedStatement pst;
	private JTextField delAlbumTxt;

	//Launch the application:
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateNew window = new CreateNew();
					window.frmAlbumManager.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Create the application:
	public CreateNew() 
	{
		initialize();
	}

	//Initialize the contents of the frame.
	private void initialize() 
	{
		frmAlbumManager = new JFrame();
		frmAlbumManager.setTitle("Album Manager");
		frmAlbumManager.getContentPane().setBackground(new Color(204, 255, 255));
		frmAlbumManager.setBounds(100, 100, 400, 162);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAlbumManager.getContentPane().setLayout(null);
		
		JLabel labelIn = new JLabel("Insert name for new album:");
		labelIn.setFont(new Font("Roboto", Font.PLAIN, 12));
		labelIn.setBounds(10, 11, 167, 14);
		frmAlbumManager.getContentPane().add(labelIn);
		
		albumNameTxt = new JTextField();
		albumNameTxt.setBounds(174, 9, 175, 20);
		frmAlbumManager.getContentPane().add(albumNameTxt);
		albumNameTxt.setColumns(10);
		
		JButton btnCreateAlbum = new JButton("CREATE ALBUM");
		btnCreateAlbum.setForeground(new Color(0, 0, 0));
		btnCreateAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Connection con = DBConnection.getConnection();
				String album = albumNameTxt.getText();
				String SQL = "CREATE TABLE IF NOT EXISTS images." +album+ " (PID Integer PRIMARY KEY AUTO_INCREMENT NOT NULL, Photo mediumblob, Location VARCHAR(50), Date VARCHAR(50), Tag VARCHAR(50), Size VARCHAR(50))";
			
				try {
					pst = con.prepareStatement(SQL);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			
				int response = JOptionPane.showConfirmDialog(null, "Create Album?", "Confirm...", JOptionPane.YES_NO_OPTION);
				if(response == 0)
				{
					try {
						record = pst.executeUpdate();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(record == 0)
					{
						JOptionPane.showMessageDialog(null, "Album has been Created","OK",1);
					}
					else 
		        	{
		        		JOptionPane.showMessageDialog(null, "Sorry...","Can't create album!",1);
		        	}
				}
				if(response == 1) {
                	JOptionPane.showMessageDialog(null, "Cancelled By User","Cancel...",1);
                }
			}
		});
		btnCreateAlbum.setBackground(SystemColor.activeCaption);
		btnCreateAlbum.setFont(new Font("Roboto", Font.BOLD, 12));
		btnCreateAlbum.setBackground(new Color(50, 217, 153));
		btnCreateAlbum.setBounds(197, 38, 130, 23);
		frmAlbumManager.getContentPane().add(btnCreateAlbum);
		
		JLabel labelDel = new JLabel("Insert an album name to delete:");
		labelDel.setFont(new Font("Roboto", Font.PLAIN, 12));
		labelDel.setBounds(10, 73, 191, 14);
		frmAlbumManager.getContentPane().add(labelDel);
		
		delAlbumTxt = new JTextField();
		delAlbumTxt.setColumns(10);
		delAlbumTxt.setBounds(191, 71, 175, 20);
		frmAlbumManager.getContentPane().add(delAlbumTxt);
		
		JButton btnDelAlbum = new JButton("DELETE ALBUM");
		btnDelAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Connection con = DBConnection.getConnection();
				String album = delAlbumTxt.getText();
				String SQL = "DROP TABLE images." +album;
			
				try {
					pst = con.prepareStatement(SQL);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			
				int response = JOptionPane.showConfirmDialog(null, "Are you sure you wish to delete this Album? Data cannot be recovered!", "Confirm...", JOptionPane.YES_NO_OPTION);
				if(response == 0)
				{
					try {
						record = pst.executeUpdate();
						DatabaseMetaData dbm = con.getMetaData();
						ResultSet tables = dbm.getTables(null, null, album, null);
						if (tables.next()) 
						{
								//Table exists
								if(record == 1)
								{
									JOptionPane.showMessageDialog(null, "Album has been successfully deleted!","OK",1);
								}
								else if(record == 0)
						        {
						        	JOptionPane.showMessageDialog(null, "Sorry...","Can't delete Album!",1);
						        }	
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Album has not been found on the database!","Error",1);
						e1.printStackTrace();
					}
					
				}
				if(response == 1) {
                	JOptionPane.showMessageDialog(null, "Cancelled By User","Paused",1);
                }
			}
		});
		btnDelAlbum.setFont(new Font("Roboto", Font.BOLD, 12));
		btnDelAlbum.setBackground(new Color(50, 217, 153));
		btnDelAlbum.setBounds(219, 95, 123, 23);
		frmAlbumManager.getContentPane().add(btnDelAlbum);
	}
}
