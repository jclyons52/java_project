package first_tier;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import second_tier.DataStorage;

public class DisplayTasks {
	
	public JPanel displayTasks(DataStorage tasks){
	JPanel subPanel5 = new JPanel();
	String output = "Displaying the task information ...\n";
	for (int i = 0; i < tasks.size(); i++) {
		output += tasks.get(i);
	}
	JTextArea jTextArea = new JTextArea(output);
    subPanel5.add(jTextArea);
    return subPanel5;
	}

}
