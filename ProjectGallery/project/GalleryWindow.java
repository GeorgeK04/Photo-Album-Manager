import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.awt.SystemColor;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.EtchedBorder;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

public class GalleryWindow 
{

	private JFrame frmProjectGallery;
	private JPanel panelInsert;
	private JPanel panelGallery;
	private JPanel panelShowImage;
	private JTextField searchTxt;
	String url = "jdbc:mysql://localhost:3306/images";
	private JLabel labelShow;
	private JPanel panelPreviewImage;
	private JLabel labelPreview;
	
	static String path;
	
	private JTextField insertLocationTxt;
	private JTextField insertDateTxt;
	private JTextField searchTagTxt;
	private JTextField insertTagTxt;
	ArrayList<String> tableNames = new ArrayList<String>();
	

	//Launch the application:
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					GalleryWindow window = new GalleryWindow();
					window.frmProjectGallery.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Create the application:
	public GalleryWindow() throws SQLException 
	{
		initialize();
	}

	public void albumPopulate() throws SQLException
	{
		Connection con = DriverManager.getConnection(url,"root","");
		DatabaseMetaData md = con.getMetaData();
		ResultSet res = md.getTables("images", "", "", null);
		//ResultSetMetaData resMeta = res.getMetaData();
		//int columnCount = resMeta.getColumnCount();
		while(res.next())
		{
			System.out.println(res.getString("TABLE_NAME"));
			String name = res.getString("TABLE_NAME");
			tableNames.add(name);
			/*for(int i=1;i<columnCount;i++)
			{
				String columnName = resMeta.getColumnName(i);
				System.out.format("%s:%s\n", columnName, res.getString(i));
			}
			*/
		}
	}
	

	JComboBox<String> viewAlbumBox = new JComboBox<String>(new Vector<String>(tableNames));
		
	//Initialize the contents of the frame:
	private void initialize() throws SQLException 
	{	
		
		albumPopulate();
		
		//Frame construction and options
		frmProjectGallery = new JFrame();
		frmProjectGallery.setTitle("Project Gallery");
		frmProjectGallery.getContentPane().setBackground(Color.LIGHT_GRAY);
		frmProjectGallery.setBounds(100, 100, 1376, 786);
		//frmProjectGallery.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProjectGallery.getContentPane().setLayout(null);
		frmProjectGallery.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//frame.setUndecorated(true);
		frmProjectGallery.setVisible(true);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 1340, 725);
		frmProjectGallery.getContentPane().add(tabbedPane);
		
		JLabel labelIndex = new JLabel("0");
		labelIndex.setFont(new Font("Roboto Black", Font.PLAIN, 14));
		labelIndex.setBounds(1156, 50, 17, 14);
		
		panelGallery = new JPanel();
		panelGallery.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, null, SystemColor.activeCaption));
		tabbedPane.addTab("Gallery", null, panelGallery, "Preview the current photos in the gallery");
		panelGallery.setLayout(null);
		
		JLabel labelLocation = new JLabel("Location:");
		labelLocation.setFont(new Font("Roboto Black", Font.PLAIN, 12));
		labelLocation.setBounds(662, 620, 63, 14);
		panelGallery.add(labelLocation);
		
		JLabel labelLocationTxt = new JLabel("");
		labelLocationTxt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelLocationTxt.setBounds(721, 620, 146, 14);
		panelGallery.add(labelLocationTxt);
		
		JLabel labelDateTxt = new JLabel("");
		labelDateTxt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelDateTxt.setBounds(721, 650, 146, 14);
		panelGallery.add(labelDateTxt);
		tabbedPane.setBackgroundAt(0, SystemColor.menu);
		tabbedPane.setForegroundAt(0, Color.BLACK);
		
		
		
		//GO TO NEXT PHOTO BUTTON
		//==============================================================================================
		JButton btnNext = new JButton(">>\r\n");
		btnNext.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
						Connection con = DriverManager.getConnection(url,"root","");
						int nextID = Integer.parseInt(labelIndex.getText()) + 1;
						java.sql.Statement statement = con.createStatement();
						ResultSet rsExtra = statement.executeQuery("SELECT * FROM images." +viewAlbumBox.getSelectedItem()+ " WHERE PID ='"+nextID+"'");
						if(rsExtra.next())
						{
							byte[] img = rsExtra.getBytes("Photo");
							ImageIcon image = new ImageIcon(img);
							Image im = image.getImage();
							Image myImg = im.getScaledInstance(labelShow.getWidth(), labelShow.getHeight(), java.awt.Image.SCALE_SMOOTH);
							ImageIcon newImage = new ImageIcon(myImg);
							labelShow.setIcon(newImage);
							labelIndex.setText(String.valueOf(nextID));
							
							String Loc = rsExtra.getString("Location");
							labelLocationTxt.setText(Loc);
							
							String Date = rsExtra.getString("Date");
							labelDateTxt.setText(Date);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "No Data");
						}
					}
					
				catch (SQLException ex) 
				{
					ex.printStackTrace();
				}				
			}
		});

		try
		{
			Connection con = DriverManager.getConnection(url,"root","");
			java.sql.Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT max(pid) AS MaxID from images."+viewAlbumBox.getSelectedItem());
			rs.next();
			int MaxID = rs.getInt(1);
			if(Integer.parseInt(labelIndex.getText()) < MaxID)
			{
				btnNext.setEnabled(true);
			}
			else if (Integer.parseInt(labelIndex.getText()) == MaxID) btnNext.setEnabled(false);
		}
		catch (Exception ex)
		{
			
		}
		
		btnNext.setBackground(Color.WHITE);
		btnNext.setFont(new Font("Roboto Black", Font.PLAIN, 15));
		btnNext.setBounds(1267, 198, 55, 309);
		panelGallery.add(btnNext);
		
		//GO TO PREVIOUS PHOTO BUTTON
		//==============================================================================================
		JButton btnPrevious = new JButton("<<");
		btnPrevious.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
						Connection con = DriverManager.getConnection(url,"root","");
						int prevID = Integer.parseInt(labelIndex.getText()) - 1;
						java.sql.Statement statement = con.createStatement();
						ResultSet rsExtra = statement.executeQuery("SELECT * FROM images." +viewAlbumBox.getSelectedItem()+ " WHERE PID = '"+ prevID +"'");
						if(rsExtra.next())
						{
							byte[] img = rsExtra.getBytes("photo");
							ImageIcon image = new ImageIcon(img);
							Image im = image.getImage();
							Image myImg = im.getScaledInstance(labelShow.getWidth(), labelShow.getHeight(), java.awt.Image.SCALE_SMOOTH);
							ImageIcon newImage = new ImageIcon(myImg);
							labelShow.setIcon(newImage);
							labelIndex.setText(String.valueOf(prevID));
							
							String Loc = rsExtra.getString("Location");
							labelLocationTxt.setText(Loc);
							
							String Date = rsExtra.getString("Date");
							labelDateTxt.setText(Date);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "No Data");
						}
					}
					
				catch (SQLException ex) 
				{
					ex.printStackTrace();
				}
			}
		});
		
		try
		{
			Connection con = DriverManager.getConnection(url,"root","");
			java.sql.Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT max(pid) AS MaxID from images."+viewAlbumBox.getSelectedItem());
			rs.next();
			int MaxID = rs.getInt(1);
			if(Integer.parseInt(labelIndex.getText()) < MaxID)
			{
				btnPrevious.setEnabled(true);
			}
			else if (Integer.parseInt(labelIndex.getText()) == MaxID) btnPrevious.setEnabled(false);
		}
		catch (Exception ex)
		{
			
		}
		btnPrevious.setBackground(Color.WHITE);
		btnPrevious.setFont(new Font("Roboto Black", Font.PLAIN, 15));
		btnPrevious.setBounds(10, 200, 55, 309);
		panelGallery.add(btnPrevious);
		
		
		
		panelShowImage = new JPanel();
		panelShowImage.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panelShowImage.setBounds(96, 75, 1139, 534);
		panelGallery.add(panelShowImage);
		panelShowImage.setLayout(null);
		
		labelShow = new JLabel("");
		labelShow.setBounds(10, 11, 1119, 520);
		
		panelShowImage.add(labelShow);
		
		JLabel lblSearch = new JLabel("Search for an image by using its index:");
		lblSearch.setFont(new Font("Roboto", Font.PLAIN, 12));
		lblSearch.setBounds(98, 619, 225, 14);
		panelGallery.add(lblSearch);
		
		searchTxt = new JTextField();
		searchTxt.setHorizontalAlignment(SwingConstants.CENTER);
		searchTxt.setFont(new Font("Roboto", Font.PLAIN, 12));
		searchTxt.setBounds(325, 615, 28, 27);
		panelGallery.add(searchTxt);
		searchTxt.setColumns(10);

		
		
		//SEARCH IMAGE USING ITS PID (PHOTO ID) FROM THE DATABASE
		//==============================================================================================
		JButton btnSearch = new JButton("SEARCH");
		
		btnSearch.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					Connection con = DriverManager.getConnection(url,"root","");
					java.sql.Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("SELECT * FROM images." +viewAlbumBox.getSelectedItem()+ " WHERE PID ='"+searchTxt.getText()+"'");
					
					if(rs.next())
					{
						System.out.println(viewAlbumBox.getSelectedItem());
						byte[] img = rs.getBytes("Photo");
						ImageIcon image = new ImageIcon(img);
						Image im = image.getImage();
						Image myImg = im.getScaledInstance(labelShow.getWidth(), labelShow.getHeight(), java.awt.Image.SCALE_SMOOTH);
						ImageIcon newImage = new ImageIcon(myImg);
						labelShow.setIcon(newImage);
						labelIndex.setText(searchTxt.getText());
						
					}
					else
					{
						JOptionPane.showMessageDialog(null, "No Data");
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				
				//DISPLAYING THE DATE AND LOCATION ONTO A TEXT FIELD
				//==============================================================================================
				try
				{
					Connection con = DriverManager.getConnection(url,"root","");
					String SQL = "SELECT PID, Location FROM images." +viewAlbumBox.getSelectedItem()+" WHERE PID = '"+searchTxt.getText()+"'";
					java.sql.Statement pst = con.prepareStatement(SQL);
					ResultSet rs = pst.executeQuery(SQL);
					if(rs.next())
					{
						String Loc = rs.getString("Location");
						labelLocationTxt.setText(Loc);
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				
				try
				{
					Connection con = DriverManager.getConnection(url,"root","");
					String SQL = "SELECT PID, Date FROM images." +viewAlbumBox.getSelectedItem()+ " WHERE PID = '"+searchTxt.getText()+"'";
					java.sql.Statement pst = con.prepareStatement(SQL);
					ResultSet rs = pst.executeQuery(SQL);
					if(rs.next())
					{
						String Loc = rs.getString("Date");
						labelDateTxt.setText(Loc);
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		
		btnSearch.setBackground(SystemColor.activeCaption);
		btnSearch.setFont(new Font("Roboto", Font.PLAIN, 12));
		btnSearch.setBounds(368, 617, 89, 23);
		btnSearch.setBorderPainted(false);
		panelGallery.add(btnSearch);
		
		JLabel labelDate = new JLabel("Date:");
		labelDate.setFont(new Font("Roboto Black", Font.PLAIN, 12));
		labelDate.setBounds(662, 649, 63, 14);
		panelGallery.add(labelDate);
		
		//BUTTON TO DELETE CURRENTLY SHOWN CONTENT FROM DATABASE
		//==============================================================================================
		JButton btnDelete = new JButton("DELETE\r\n");
		btnDelete.setToolTipText("To delete an image, put its ID onto the search field.");
		btnDelete.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//String ID = searchTxt.getText();
				String DelID = labelIndex.getText();
				String DelQuery = "DELETE FROM images." +viewAlbumBox.getSelectedItem()+ " WHERE PID = " +DelID;
				
				
				try
				{
					Connection con = DriverManager.getConnection(url,"root",""); 
					PreparedStatement pst = con.prepareStatement(DelQuery);
					if(pst.executeUpdate() == 1)
					{
						JOptionPane.showMessageDialog(null, "Image successfully deleted!");
					}
					else JOptionPane.showMessageDialog(null, "No image exists with this ID");
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		
		btnDelete.setBackground(new Color(250, 128, 114));
		btnDelete.setFont(new Font("Roboto", Font.PLAIN, 12));
		btnDelete.setBounds(467, 617, 89, 23);
		btnDelete.setBorderPainted(false);
		panelGallery.add(btnDelete);
		
		JLabel labelCurIndex = new JLabel("Current index:");
		labelCurIndex.setFont(new Font("Roboto", Font.PLAIN, 14));
		labelCurIndex.setBounds(1065, 50, 89, 14);
		panelGallery.add(labelCurIndex);
		
		
		panelGallery.add(labelIndex);
		
		JLabel lblSearchTags = new JLabel("Search for an image by using related tags:");
		lblSearchTags.setFont(new Font("Roboto", Font.PLAIN, 12));
		lblSearchTags.setBounds(98, 651, 236, 14);
		panelGallery.add(lblSearchTags);
		
		searchTagTxt = new JTextField();
		searchTagTxt.setHorizontalAlignment(SwingConstants.LEFT);
		searchTagTxt.setFont(new Font("Roboto", Font.PLAIN, 12));
		searchTagTxt.setColumns(10);
		searchTagTxt.setBounds(335, 645, 221, 27);
		panelGallery.add(searchTagTxt);
		
		//BUTTON TO SEARCH WITH A TAG:
		//==============================================================================================
		JButton btnSearchTags = new JButton("SEARCH");
		btnSearchTags.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					Connection con = DriverManager.getConnection(url,"root","");
					java.sql.Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("SELECT * FROM images." +viewAlbumBox.getSelectedItem()+ " WHERE TAG = '"+searchTagTxt.getText()+"'");
					if(rs.next())
					{
						int idValue = rs.getInt(1);
						byte[] img = rs.getBytes("photo");
						ImageIcon image = new ImageIcon(img);
						Image im = image.getImage();
						Image myImg = im.getScaledInstance(labelShow.getWidth(), labelShow.getHeight(), java.awt.Image.SCALE_SMOOTH);
						ImageIcon newImage = new ImageIcon(myImg);
						labelShow.setIcon(newImage);
						labelIndex.setText(String.valueOf(idValue));
						
					}
					else
					{
						JOptionPane.showMessageDialog(null, "No Data");
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				
				//DISPLAYING THE DATE AND LOCATION ONTO A TEXT FIELD
				//==============================================================================================
				try
				{
					Connection con = DriverManager.getConnection(url,"root","");
					String SQL = "SELECT TAG, Location FROM images." +viewAlbumBox.getSelectedItem()+ "  WHERE TAG = '"+searchTagTxt.getText()+"'";
					java.sql.Statement pst = con.prepareStatement(SQL);
					ResultSet rs = pst.executeQuery(SQL);
					if(rs.next())
					{
						String Loc = rs.getString("Location");
						labelLocationTxt.setText(Loc);
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				
				try
				{
					Connection con = DriverManager.getConnection(url,"root","");
					String SQL = "SELECT TAG, Date FROM images." +viewAlbumBox.getSelectedItem()+ " WHERE TAG = '"+searchTagTxt.getText()+"'";
					java.sql.Statement pst = con.prepareStatement(SQL);
					ResultSet rs = pst.executeQuery(SQL);
					if(rs.next())
					{
						String Loc = rs.getString("Date");
						labelDateTxt.setText(Loc);
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		
		btnSearchTags.setFont(new Font("Roboto", Font.PLAIN, 12));
		btnSearchTags.setBorderPainted(false);
		btnSearchTags.setBackground(SystemColor.activeCaption);
		btnSearchTags.setBounds(564, 647, 89, 23);
		panelGallery.add(btnSearchTags);
		
		
		
		JLabel labelBox = new JLabel("Select an album to view:");
		labelBox.setFont(new Font("Roboto", Font.PLAIN, 12));
		labelBox.setBounds(96, 16, 152, 14);
		panelGallery.add(labelBox);
			
		panelInsert = new JPanel();
		panelInsert.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(153, 180, 209)));
		tabbedPane.addTab("Insert to Gallery", null, panelInsert, "Edit the gallery by inserting and deleting photos");
		panelInsert.setLayout(null);
		
		panelPreviewImage = new JPanel();
		panelPreviewImage.setLayout(null);
		panelPreviewImage.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panelPreviewImage.setBounds(96, 75, 1139, 534);
		panelInsert.add(panelPreviewImage);
		
		labelPreview = new JLabel("");
		labelPreview.setBounds(10, 7, 1119, 521);
		panelPreviewImage.add(labelPreview);
		
		
		
		//BUTTON TO OPEN AND PREVIEW AN IMAGE WHICH EXISTS ON YOUR COMPUTER
		//==============================================================================================
		JButton btnOpenImage = new JButton("Open Image...");
		btnOpenImage.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Image", "jpg","png");
				chooser.addChoosableFileFilter(filter);
				
				int result = chooser.showSaveDialog(null);
				File selectedFile = chooser.getSelectedFile();
				String filename = selectedFile.getName();
			
				
				if(filename.endsWith(".jpg")||filename.endsWith(".JPG")||filename.endsWith(".PNG")||filename.endsWith(".png")) 
				{
					if(result == JFileChooser.APPROVE_OPTION) 
					{
						path = selectedFile.getAbsolutePath();
						ImageIcon myImage = new ImageIcon(path);
						
						Image img = myImage.getImage();
						Image newImage = img.getScaledInstance(labelPreview.getWidth(), labelPreview.getHeight(), Image.SCALE_SMOOTH);
					
					    ImageIcon image = new ImageIcon(newImage);
					    labelPreview.setIcon(image);
					}
				}
			}
		});
		
		btnOpenImage.setBackground(new Color(176, 224, 230));
		btnOpenImage.setBounds(200, 620, 170, 64);
		panelInsert.add(btnOpenImage);
		
		//JComboBox comboBox = new JComboBox();
		JComboBox<String> saveAlbumBox = new JComboBox<String>(new Vector<String>(tableNames));
		saveAlbumBox.setBounds(573, 46, 136, 22);
		panelInsert.add(saveAlbumBox);
		tabbedPane.setBackgroundAt(1, SystemColor.menu);
		tabbedPane.setForegroundAt(1, Color.BLACK);
		
		//BUTTON TO SAVE THE PREVIEWED IMAGE ONTO THE DATABASE
		//==============================================================================================
		JButton btnSaveChanges = new JButton("Save Changes");
		btnSaveChanges.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				FileInputStream fInStream = null;
				try
				{
					Connection con = DBConnection.getConnection();
					fInStream = new FileInputStream(path);
					
					String locData = insertLocationTxt.getText();
        			String dateData = insertDateTxt.getText();
        			String tagData = insertTagTxt.getText();
        			Path paths = Paths.get(path);
        			long bytes = Files.size(paths);
        			String sizeData = bytes / 1024 + "KiB";
					PreparedStatement pst = con.prepareStatement("INSERT INTO images." +saveAlbumBox.getSelectedItem()+ "(Photo, Location, Date, Tag, Size) VALUES (?,'"+locData+"','"+dateData+"', '"+tagData+"' ,'"+sizeData+"')");
					
					pst.setBinaryStream(1, fInStream);
					int response = JOptionPane.showConfirmDialog(tabbedPane, "Save Image?", "Confirm...", JOptionPane.YES_NO_OPTION);
			        if(response == 0) 
			        {
			        	int record = pst.executeUpdate();
			        	if(record == 1) 
			        	{
			        		JOptionPane.showMessageDialog(tabbedPane, "Data is Stored...","Ok Done",1);
			        	}
			        	else 
			        	{
			        		JOptionPane.showMessageDialog(tabbedPane, "Sorry...","Can't Store Image",1);
			        	}

			        }
                    if(response == 1) {
                    	JOptionPane.showMessageDialog(tabbedPane, "Cancelled By User","Cancel...",1);
                    }
			        
			    }
				catch(Exception ex) 
				{
			    	System.out.println(""+ ex);
			    }
			}
		});
		
		btnSaveChanges.setBackground(new Color(0, 255, 127));
		btnSaveChanges.setBounds(950, 620, 170, 64);
		panelInsert.add(btnSaveChanges);
		
		JLabel labelInLocation = new JLabel("Insert a location for the photo:");
		labelInLocation.setFont(new Font("Roboto", Font.PLAIN, 12));
		labelInLocation.setBounds(96, 24, 170, 14);
		panelInsert.add(labelInLocation);
		
		JLabel labelInDate = new JLabel("Insert a date for the photo:");
		labelInDate.setFont(new Font("Roboto", Font.PLAIN, 12));
		labelInDate.setBounds(96, 49, 170, 14);
		panelInsert.add(labelInDate);
		
		insertLocationTxt = new JTextField();
		insertLocationTxt.setFont(new Font("Roboto", Font.PLAIN, 12));
		insertLocationTxt.setBounds(276, 22, 182, 20);
		panelInsert.add(insertLocationTxt);
		insertLocationTxt.setColumns(10);
		
		insertDateTxt = new JTextField();
		insertDateTxt.setFont(new Font("Roboto", Font.PLAIN, 12));
		insertDateTxt.setColumns(10);
		insertDateTxt.setBounds(276, 47, 182, 20);
		panelInsert.add(insertDateTxt);
		
		JLabel labelInTag = new JLabel("Insert a tag for the picture:");
		labelInTag.setFont(new Font("Roboto", Font.PLAIN, 12));
		labelInTag.setBounds(472, 25, 165, 14);
		panelInsert.add(labelInTag);
		
		insertTagTxt = new JTextField();
		insertTagTxt.setBounds(627, 22, 167, 20);
		panelInsert.add(insertTagTxt);
		insertTagTxt.setColumns(10);

		
		JLabel labelSelAlb = new JLabel("Select an album:");
		labelSelAlb.setFont(new Font("Roboto", Font.PLAIN, 12));
		labelSelAlb.setBounds(472, 50, 104, 14);
		panelInsert.add(labelSelAlb);
		
		//JComboBox viewAlbumBox = new JComboBox();
		viewAlbumBox.setModel(new DefaultComboBoxModel<String>(tableNames.toArray(new String[0])));
		viewAlbumBox.setBounds(241, 13, 112, 22);
		panelGallery.add(viewAlbumBox);
		tabbedPane.setBackgroundAt(1, SystemColor.menu);
		tabbedPane.setForegroundAt(1, Color.BLACK);
	}
}
