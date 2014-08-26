
package second_tier;

import java.util.ArrayList;

public class DataStorage {
	// attribute
	ArrayList<Task> dStore;
	// constructor
	public DataStorage () {
		dStore = new ArrayList<Task>();
	}
	// methods
	public void addAll (ArrayList<Task> aLP) {
		dStore.addAll (aLP);
	}
	public void add (Task p) {
		if (p instanceof StudyTask) {
			dStore.add ((StudyTask) p);
		} else {
			dStore.add ((PersonalTask) p);
		}
	}
	public int size () {
		return dStore.size();
	}
	public Task get (int index) {
		return dStore.get (index);
	}
	public ArrayList<Task> getArrayList() {
		return dStore;
	}
} // end class DataStorage