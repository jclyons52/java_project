package second_tier;


public class StudyTask extends Task {
	private static final long serialVersionUID = 100L;
		// attributes
	private boolean graded;
	private String subject;
	private double totalMarks;

		// constructor

	public StudyTask (String dd, String t, String details, boolean grd, String s, double tM) {
		super (dd, t, details);
		graded = grd;
		subject = s;
		totalMarks = tM;
	}
		// the getters (accessors) ...
	public boolean getGraded() {
		return graded;
	}
	public String getSubject() {
		return subject;
	}
	public double getTotalMarks() {
		return totalMarks;
	}
		// ... & setters (mutators)
	public void setGraded (boolean grd) {
		graded = grd;
	}
	public void setSubject (String s) {
		subject = s;
	}
	public void setTotalMarks (double tM) {
		totalMarks = tM;
	}

	public String toString () {
		return super.toString() + "\nGraded: " + getGraded() +
					"\nSubject: " + getSubject() +
					"\nTotal Marks: " + getTotalMarks() + " marks";
	}
} // end class
