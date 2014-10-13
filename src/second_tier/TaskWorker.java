
package second_tier;

import java.sql.SQLException;
import java.util.ArrayList;

import third_tier.tables.PersonalTaskManager;
import third_tier.tables.StudyTaskManager;

/**
 * handles interaction between UI and IO layers of project
 */
public class TaskWorker {
	
	public ArrayList<Task> open () throws SQLException {
		//opens file from storage
		ArrayList<Task> list = StudyTaskManager.display();
		list.addAll(PersonalTaskManager.display());
		return list;
	}

	public boolean saveFile (ArrayList<Task> list) throws Exception {
		//saves file to storage
		boolean result = false;
		for (Task task : list) {
			if (task instanceof StudyTask) {
				result = StudyTaskManager.save((StudyTask) task);
			} else {
				result = PersonalTaskManager.save((PersonalTask) task);
			}
		}
		return result;
	}

}
