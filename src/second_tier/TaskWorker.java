package second_tier;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import third_tier.TaskFileReader;
import third_tier.TaskFileWriter;


public class TaskWorker {
	
	public ArrayList<Task> openFile (String fileName) throws ClassNotFoundException, IOException, FileNotFoundException {
		TaskFileReader pfr = new TaskFileReader ();
		ArrayList<Task> list = pfr.openFile (fileName);
		return list;
	}

	public void saveFile (String fileName, DataStorage list) throws IOException {
		TaskFileWriter pfw = new TaskFileWriter ();
		pfw.saveFile (fileName, list);
	}

}
