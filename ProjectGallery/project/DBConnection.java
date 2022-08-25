import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection 

{
	public static Connection con;
	public static Connection getConnection()
	
	{
		try
		
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/mysql";
			con = DriverManager.getConnection(url,"root","");
		}
		catch(Exception ex) 
		{
			System.out.println("From DBConnection :: " + ex);
		}
	return con;
	}
}