
package second_tier;

/**
 * subclass of task
 */

public class StudyTask extends Task {
	private static final long serialVersionUID = 100L;
		// attributes
	private boolean graded;
	private String subject;
	private double totalMarks;

		// constructor
	
	public StudyTask(){};

	public StudyTask (String dd, String t, String details, boolean grd, String s, double tM) {
		super (dd, t, details);
		graded = grd;
		subject = s;
		totalMarks = tM;
	}
		// the getters (accessors) ...
	public boolean getGraded() {
		//returns value of the attribute "graded"
		return graded;
	}
	public String getSubject() {
		//returns value of the attribute "subject"
		return subject;
	}
	public double getTotalMarks() {
		//returns value of the attribute "totalMarks"
		return totalMarks;
	}
		// ... & setters (mutators)
	public void setGraded (boolean grd) {
		//sets value of the attribute "graded"
		graded = grd;
	}
	public void setSubject (String s) {
		//sets value of the attribute "subject"
		subject = s;
	}
	public void setTotalMarks (double tM) {
		//sets value of the attribute "totalMarks"
		totalMarks = tM;
	}

	public String toString () {
		// returns attributes of method as a string concatenated with some other text for structure and readability
		return super.toString() + "\nGraded: " + getGraded() +
					"\nSubject: " + getSubject() +
					"\nTotal Marks: " + getTotalMarks() + " marks";
	}
} // end class
