package third_tier.tables;

import java.sql.SQLException;
import java.util.ArrayList;

import second_tier.StudyTask;
import second_tier.Task;


public interface DatabaseObjectManager {
	public ArrayList<Task> display() throws SQLException;
	public Task display(int Id);
	public boolean save(Task bean);
}
