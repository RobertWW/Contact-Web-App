package com.contactlist.myapp;
import java.util.Locale;
import javax.swing.JOptionPane;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	/**
	 * Simply selects the home view to render by returning its name. Opens the home.jsp view.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "home";
	}
	
	
/**
 * Opens the save view jsp that saves a file as a CSV.	
 * @return
 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save() {
		return "save";
	}
	
	
/**
 * Requests for the following parameters from the VALIDATE.JSP view
 * @param firstName
 * @param lastName
 * @param email
 * @param phoneNumber
 * @param person
 * @return
 * 
 * If the email and phone number are "valid" then it adds the information to the database.
 * ----The check to see if they're valid are in the Person.java class.----
 * Everytime a new person is added  to the database, it also creates a CSV file with  exportData()
 * 
 */
	@RequestMapping(value="validate", method = RequestMethod.POST)
    public String validate(@RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            Person person){
		if (person.checkInfo(email, phoneNumber) && !lastName.isEmpty() && !firstName.isEmpty()) {
			person.addToDatabase(firstName, lastName, email, phoneNumber);
	//		person.printOutDatabase();	//prints out database as a check
	//		person.exportData();		//Creates CSV
			return "validate";			//returns the validate.jsp view
		} else {
			JOptionPane.showMessageDialog(null, "Oops! You either entered information incorrectly or didn't fill out"
					+ " a required field!", "Error",
                    JOptionPane.ERROR_MESSAGE);						//if anything doesn't check out then it displays an error message
			return "home";									//returns HOME.JSP
		}

	}
	
	
	/**
	 * Simply saves a copy of the database as a CSV and downloads
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "download", method = RequestMethod.POST)
	public String download(Person person) {
		person.exportData();				//Creates CSV; calls from Person.java
		return "download";
	}
	
	
	/**
	 * Checks if the ID is there since that's how we're checking through our database
	 * Checks to see if the email and phone numbers are either blank or valid
	 * Updates the information via Person.java if everything checks out
	 * If things don't check out, it sends an error message (JOptionPane)
	 * @param ID
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param phoneNumber
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String test(@RequestParam("ID") String ID,
			@RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            Person person) {
		if (ID.isEmpty()){										//Checking if the ID is empty (Mandatory field)
			JOptionPane.showMessageDialog(null, "Oops! You either entered information incorrectly or didn't fill out"
					+ " a required field!", "Error",
                    JOptionPane.ERROR_MESSAGE);
			return "save";
		} else if(person.checkEmail(email) == false && !email.isEmpty()) {			//if the email is valid and that it's not empty
			JOptionPane.showMessageDialog(null, "Oops! You either entered information incorrectly or didn't fill out"
					+ " a required field!", "Error",
                    JOptionPane.ERROR_MESSAGE);
			return "save";
		} else if (person.checkNumber(phoneNumber) == false && !phoneNumber.isEmpty()) {		//same check as email
			JOptionPane.showMessageDialog(null, "Oops! You either entered information incorrectly or didn't fill out"
					+ " a required field!", "Error",
                    JOptionPane.ERROR_MESSAGE);
			return "save";		
		} else {
		person.update(ID, firstName, lastName, email, phoneNumber);						//updates the information.
		return "home";																	//won't update blank information
		}
	}
}
