
package second_tier;

import java.util.ArrayList;
/**
 * handles interaction of UI and models of project
 */
public class DataStorage {
	// attribute
	ArrayList<Task> dStore;
	// constructor
	public DataStorage () {
		dStore = new ArrayList<Task>();
	}
	// methods
	public void addAll (ArrayList<Task> aLP) {
		//adds all tasks to array list
		dStore.addAll (aLP);
	}
	public void add (Task p) {
		// adds individual task to array list
		if (p instanceof StudyTask) {
			dStore.add ((StudyTask) p);
		} else {
			dStore.add ((PersonalTask) p);
		}
	}
	public int size () {
		//returns number of entries in array list
		return dStore.size();
	}
	public Task get (int index) {
		//gets individual member of array list
		return dStore.get (index);
	}
	public ArrayList<Task> getArrayList() {
		//returns entire array list
		return dStore;
	}
} // end class DataStorage