package first_tier;

import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainInterface extends JFrame {
	
	private	JPanel		topPanel;
	
	public MainInterface() throws SQLException{
	
	String[] TaskType = new String[] {"Study Tasks", "Personal Tasks"};
	int answer = JOptionPane.showOptionDialog(null, "is it urgent?", "Title", JOptionPane.DEFAULT_OPTION,
					JOptionPane.PLAIN_MESSAGE,null, TaskType, TaskType[0]);
		if(answer == 0){
			StudyInterface thisFrame = new StudyInterface();
			thisFrame.setVisible(true);
		}else{
			UserInterface thisFrame = new UserInterface();
			thisFrame.setVisible(true);
		}
	}
}
