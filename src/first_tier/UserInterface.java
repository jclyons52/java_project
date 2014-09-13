package first_tier;
import java.awt.*;

import javax.swing.*;

import second_tier.DataStorage;
import second_tier.TaskWorker;

import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
	Test BorderLayout adding JButtons to the JFrame's contentPane.</br></br>
*/
public class UserInterface extends JFrame {

	public static final long serialVersionUID = 100L;

	public UserInterface()  {
		DataStorage tasks = new DataStorage();
		// display menu and process 
		boolean finished = false, openedFile = false, fileSaved = true;
		String fileName = "data.obj"; // assuming a file name

			// default layout of a JFrame content Pane is BorderLayout

			// set up the JFrame
		setTitle("BorderLayout Test");
		setSize(600,400);  // the size of the JFrame in pixels
		setLocation(300, 300); // distance from top left-hand corner of screen in pixels

		addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		
		getContentPane().setBackground(Color.YELLOW);

		JButton myJButton1 = new JButton("test1");
		getContentPane().add(myJButton1, BorderLayout.NORTH);
		JButton myJButton2 = new JButton("test2");
		getContentPane().add(myJButton2, BorderLayout.SOUTH);
		JButton myJButton3 = new JButton("test3");
		getContentPane().add(myJButton3, BorderLayout.EAST);
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new GridLayout (6, 1, 4, 4));
		JButton myJButton6 = new JButton( "Open Existing File" );
		subPanel.add(myJButton6);
		JButton myJButton7 = new JButton( "Add a new personal task" );
	    subPanel.add( myJButton7);
		JButton myJButton8 =new JButton( "Add a new study task" );
	    subPanel.add(myJButton8 );
		JButton myJButton9 =new JButton( "Display all details" );
	    subPanel.add( myJButton9);
		JButton myJButton10 = new JButton( "Save All Data to File" );
	    subPanel.add( myJButton10);

	    //Now we simply add it to your main panel.
	    getContentPane().add(subPanel, BorderLayout.WEST);
	    JPanel subPanel5 = new JPanel();
	    subPanel5.setVisible(true);
	    subPanel5.setBackground(Color.BLUE);
		getContentPane().add(subPanel5, BorderLayout.CENTER);
		
		myJButton9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				String output = "Displaying the task information ...\n";
				for (int i = 0; i < tasks.size(); i++) {
					output += tasks.get(i);
				}
				JTextArea jTextArea = new JTextArea(output);
//			    jlabel.setFont(new Font("Verdana",1,20));
			    subPanel5.add(jTextArea);
			}
		});
		
		
		myJButton6.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	try {
        			if (!openedFile) { // addAll() appends the file data to the ArrayList data
        				TaskWorker tw = new TaskWorker ();
        				tasks.addAll (tw.openFile (fileName));
        				JOptionPane.showMessageDialog(null, "\n** File successfully opened **\n");
//        				openedFile = true;
        			} else { // don't double-up the data in the ArrayList
        				JOptionPane.showMessageDialog(null, "\n*** ERROR: File was not found or has already been opened ***\n");
        			}
        		}
        		catch (ClassNotFoundException cnfe) {
        			JOptionPane.showMessageDialog(null, "\n**** ERROR: Problem with data type ****");
//        			openedFile = true;
        		}
        		catch (FileNotFoundException fnfe) {
        			JOptionPane.showMessageDialog(null, "\n**** ERROR: Cannot find the data file ****\n");
//        			openedFile = true; // to prevent doubling-up the data in the ArrayList
        		}
        		catch (IOException io) {
        			JOptionPane.showMessageDialog(null, "\n*** ERROR: Cannot perform I/O operation ***\n");
        		}
            }
            });
	}
	

	

} // end class layoutTest