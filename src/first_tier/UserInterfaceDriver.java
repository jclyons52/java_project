package first_tier;

import java.sql.SQLException;

/** 
 * main method, creates instance of UI
 * @author Joseph
 *
 */

public class UserInterfaceDriver {
	
	
		public static void main(String[] args) throws SQLException {
			
			MainInterface thisFrame = new MainInterface();
			thisFrame.setVisible(true);
			
			
		}

	}
