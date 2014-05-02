package com.contactlist.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;


public class Person {
    PreparedStatement pstmt;
    
    
    /**
     * Sends both the number and email to checkNumber and checkEmail, respectively
     * If they are valid, it sends back a true, giving the "go ahead" to add them to the database
     * @param email
     * @param number
     * @return
     */
	   public boolean checkInfo(String email, String number){
		   checkNumber(number);
		   checkEmail(email);
		   if (checkNumber(number) && checkEmail(email)) {
			   return true;
		   } else {
			   return false;
		   }
	   }
	   
	   
/**
 * If it matches this format then it's an accepted email.
 * @param email
 * @return
 */
	   public boolean checkEmail(String email){
		   if (email.matches("[a-zA-Z0-9\\.]+@[a-zA-Z0-9\\-\\_\\.]+\\.[a-zA-Z0-9]{3}")){
			   return true;
		   } else{
			   return false;
		   }
	}
	   
	   
	   /**
	    * If the phone number can both 1) be converted to a double and 2) is 10 digits in length
	    * then it checks out. This is assuming the phone number is 10 digits. Currently doesn't
	    * check for outrageous phone numbers (like, 0000000000) or international number codes.
	    * @param number
	    * @return
	    */
	   public boolean checkNumber(String number){
		   try {
			   double phoneNumber = Double.parseDouble(number);
		   } catch(NumberFormatException nfe) {
			   return false;
		   }
		   if (number.length() != 10) {
			   return false;
		   } else{
			   return true;

		   }
	   }
	   
	   
	   /**
	    * Connects to the database using the JDBC driver.
	    * Assumes the ContactList database with the Contacts table is already created
	    * If it connects, it adds all of the following parameters to the database. Each row
	    * has an AUTO_INCREMENT id number.
	    * @param firstName
	    * @param lastName
	    * @param email
	    * @param phoneNumber
	    */
		public void addToDatabase(String firstName, String lastName, String email, String phoneNumber) {

			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("No JDBC Driver found!");
			}
			Connection connection = null;
			 
			try {
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactList","root", "");
		 
			} catch (SQLException e) {
				System.out.println("Connection Failed!");
			}
		 
			if (connection != null) {
				System.out.println("Connected to database");
			} else {
				System.out.println("Failed to make connection!");
			}
			try {
				String query = " insert into Contacts (firstName, lastName, Email, phoneNumber)"
				        + " values (?,?,?,?)"; 
			     pstmt = connection.prepareStatement(query);
			     pstmt.setString(1, firstName);
			     pstmt.setString(2, lastName);
			     pstmt.setString(3, email);
			     pstmt.setString(4, phoneNumber);
			     pstmt.executeUpdate();
			     connection.close();
				pstmt.close();
			} catch (SQLException e) {
				System.out.println("Failed to add!");

			}		
		}
		
		
	
		
		
/**
 * Currently creates a new file "test.csv" to the desktop
 * Connects to the database and then iterates through the entire
 * database and appends the data to the test.csv file.
 */
		public void exportData() {
			
		
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("No JDBC Driver found!");
			}
			Connection connection = null;
			 
			try {
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactList","root", "");
		 
			} catch (SQLException e) {
				System.out.println("Connection Failed!");
			}
		 
			if (connection != null) {
				System.out.println("Connected to database");
			} else {
				System.out.println("Failed to make connection!");
			}
	        try {
	        	
	        	String query = "select * from Contacts ;";
			     pstmt = connection.prepareStatement(query);
		        ResultSet rs =  pstmt.executeQuery(query);
		  	    FileWriter writer = new FileWriter("/Users/Robert/Desktop/test.csv");
			    writer.append("First Name");
			    writer.append(',');
			    writer.append("Last Name");
			    writer.append(',');
			    writer.append("Email Address");
			    writer.append(',');
			    writer.append("Phone Number");
			    writer.append('\n');
			    writer.append('\n');
		        while (rs.next())
		        {
		          String firstName = rs.getString("firstName");
		          String lastName = rs.getString("lastName");
		          String email = rs.getString("email");
		          String phoneNumber = rs.getString("phoneNumber"); 
		          // print the results
		          
		  	  
			    writer.append(firstName);					//Adds everything to .cvs file
			    writer.append(',');
			    writer.append(lastName);
			    writer.append(',');
			    writer.append(email);
			    writer.append(',');
			    writer.append(phoneNumber);
			    writer.append('\n');		                   
		        }
		 
			     connection.close();
				pstmt.close();
				writer.close();
			} catch (SQLException e) {
				System.out.println("Failed!");

			} catch (IOException o) {
				
			}

	    }
		
		
		
		
		/**
		 * Gets the following parameters from the update forms in the save.jsp view
		 * 
		 * @param ID
		 * @param firstName
		 * @param lastName
		 * @param email
		 * @param phoneNumber
		 * 
		 * Puts each of the parameters in a hashmap with the corresponding name in the MYSQL database
		 * It puts everything in the map, regardless if there is actually data in it
		 * If the VALUE of the hashmap isn't empty, it'll UPDATE the database with the information
		 */
		public void update(String ID, String firstName, String lastName, String email, String phoneNumber) {
			Map<String,String> updateMap = new HashMap<String,String>();
			updateMap.put("firstName", firstName);									//puts everything in hashmap
			updateMap.put("lastName",lastName);
			updateMap.put("Email", email);
			updateMap.put("phoneNumber", phoneNumber);
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("No JDBC Driver found!");
			}
			Connection connection = null;
			 
			try {
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactList","root", "");
		 
			} catch (SQLException e) {
				System.out.println("Connection Failed!");
			}
		 
			if (connection != null) {
				System.out.println("Connected to database");
			} else {
				System.out.println("Failed to make connection!");
			}
			try {
				for (Map.Entry<String, String> entry : updateMap.entrySet()) {				//updates every non-empty value taken from user
					if (!entry.getValue().isEmpty()) {
					System.out.println(entry.getValue() + entry.getKey());
					String query = "UPDATE contacts SET "+ entry.getKey() + 
							" = '"+ entry.getValue() +"' WHERE ID = '" + ID + "'";
				     pstmt = connection.prepareStatement(query);
				     pstmt.executeUpdate();													//execute MySQL command
					}
				}

			     connection.close();
				pstmt.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Oops! You either entered information incorrectly or didn't fill out"
						+ " a required field!", "Error",
	                    JOptionPane.ERROR_MESSAGE);
			}					
		}
		
		
		
		
		
		/**
		 * Used as a test to see if my database looked correct.
		 */
/*		public void printOutDatabase() {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("No JDBC Driver found!");
			}
			Connection connection = null;
			 
			try {
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactList","root", "");
		 
			} catch (SQLException e) {
				System.out.println("Connection Failed!");
			}
		 
			if (connection != null) {
				System.out.println("Connected to database");
			} else {
				System.out.println("Failed to make connection!");
			}
			try {
		        String query = "select * from Contacts ;";
			     pstmt = connection.prepareStatement(query);
		        ResultSet rs =  pstmt.executeQuery(query);
		        while (rs.next())
		        {
		          int id = rs.getInt("id");
		          String firstName = rs.getString("firstName");
		          String lastName = rs.getString("lastName");
		          String email = rs.getString("email");
		          String phoneNumber = rs.getString("phoneNumber"); 
		          // print the results
		          System.out.format("%s, %s, %s, %s, %s\n", id, firstName, lastName, email, phoneNumber);
		        }
		 
			     connection.close();
				pstmt.close();
			} catch (SQLException e) {
				System.out.println("Failed!");

			}		
		}
*/	
}
