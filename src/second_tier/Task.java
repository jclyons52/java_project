
package second_tier;

import java.io.Serializable;

/**
 * abstract superclass of PersonalTask and StudyTask
 */

public abstract class Task implements Serializable{

	private static final long serialVersionUID = 100L;
	// task attributes
	protected String title, details, dueDate;
	protected int id = 0;

	// the constructors
	
	Task(){};
	Task (String aTitle, String theDetails, String dueWhen) {
		dueDate = dueWhen;
		title = aTitle;
		details = theDetails;
	}

	// the mutators
	
	public void setId(int id) {
		this.id = id;
	}
	public void setTitle (String aTitle) {
		title = aTitle;
	}
	public void setDetails (String theDetails) {
		details = theDetails;
	}
	public void setDueDate (String dueWhen) {
		dueDate = dueWhen;
	}

	// the accessors
	public int getId() {
		return id;
	}
	public String getTitle () {
		return title;
	}
	public String getDetails () {
		return details;
	}
	public String getDueDate () {
		return dueDate;
	}

	// make attributes available for display
	public String toString () {
		return "\nTitle: " + title + "\nDetails: " + details +  "\nDue Date: " + dueDate;
	}
	

} // end task class