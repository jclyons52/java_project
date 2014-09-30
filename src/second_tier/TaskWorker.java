
package second_tier;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import third_tier.TaskFileReader;
import third_tier.TaskFileWriter;

/**
 * handles interaction between UI and IO layers of project
 */
public class TaskWorker {
	
	public ArrayList<Task> openFile (String fileName) throws ClassNotFoundException, IOException, FileNotFoundException {
		//opens file from storage
		TaskFileReader pfr = new TaskFileReader ();
		ArrayList<Task> list = pfr.openFile (fileName);
		return list;
	}

	public void saveFile (String fileName, DataStorage list) throws IOException {
		//saves file to storage
		TaskFileWriter pfw = new TaskFileWriter ();
		pfw.saveFile (fileName, list);
	}

}
