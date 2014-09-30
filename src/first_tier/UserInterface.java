package first_tier;
import java.awt.*;

import javax.swing.*;

import second_tier.DataStorage;
import second_tier.PersonalTask;
import second_tier.StudyTask;
import second_tier.TaskWorker;
import third_tier.tables.StudyTaskManager;

import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
/**
	this is the primary user interface class, it contains the code for the border layout JPane along with the
	UI methods for adding new tasks
*/
public class UserInterface extends JFrame implements ActionListener {
	DataStorage tasks = new DataStorage();
	public ArrayList<String> titles = new ArrayList<String>();

	public static final long serialVersionUID = 100L;
	public JComboBox combo;
	public JScrollPane textScroller;
	public JTextArea textArea = new JTextArea("",90,30);

	
	public UserInterface() throws SQLException  {
		/*
		 * this is method automatically loads the data from file and creates the primary user interface 
		 */
		JPanel subPanel5 = new JPanel();
	    JTextArea jTextArea = new JTextArea();
		
		// display menu and process 
		boolean openedFile = false, fileSaved = true;
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
		
		if (!openedFile) { // addAll() appends the file data to the ArrayList data
			tasks.addAll (StudyTaskManager.display());
			for (int i = 0; i < tasks.size(); i++) {
				titles.add(tasks.get(i).getTitle());
			}
//			    jlabel.setFont(new Font("Verdana",1,20));
		    subPanel5.add(jTextArea);
//				openedFile = true;
		} else { // don't double-up the data in the ArrayList
			JOptionPane.showMessageDialog(null, "\n*** ERROR: File was not found or has already been opened ***\n");
		}
    
		
		
		getContentPane().setBackground(Color.YELLOW);

		combo = new JComboBox(titles.toArray());
		combo.addActionListener(this);
		getContentPane().add(combo);
		getContentPane().add(Box.createRigidArea(new Dimension(0,15)));
		textScroller = new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		textArea.setLineWrap(true);
		textArea.setRows(10);
		textArea.setWrapStyleWord(true);
		getContentPane().add(combo, BorderLayout.NORTH);
		
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new GridLayout (6, 1, 4, 4));
		JButton myJButton7 = new JButton( "Add a new personal task" );
		myJButton7.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	PersonalTask newPersonalTask = addPersonalTask ();
            	tasks.add (newPersonalTask);
            	titles.add(newPersonalTask.getTitle());
            	combo.addItem(newPersonalTask.getTitle());
            }
        });
	    subPanel.add( myJButton7);
		JButton myJButton8 =new JButton( "Add a new study task" );
		myJButton8.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	StudyTask newStudyTask = addStudyTask ();
            	tasks.add (newStudyTask);
            	titles.add(newStudyTask.getTitle());
            	combo.addItem(newStudyTask.getTitle());
            }
        });
		
	    subPanel.add(myJButton8 );
		JButton myJButton10 = new JButton( "Save All Data to File" );
		myJButton10.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	try {
					 saveFile (fileName, tasks);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "\n**** ERROR: Data cannot be saved ****\n");
				}
            }
        });
		
		
	    subPanel.add( myJButton10);

	    //Now we simply add it to your main panel.
	    getContentPane().add(subPanel, BorderLayout.WEST);
	    
	    
	    
	    subPanel5.setVisible(true);
	    subPanel5.setBackground(Color.BLUE);
	    subPanel5.add(textArea);
		getContentPane().add(subPanel5, BorderLayout.CENTER);
		
		
		
		
		
		
		
	}
	
	public PersonalTask addPersonalTask () {
		/*
		 * this method is the user interface for creating a new personal task
		 */
		String aTitle = JOptionPane.showInputDialog (null, "\nWhat is the title of the task? ");

		String theDetails = JOptionPane.showInputDialog (null, "please give a breif description of the process required to complete " + aTitle);
		
		String dueWhen = JOptionPane.showInputDialog (null, "When is " + aTitle + " due?");
		
		String resources = JOptionPane.showInputDialog (null,"what resources need to be gathered for this task?");
		
		String[] yesNo = new String[] {"Yes", "No"};
		int answer = JOptionPane.showOptionDialog(null, "is it urgent?", "Title", JOptionPane.DEFAULT_OPTION,
						JOptionPane.PLAIN_MESSAGE,null, yesNo, yesNo[0]);
		boolean urgent = false;
			if(answer == 0){
				urgent = true;
			}else{
				urgent = false;
			}	
		
			String ans = JOptionPane.showInputDialog (null, "what is the cost involved?");
			double cost = Double.parseDouble(ans);
		PersonalTask x = new PersonalTask(aTitle,theDetails,dueWhen,urgent,resources,cost);
		return x;
	}

	public StudyTask addStudyTask () {
		/*
		 * this method is the user interface for creating a new study task
		 */
		
		String aTitle = JOptionPane.showInputDialog ("\nWhat is the title of the task? ");

		String theDetails = JOptionPane.showInputDialog ("please describe what is required to complete " + aTitle);

		String dueWhen = JOptionPane.showInputDialog ("When is " + aTitle + " due?");
			
		String[] yesNo = new String[] {"Yes", "No"};
		int answer = JOptionPane.showOptionDialog(null,"is this assesment graded? y/n", "title", JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE,null, yesNo, yesNo[0]);
			boolean graded = false;
			if(answer == 0){
				graded = true;
			}else{
				graded = false;
			
			}	
			
		String[] subject = new String[] {"webdesign", "SQL","buildADatabase","HealthAndSafety","Sustainability","OOP"};
		int result = JOptionPane.showOptionDialog(null,"What subject is " + aTitle + "for?", "title", JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE,null, subject, subject[0]);
		String resultSubject = subject[result];
		
		double tM;
		
		do { 
			String tm = JOptionPane.showInputDialog ("How many marks is " + aTitle + " worth?");
			tM = Double.parseDouble(tm);
			if (tM < 0) {
				JOptionPane.showMessageDialog(null, "Error - value must be zero or more");
			}
		} while (tM < 0);

		StudyTask e = new StudyTask (aTitle, theDetails , dueWhen,graded, resultSubject , tM);

		return e;

	} 
	
	public boolean saveFile (String fileName, DataStorage list) throws IOException {
		/*
		 * saves file to storage
		 */
		TaskWorker pw = new TaskWorker ();
		pw.saveFile (fileName, list); // assuming file name
		JOptionPane.showMessageDialog(null, "\n** Data Successfully Saved **\n");
		return true;
	}
	
	public void actionPerformed(ActionEvent e) {
		int i = combo.getSelectedIndex();
		
		textArea.setText(tasks.get(i).toString());
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				textScroller.getVerticalScrollBar().setValue(0);
			}

		});
		
		
	}
	

} // end class layoutTest