package third_tier;

	import java.util.ArrayList;
	import java.io.*;

	import second_tier.*;

	public class TaskFileReader {
		@SuppressWarnings("unchecked")
		public ArrayList<Task> openFile (String fileName) throws ClassNotFoundException, IOException, FileNotFoundException {

			ObjectInputStream objIn = new ObjectInputStream (new FileInputStream (fileName));
			ArrayList<Task> list = new ArrayList<Task>();
			list = (ArrayList<Task>) objIn.readObject(); // we know it's an ArrayList<Task>!!
			objIn.close ();

			return list;
		}
	}

