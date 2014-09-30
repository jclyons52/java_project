
package second_tier;

/**
 * subclass of task
 */

public class PersonalTask extends Task {
	private static final long serialVersionUID = 100L;
	private boolean important;
	private String requirements;
	private double cost;
	
	public PersonalTask(){};

	public PersonalTask (String dd, String t, String details, boolean imp, String s, double tM) {
		//inherits attributes of parent class (Task) and adds attributes listed below
		super (dd, t, details);
		important = imp;
		requirements = s;
		cost = tM;
	}

	public boolean getImportant() {
		//returns value of the attribute "important"
		return important;
	}
	public String getRequirements() {
		//returns value of the attribute "requirements"
		return requirements;
	}
	public double getCost() {
		//returns value of the attribute "cost"
		return cost;
	}
	
	public void setImportant (boolean grd) {
		//sets value of the attribute "important"
		important = grd;
	}
	public void setRequirements (String s) {
		//sets value of the attribute "requirements"
		requirements = s;
	}
	public void setCost (double tM) {
		//sets value of the attribute "cost"
		cost = tM;
	}

	public String toString () {
		// returns attributes of method as a string concatenated with some other text for structure and readability 
		return super.toString() + "\nImportant: " + (getImportant()?"no":"yes") +
					"\nWhat is required to complete it: " + getRequirements() +
					"\nTotal Cost: $" + getCost() + " Dollars";
	}
}
