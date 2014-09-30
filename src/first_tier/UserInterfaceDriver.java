package first_tier;

import java.sql.SQLException;

/** 
 * main method, creates instance of UI
 * @author Joseph
 *
 */

public class UserInterfaceDriver {
		public static void main(String[] args) throws SQLException {
			UserInterface thisFrame = new UserInterface();
			thisFrame.setVisible(true);
		}

	}
