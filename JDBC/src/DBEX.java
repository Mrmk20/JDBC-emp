import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;

class DBAccessor {
	Connection conn;
	Statement stmt;
	
	DBAccessor() throws SQLException{
		Driver myDriver = new com.mysql.cj.jdbc.Driver();
		DriverManager.registerDriver(myDriver);
		String url = "jdbc:mysql://localhost:3306/empreg";
		String username = "root";
		String password = "Mrmk1620";
		conn = DriverManager.getConnection(url, username, password);
		stmt = conn.createStatement();
	}
	
	void insertData() throws SQLException {
		stmt.executeUpdate("INSERT INTO employee" + "(emp_id,name)" + "VALUES (1, 'Alpha')");
		stmt.executeUpdate("INSERT INTO employee" + "(emp_id,name)" + "VALUES (2, 'Bravo')");
		stmt.executeUpdate("INSERT INTO employee" + "(emp_id,name)" + "VALUES (3, 'Charlie')");
		stmt.executeUpdate("INSERT INTO employee" + "(emp_id,name)" + "VALUES (4, 'Charlie')");
		stmt.close();
	}
	/**
	 * @throws SQLException
	 */
	void retrieveData() throws SQLException {	
		ResultSet res;
		stmt = conn.createStatement();
		res = stmt.executeQuery("SELECT * FROM employee");
		System.out.println("EMPLOYEE TABLE");
		while(res.next()) {
			System.out.println(res.getInt(1) + " " + res.getString(2));
		}
		System.out.println("\n");
		res.close();
	}
	void updateData() throws SQLException {
		int nRows;
		stmt = conn.createStatement();
		nRows = stmt.executeUpdate("UPDATE employee SET name = 'Delta' WHERE emp_id = 4");
		System.out.println("No.of rows affected is " + nRows + "\n");
		stmt.close();
	}
	void deleteData() throws SQLException {
		int nRows;
		stmt = conn.createStatement();
		nRows = stmt.executeUpdate("DELETE FROM employee WHERE emp_id = 3");
		System.out.println("No.of rows affected is " + nRows + "\n");
		stmt.close();
	}
	void truncateData() throws SQLException {
		int nRows;
		stmt = conn.createStatement();
		nRows = stmt.executeUpdate("TRUNCATE employee");
		System.out.println("No.of rows affected is " + nRows + "\n");
		stmt.close();
	}
	
	void displayAboutResultData() throws SQLException {
		ResultSetMetaData rsmd;
		int nullable;

		ResultSet res = stmt.executeQuery("SELECT * FROM employee");
		rsmd = res.getMetaData();
		System.out.println("The no.of columns is"+ rsmd.getColumnCount());
		
		System.out.println("The column name is " + rsmd.getColumnName(1));
		System.out.println("The db-specific name of the column is " +
		rsmd.getColumnTypeName(1));
		nullable = rsmd.isNullable(1);
		
		System.out.println("The column name is " + rsmd.getColumnName(2));
		System.out.println("The db-specific name of the column is " + 
		rsmd.getColumnTypeName(2));
		nullable = rsmd.isNullable(2);
		stmt.close();
	}
	
	void displayDatabaseMetaData() throws SQLException {
		DatabaseMetaData dmd;
		dmd = conn.getMetaData();
		
		System.out.println("The database url is " + dmd.getURL());
		System.out.println("The name of the user is " + dmd.getUserName());
		System.out.println("The name of the driver is " + dmd.getDriverName());
		if(dmd.isReadOnly()) {
			String x = "The db is read only";
			System.out.println(x);
		}else {
			
			System.out.println("The db is not read only");
		}
	}
}

public class DBEX {

	public static void main(String[] args) throws SQLException {
		try {
		DBAccessor dba = new DBAccessor();
		dba.insertData();
		dba.retrieveData();
		dba.updateData();
		dba.retrieveData();
		dba.deleteData();
		dba.retrieveData();
		dba.displayAboutResultData();
		dba.displayDatabaseMetaData();
		dba.truncateData();
		dba.retrieveData();
		}
		
		catch (SQLException e) {
			System.out.println(e);
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}
