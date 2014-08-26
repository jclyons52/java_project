package second_tier;


public class PersonalTask extends Task {
	private static final long serialVersionUID = 100L;
	private boolean important;
	private String requirements;
	private double cost;


	public PersonalTask (String dd, String t, String details, boolean imp, String s, double tM) {
		super (dd, t, details);
		important = imp;
		requirements = s;
		cost = tM;
	}

	public boolean getImportant() {
		return important;
	}
	public String getRequirements() {
		return requirements;
	}
	public double getCost() {
		return cost;
	}
	
	public void setImportant (boolean grd) {
		important = grd;
	}
	public void setRequirements (String s) {
		requirements = s;
	}
	public void setCost (double tM) {
		cost = tM;
	}

	public String toString () {
		return super.toString() + "\nImportant: " + (getImportant()?"no":"yes") +
					"\nWhat is required to complete it: " + getRequirements() +
					"\nTotal Cost: $" + getCost() + " Dollars";
	}
}
