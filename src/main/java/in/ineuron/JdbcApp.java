package in.ineuron;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;


public class JdbcApp 
{
	
	private static Logger logger = Logger.getLogger(JdbcApp.class);
	
	static {
		SimpleLayout layout = new SimpleLayout();
		ConsoleAppender appender = new ConsoleAppender(layout);
		logger.addAppender(appender);
		logger.setLevel(Level.INFO);
	}
	
	public static void main( String[] args )
	{
		logger.debug("Control entering the main() method...");
		
		Connection connection = null;
		PreparedStatement psmt = null;
		ResultSet resultSet = null;

		String sqlSelectQuery = "select sid,sname,sage,saddress from student";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			logger.debug("Driver loaded successfully...");

			String url = "jdbc:mysql:///pwskill";
			String user = "root";
			String password = "****";

			connection = DriverManager.getConnection(url, user, password);
			logger.info("Connection Established Successfully...");

			psmt = connection.prepareStatement(sqlSelectQuery);
			logger.info("PreparedStatement Object created successfully...");

			resultSet = psmt.executeQuery();
			logger.info("ResultSet Object created by executing query...");

			System.out.println();
			System.out.println("SID\tSNAME\tSAGE\tSADDRESS");
			while (resultSet.next()) {
				System.out.println(resultSet.getInt(1) + "\t" + resultSet.getString(2) + "\t" + resultSet.getInt(3) + "\t" +
						resultSet.getString(4));
			}
		}
		catch (ClassNotFoundException ce) {
			ce.printStackTrace();
			logger.error("ClassNotFoundException Occred...");
		}
		catch(SQLException ce) {
			ce.printStackTrace();
			logger.error("SQLException Occred => " + ce.getMessage() + " => " + ce.getErrorCode());
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.fatal("Unknown Error Occured...");
		}
		finally {
			if(resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("SQLException Occred => " + e.getMessage() + " => " + e.getErrorCode());
				}
			if(psmt != null) {
				try {
					psmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("SQLException Occred => " + e.getMessage() + " => " + e.getErrorCode());
				}
			}
			if(connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("SQLException Occred => " + e.getMessage() + " => " + e.getErrorCode());
				}
		}

		logger.debug("Control exiting the main() method...");
	}
}
