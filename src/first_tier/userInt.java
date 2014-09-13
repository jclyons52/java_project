package first_tier;


import java.util.*; // for Scanner and ArrayList
import java.io.*; // for FileNotFoundException

import javax.swing.JOptionPane;

import second_tier.*;

public class userInt {

		public void begin() {

			Scanner input = new Scanner (System.in);
			// create the Person ArrayList object
			DataStorage tasks = new DataStorage();
			// display menu and process 
			boolean finished = false, openedFile = false, fileSaved = true;
			String fileName = "data.obj"; // assuming a file name

			while (!finished) { // a loop to allow the program to continue until the user chooses to exit program
				int selection = showMenu (input);
				switch (selection) {
				case 1: try {
								if (!openedFile) { // addAll() appends the file data to the ArrayList data
									TaskWorker tw = new TaskWorker ();
									tasks.addAll (tw.openFile (fileName));
									JOptionPane.showMessageDialog(null, "\n** File successfully opened **\n");
									openedFile = true;
								} else { // don't double-up the data in the ArrayList
									JOptionPane.showMessageDialog(null, "\n*** ERROR: File was not found or has already been opened ***\n");
								}
							}
							catch (ClassNotFoundException cnfe) {
								JOptionPane.showMessageDialog(null, "\n**** ERROR: Problem with data type ****");
								openedFile = true;
							}
							catch (FileNotFoundException fnfe) {
								JOptionPane.showMessageDialog(null, "\n**** ERROR: Cannot find the data file ****\n");
								openedFile = true; // to prevent doubling-up the data in the ArrayList
							}
							catch (IOException io) {
								JOptionPane.showMessageDialog(null, "\n*** ERROR: Cannot perform I/O operation ***\n");
							}
							break;
					case 2: tasks.add (addPersonalTask (input));
								fileSaved = false;
								break;
					case 3: tasks.add (addStudyTask (input));
								fileSaved = false;
								break;
					case 4: 		
						 String output = "Displaying the task information ...\n";
								for (int i = 0; i < tasks.size(); i++) {
									output += tasks.get(i);
//									System.out.println ("---------------------------------");
								}
								JOptionPane.showMessageDialog(null, output);
								
								break;
					case 5: try {
							if (!openedFile) {
								JOptionPane.showMessageDialog(null, "WARNING: This action will overwrite the data in the file --> \n Please open the file before saving!!");
							} else {
								fileSaved = saveFile (fileName, tasks);
							}
						}
		  				catch (IOException ioe) {
		  					JOptionPane.showMessageDialog(null, "\n**** ERROR: Data cannot be saved ****\n");
		  				}
		  				break;
					case 6: if (!fileSaved) {
						JOptionPane.showMessageDialog(null, "\n*** WARNING: New data has NOT been saved ***\n");

							System.out.print ("Do you wish to save the new data? (y or n) ");
							String[] yesNo = new String[] {"Yes", "No"};
							int answer = JOptionPane.showOptionDialog(null, "is it urgent?", "GST calculator", JOptionPane.DEFAULT_OPTION,
											JOptionPane.PLAIN_MESSAGE,null, yesNo, yesNo[0]);
						if (answer == 0) {
							try {
								saveFile (fileName, tasks);
							}
				  			catch (IOException ioe) {
				  				JOptionPane.showMessageDialog(null, "\n**** ERROR: Data cannot be saved ****\n");
				  			}
				  		}
					}
					finished = true; // stops the while loop
					JOptionPane.showMessageDialog(null, "\n*** Program Ended ***\n *** Thank you for using this program ***\n");
					break;
					default: JOptionPane.showMessageDialog(null, "\n** Invalid Selection **\n");
				} // end switch
			} // end while loop
		} // end begin()

		public int showMenu (Scanner input) {
			int  count = 0; 
			String [] options = {++count +". Open Existing File", ++count + ". Add a new personal task", ++count + ". Add a new study task",
					++count + ". Display all details", ++count + ". Save All Data to File", ++count + ". Exit Program\n"};
			int response = JOptionPane.showOptionDialog(null, "Type the number of your selection, and tap the Enter key: ", "\n******* MENU *******\n",
			        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			return response+1;
		}

		public PersonalTask addPersonalTask (Scanner input) {
			String aTitle = JOptionPane.showInputDialog (null, "\nWhat is the title of the task? ");

			String theDetails = JOptionPane.showInputDialog (null, "please give a breif description of the process required to complete " + aTitle);
			
			String dueWhen = JOptionPane.showInputDialog (null, "When is " + aTitle + " due?");
			
			String resources = JOptionPane.showInputDialog (null,"what resources need to be gathered for this task?");
			
			System.out.print ("is it urgent? y or n");
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

		public StudyTask addStudyTask (Scanner input) {

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
			TaskWorker pw = new TaskWorker ();
			pw.saveFile (fileName, list); // assuming file name
			JOptionPane.showMessageDialog(null, "\n** Data Successfully Saved **\n");
			return true;
		}


	} 

