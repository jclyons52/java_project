package third_tier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.ResultSetMetaData;

public class PDatabase {
	
	/**
	 * Object array used to hold the results retrieved from the database.
	 */
	private Object[][] databaseResults;
	/**
	 * ResultSet that holds the information retrieved from database.
	 */
	public ResultSet rows;
	/**
	 * String array used to hold the column names for the table.
	 */
	public Vector<String> columns;
	/**
	 * The table model used for manipulation of the JTable.
	 */
	public DefaultTableModel defaultTableModel;
	/**
	 * Connection object used to interact with the database.
	 */
	private Connection conn = null;
	
	/**
	 * Database constructor
	 * @throws SQLException 
	 */
	public PDatabase() throws SQLException {
		// Connect to database and retrieve result set
		conn = ConnectionManager.getInstance().getConnection();
		String sql = "SELECT * FROM personal_tasks";
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		rows = stmt.executeQuery(sql);
		ResultSetMetaData metaData = (ResultSetMetaData) rows.getMetaData();

		// Create columns names
		columns = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columns.add(metaData.getColumnName(column));
		}
		// Create some data
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rows.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rows.getObject(columnIndex));
			}
			data.add(vector);
		}
		defaultTableModel = new DefaultTableModel(data, columns);
		
		
	}
}
