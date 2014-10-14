package first_tier;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import third_tier.SDatabase;

public class StudyInterface extends JFrame {
	
	private JButton addTask, removeTask;
	private JLabel errorMessage;
	private JTextField tfDueDate, tfTitle, tfDetails, tfGraded, tfSubject, tfTotalMarks;
	// Instance attributes used in this example
		private	JTable		table;
		private	JPanel		topPanel;
		private	JScrollPane scrollPane;
		private JPanel      inputPanel;
		
		SDatabase data = new SDatabase();

	
	public StudyInterface() throws SQLException  {
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
	 		tfGraded = new JTextField("graded", 14);
	 		tfSubject = new JTextField("subject", 8);
	 		tfTotalMarks = new JTextField("total_marks", 3);
	 		
	 // Create a focus listener and add it to each text field to remove text when clicked on
			ListenForFocus focusListener = new ListenForFocus();
			tfDueDate.addFocusListener(focusListener);
			tfTitle.addFocusListener(focusListener);
			tfDetails.addFocusListener(focusListener);
			tfGraded.addFocusListener(focusListener);
			tfSubject.addFocusListener(focusListener);
			tfTotalMarks.addFocusListener(focusListener);
	 		
	 	// Set button values
	 		addTask = new JButton("Add Task");
	 		removeTask = new JButton("Remove Task");
			
			// Add action listeners to the buttons to listen for clicks
			ListenForAction actionListener = new ListenForAction();
			addTask.addActionListener(actionListener);
			removeTask.addActionListener(actionListener);
	    
	 // Create a new panel and add the text fields and add/remove buttons to it
	    inputPanel = new JPanel();
		inputPanel.add(tfDueDate);
		inputPanel.add(tfTitle);
		inputPanel.add(tfDetails);
		inputPanel.add(tfGraded);
		inputPanel.add(tfSubject);
		inputPanel.add(tfTotalMarks);
		
		inputPanel.add(addTask);
		inputPanel.add(removeTask);
	    
	 // Add the table to a scrolling pane
	 		scrollPane = new JScrollPane( table );
	 		topPanel.add( scrollPane, BorderLayout.CENTER );
	 		topPanel.add( inputPanel, BorderLayout.SOUTH );
	 		
	
		
	}
	
	private class ListenForAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == addTask) { // If the user clicks Add Customer, add the information into the database
				// Create variables to hold information to be inserted, and get the info from the text fields
				String dueDate, title, details, graded, subject, total_marks;
				dueDate = tfDueDate.getText();
				title = tfTitle.getText();
				details = tfDetails.getText();
				graded = tfGraded.getText();
				subject = tfSubject.getText();
				total_marks = tfTotalMarks.getText();
				
				int taskID = 0;
				
				try { // Attempt to insert the information into the database
					data.rows.moveToInsertRow();
					data.rows.updateString("due_date", dueDate);
					data.rows.updateString("title", title);
					data.rows.updateString("details", details);
					data.rows.updateString("graded", graded);
					data.rows.updateString("subject", subject);
					data.rows.updateString("total_marks", total_marks);
					
					data.rows.insertRow();
					data.rows.updateRow();
					
					data.rows.last();
					taskID = data.rows.getInt(1);
					Object[] task = {taskID, dueDate, title, details, graded, subject, total_marks};
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
			} else if(tfGraded.getText().equals("graded") && e.getSource() == tfGraded) {
				tfGraded.setText("");
			} else if(tfSubject.getText().equals("subject") && e.getSource() == tfSubject) {
				tfSubject.setText("");
			} else if(tfTotalMarks.getText().equals("total_marks") && e.getSource() == tfTotalMarks) {
				tfTotalMarks.setText("");
			}
		}

		public void focusLost(FocusEvent e) { // If the text field loses focus and is blank, set the default text back
			if(tfDueDate.getText().equals("") && e.getSource() == tfDueDate) {
				tfDueDate.setText("due date");
			} else if(tfTitle.getText().equals("") && e.getSource() == tfTitle) {
				tfTitle.setText("title");
			} else if(tfDetails.getText().equals("") && e.getSource() == tfDetails) {
				tfDetails.setText("Details");
			} else if(tfGraded.getText().equals("") && e.getSource() == tfGraded) {
				tfGraded.setText("graded");
			} else if(tfSubject.getText().equals("") && e.getSource() == tfSubject) {
				tfSubject.setText("subject");
			} else if(tfTotalMarks.getText().equals("") && e.getSource() == tfTotalMarks) {
				tfTotalMarks.setText("total_marks");
			}
		}
		
	}

}
