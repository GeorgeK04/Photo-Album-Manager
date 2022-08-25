import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class AlbumManager {

	private JFrame frame;
	private JTable table;
	String url = "jdbc:mysql://localhost:3306/images";
	ArrayList<String> tableNames = new ArrayList<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlbumManager window = new AlbumManager();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public AlbumManager() throws SQLException {
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

	
	JComboBox<String> comboBoxAlbums = new JComboBox<String>(new Vector<String>(tableNames));
	//Initialize the contents of the frame.
	private void initialize() throws SQLException {
		
		albumPopulate();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 525, 366);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(204, 255, 255));
		frame.getContentPane().setLayout(null);
		
		JButton btnTable = new JButton("Show Table");
		btnTable.setFont(new Font("Roboto", Font.PLAIN, 12));
		btnTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Connection con = DBConnection.getConnection();
				try {
				java.sql.Statement statement = con.createStatement();
				ResultSet rs = statement.executeQuery("SELECT * FROM images." +comboBoxAlbums.getSelectedItem());
				table.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (Exception e1)	{
					e1.printStackTrace();
				}
			}
		});
		btnTable.setBounds(14, 9, 107, 23);
		btnTable.setBackground(new Color(50, 217, 153));
		frame.getContentPane().add(btnTable);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(13, 42, 483, 273);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		//JComboBox comboBoxAlbums = new JComboBox();
		comboBoxAlbums.setModel(new DefaultComboBoxModel<String>(tableNames.toArray(new String[0])));
		comboBoxAlbums.setBounds(230, 10, 102, 22);
		frame.getContentPane().add(comboBoxAlbums);
		
		JLabel labelAlbum = new JLabel("Select an album:");
		labelAlbum.setFont(new Font("Roboto", Font.PLAIN, 12));
		labelAlbum.setBounds(134, 14, 98, 14);
		frame.getContentPane().add(labelAlbum);
	}
}
