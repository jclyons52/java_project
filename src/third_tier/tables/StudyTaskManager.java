package third_tier.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import second_tier.StudyTask;
import second_tier.Task;
import third_tier.ConnectionManager;

public class StudyTaskManager {
	
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	
	public static ArrayList<Task> display() throws SQLException {

		String sql = "SELECT * FROM study_tasks";
		ArrayList<Task> tasks = new ArrayList<Task>();
		try (
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				){

			
			while (rs.next()) {
				StudyTask bean = new StudyTask();
				bean.setId(rs.getInt("id"));
				bean.setTitle(rs.getString("title"));
				bean.setDetails(rs.getString("details"));
				bean.setDueDate(rs.getString("due_date"));
				bean.setGraded(rs.getBoolean("graded"));
				bean.setSubject(rs.getString("subject"));
				bean.setTotalMarks(rs.getDouble("total_marks"));
				tasks.add(bean);
			}
		}
		return tasks;
	}
	
	public static StudyTask display(int studyTaskId) throws SQLException {

		String sql = "SELECT * FROM study_tasks WHERE id = ?";
		ResultSet rs = null;

		try (
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			stmt.setInt(1, studyTaskId);
			rs = stmt.executeQuery();

			if (rs.next()) {
				StudyTask bean = new StudyTask();
				bean.setTitle(rs.getString("title"));
				bean.setDetails(rs.getString("details"));
				bean.setDueDate(rs.getString("due_date"));
				bean.setGraded(rs.getBoolean("graded"));
				bean.setSubject(rs.getString("subject"));
				bean.setTotalMarks(rs.getDouble("total_marks"));
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
	
	public static boolean save(StudyTask bean) throws Exception{
		int id = bean.getId();
		if( id != 0){
			return update(bean);
		}else{
			return insert(bean);
		}
	}
	
	private static boolean insert(StudyTask bean) throws Exception {

		String sql = "INSERT into study_tasks (title, details, due_date, graded, subject, total_marks) " +
				"VALUES (?, ?, ?, ?, ?, ?)";
		ResultSet keys = null;
		try (
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				) {
			
			stmt.setString(1, bean.getTitle());
			stmt.setString(2, bean.getDetails());
			stmt.setString(3, bean.getDueDate());
			stmt.setBoolean(4, bean.getGraded());
			stmt.setString(5, bean.getSubject());
			stmt.setDouble(6, bean.getTotalMarks());
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
	
	private static boolean update(StudyTask bean) throws Exception {

		String sql =
				"UPDATE study_tasks SET " +
				"title = ?, details = ?, due_date = ?, graded = ?, subject = ?, total_marks = ? " +
				"WHERE id = ?";
		try (
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			
			stmt.setString(1, bean.getTitle());
			stmt.setString(2, bean.getDetails());
			stmt.setString(3, bean.getDueDate());
			stmt.setBoolean(4, bean.getGraded());
			stmt.setString(5, bean.getSubject());
			stmt.setDouble(6, bean.getTotalMarks());
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
	
	public static boolean delete(int studyTaskId) throws SQLException{
		String sql = "DELETE FROM study_tasks WHERE id = ? LIMIT 1";
		try (
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			
			stmt.setInt(1, studyTaskId);
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
