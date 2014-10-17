package first_tier;


import java.awt.*;

import javax.swing.*;

import third_tier.PDatabase;

import java.awt.event.*;
import java.sql.SQLException;

/**
	this is the primary user interface class, it contains the code for the border layout JPane along with the
	UI methods for adding new tasks
*/
public class UserInterface extends JFrame  {

	private JButton addTask, removeTask, back;
	private JLabel errorMessage;
	private JTextField tfDueDate, tfTitle, tfDetails, tfImportant, tfRequirements, tfCost;
	// Instance attributes used in this example
		private	JTable		table;
		private	JPanel		topPanel;
		private	JScrollPane scrollPane;
		private JPanel      inputPanel;
		
		PDatabase data = new PDatabase();

	
	public UserInterface() throws SQLException  {
		super();
		
		table = new JTable(data.defaultTableModel);
		
		// Create a new mouse listener and assign it to the table
 		ListenForMouse mouseListener = new ListenForMouse();
 		table.addMouseListener(mouseListener);
		
		// Set the frame characteristics
				setTitle( "Simple Table Application" );
				setSize( 900, 600 );
				setBackground( Color.gray );

		// Create a panel to hold all other components
				topPanel = new JPanel();
				topPanel.setLayout( new BorderLayout() );
				getContentPane().add( topPanel );
		
		
				
	    
	
	    
	 // Set the text field widths and values
	 		tfDueDate = new JTextField("due date", 6);
	 		tfTitle = new JTextField("title", 8);
	 		tfDetails = new JTextField("details", 8);
	 		tfImportant = new JTextField("important", 14);
	 		tfRequirements = new JTextField("requirements", 8);
	 		tfCost = new JTextField("cost", 3);
	 		
	 // Create a focus listener and add it to each text field to remove text when clicked on
			ListenForFocus focusListener = new ListenForFocus();
			tfDueDate.addFocusListener(focusListener);
			tfTitle.addFocusListener(focusListener);
			tfDetails.addFocusListener(focusListener);
			tfImportant.addFocusListener(focusListener);
			tfRequirements.addFocusListener(focusListener);
			tfCost.addFocusListener(focusListener);
	 		
	 	// Set button values
	 		addTask = new JButton("Add Task");
	 		removeTask = new JButton("Remove Task");
	 		back = new JButton("back to menu");
			
			// Add action listeners to the buttons to listen for clicks
			ListenForAction actionListener = new ListenForAction();
			addTask.addActionListener(actionListener);
			removeTask.addActionListener(actionListener);
			back.addActionListener(actionListener);
	    
	 // Create a new panel and add the text fields and add/remove buttons to it
	    inputPanel = new JPanel();
		inputPanel.add(tfDueDate);
		inputPanel.add(tfTitle);
		inputPanel.add(tfDetails);
		inputPanel.add(tfImportant);
		inputPanel.add(tfRequirements);
		inputPanel.add(tfCost);
		
		inputPanel.add(addTask);
		inputPanel.add(removeTask);
	    
	 // Add the table to a scrolling pane
	 		scrollPane = new JScrollPane( table );
	 		topPanel.add( back, BorderLayout.NORTH );
	 		topPanel.add( scrollPane, BorderLayout.CENTER );
	 		topPanel.add( inputPanel, BorderLayout.SOUTH );
	 		
	
		
	}
	
	private class ListenForAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == addTask) { // If the user clicks Add Customer, add the information into the database
				// Create variables to hold information to be inserted, and get the info from the text fields
				String dueDate, title, details, important, requirements, cost;
				dueDate = tfDueDate.getText();
				title = tfTitle.getText();
				details = tfDetails.getText();
				important = tfImportant.getText();
				requirements = tfRequirements.getText();
				cost = tfCost.getText();
				
				int taskID = 0;
				
				try { // Attempt to insert the information into the database
					data.rows.moveToInsertRow();
					data.rows.updateString("due_date", dueDate);
					data.rows.updateString("title", title);
					data.rows.updateString("details", details);
					data.rows.updateString("important", important);
					data.rows.updateString("requirements", requirements);
					data.rows.updateString("cost", cost);
					
					data.rows.insertRow();
					data.rows.updateRow();
					
					data.rows.last();
					taskID = data.rows.getInt(1);
					Object[] task = {taskID, dueDate, title, details, important, requirements, cost};
					data.defaultTableModel.addRow(task); // Add the row to the screen
					errorMessage.setText(""); // Remove the error message if one was displayed
				} catch (SQLException e2) { // Catch any exceptions and display appropriate errodata.rows
					System.out.println(e2.getMessage());
				}
			} else if (e.getSource() == removeTask) {
				try { // If the user clicked remove customer, delete from database and remove from table
					data.defaultTableModel.removeRow(table.getSelectedRow());
					data.rows.absolute(table.getSelectedRow());
					data.rows.deleteRow();
				} catch(SQLException e1) { // Catch any exceptions
					System.out.println(e1.getMessage());
					errorMessage.setText(e1.getMessage());
				} catch(ArrayIndexOutOfBoundsException e1) {
					System.out.println(e1.getMessage());
					errorMessage.setText("To delete a customer, you must fidata.rowst select a row.");
				}
			}else if (e.getSource() == back) {
				
			}
		}
	}
	
	private class ListenForMouse extends MouseAdapter {
		public void mouseReleased(MouseEvent mouseEvent) {			
			// If the mouse is released and the click was a right click
			if (SwingUtilities.isRightMouseButton(mouseEvent)) {
				// Create a dialog for the user to enter new data
				String value = JOptionPane.showInputDialog(null, "Enter Cell Value:");
				if(value != null) { // If they entered info, update the database
					table.setValueAt(value, table.getSelectedRow(), table.getSelectedColumn());
					String updateColumn;
					
					try { // Go to the row in the db
						data.rows.absolute(table.getSelectedRow()+1);
						updateColumn = data.defaultTableModel.getColumnName(table.getSelectedColumn());
					
							data.rows.updateString(updateColumn, value);
							data.rows.updateRow();
						
					} catch (SQLException e1) { // Catch any exceptions and display an error
						errorMessage.setText("An error has occurred.");
						System.out.println(e1.getMessage());
					}
				}
			}
		}
	}
	
	/**
	 * FocusListener implementation used to listen for JTextFields
	 * being focused on.
	 */
	private class ListenForFocus implements FocusListener {
		// place holders in the JTextFields.
		public void focusGained(FocusEvent e) { // If a text field gains focus and has the default text, remove the text
			if(tfDueDate.getText().equals("due date") && e.getSource() == tfDueDate) {
				tfDueDate.setText("");
			} else if(tfTitle.getText().equals("title") && e.getSource() == tfTitle) {
				tfTitle.setText("");
			} else if(tfDetails.getText().equals("details") && e.getSource() == tfDetails) {
				tfDetails.setText("");
			} else if(tfImportant.getText().equals("important") && e.getSource() == tfImportant) {
				tfImportant.setText("");
			} else if(tfRequirements.getText().equals("requirements") && e.getSource() == tfRequirements) {
				tfRequirements.setText("");
			} else if(tfCost.getText().equals("cost") && e.getSource() == tfCost) {
				tfCost.setText("");
			}
		}

		public void focusLost(FocusEvent e) { // If the text field loses focus and is blank, set the default text back
			if(tfDueDate.getText().equals("") && e.getSource() == tfDueDate) {
				tfDueDate.setText("due date");
			} else if(tfTitle.getText().equals("") && e.getSource() == tfTitle) {
				tfTitle.setText("title");
			} else if(tfDetails.getText().equals("") && e.getSource() == tfDetails) {
				tfDetails.setText("Details");
			} else if(tfImportant.getText().equals("") && e.getSource() == tfImportant) {
				tfImportant.setText("Important");
			} else if(tfRequirements.getText().equals("") && e.getSource() == tfRequirements) {
				tfRequirements.setText("requirements");
			} else if(tfCost.getText().equals("") && e.getSource() == tfCost) {
				tfCost.setText("cost");
			}
		}
		
	}

}