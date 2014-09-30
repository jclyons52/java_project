package third_tier.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import second_tier.PersonalTask;
import second_tier.Task;
import third_tier.ConnectionManager;

public class PersonalTaskManager {
private static Connection conn = ConnectionManager.getInstance().getConnection();
	
	public static ArrayList<Task> display() throws SQLException {

		String sql = "SELECT * FROM personal_tasks";
		ArrayList<Task> tasks = new ArrayList<Task>();
		try (
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				){

			
			while (rs.next()) {
				PersonalTask bean = new PersonalTask();
				bean.setTitle(rs.getString("title"));
				bean.setDetails(rs.getString("details"));
				bean.setDueDate(rs.getString("due_date"));
				bean.setImportant(rs.getBoolean("important"));
				bean.setRequirements(rs.getString("requirements"));
				bean.setCost(rs.getDouble("cost"));
				tasks.add(bean);
			}
		}
		return tasks;
	}
	
	public static PersonalTask display(int personalTaskId) throws SQLException {

		String sql = "SELECT * FROM personal_tasks WHERE id = ?";
		ResultSet rs = null;

		try (
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			stmt.setInt(1, personalTaskId);
			rs = stmt.executeQuery();

			if (rs.next()) {
				PersonalTask bean = new PersonalTask();
				bean.setTitle(rs.getString("title"));
				bean.setDetails(rs.getString("details"));
				bean.setDueDate(rs.getString("due_date"));
				bean.setImportant(rs.getBoolean("important"));
				bean.setRequirements(rs.getString("requirements"));
				bean.setCost(rs.getDouble("cost"));
				 return bean;
			} else {
				return null;
			}

		} catch (SQLException e) {
			System.err.println(e);
			return null;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}
	
	public static boolean save(PersonalTask bean) throws Exception{
		int id = bean.getId();
		if( id != 0){
			return update(bean);
		}else{
			return insert(bean);
		}
	}
	
	private static boolean insert(PersonalTask bean) throws Exception {

		String sql = "INSERT into study_task (title, details, due_date, important, requirements, cost) " +
				"VALUES (?, ?, ?, ?, ?, ?)";
		ResultSet keys = null;
		try (
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				) {
			
			stmt.setString(1, bean.getTitle());
			stmt.setString(2, bean.getDetails());
			stmt.setString(3, bean.getDueDate());
			stmt.setBoolean(4, bean.getImportant());
			stmt.setString(5, bean.getRequirements());
			stmt.setDouble(6, bean.getCost());
			int affected = stmt.executeUpdate();
			
			if (affected == 1) {
				keys = stmt.getGeneratedKeys();
				keys.next();
				int newKey = keys.getInt(1);
				bean.setId(newKey);
			} else {
				System.err.println("No rows affected");
				return false;
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		} finally{
			if (keys != null) keys.close();
		}
		return true;
	}
	
	private static boolean update(PersonalTask bean) throws Exception {

		String sql =
				"UPDATE study_task SET " +
				"title = ?, details = ?, due_date = ?, important = ?, requirements = ?, cost = ? " +
				"WHERE id = ?";
		try (
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			
			stmt.setString(1, bean.getTitle());
			stmt.setString(2, bean.getDetails());
			stmt.setString(3, bean.getDueDate());
			stmt.setBoolean(4, bean.getImportant());
			stmt.setString(5, bean.getRequirements());
			stmt.setDouble(6, bean.getCost());
			stmt.setInt(7, bean.getId());
			
			int affected = stmt.executeUpdate();
			if (affected == 1) {
				return true;
			} else {
				return false;
			}
			
		}
		catch(SQLException e) {
			System.err.println(e);
			return false;
		}

	}
}