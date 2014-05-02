<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %> 
<%@ page session="false" %>
<html>
<head>
	<title>Check or Save File!</title>
</head>
<body>
<%
	try {

	String connectionURL = "jdbc:mysql://localhost:3306/ContactList";   //Connects to database
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	Class.forName("com.mysql.jdbc.Driver").newInstance();
	connection = DriverManager.getConnection(connectionURL, "root", "");
	statement = connection.createStatement();
	String QueryString = "SELECT * from Contacts";
	rs = statement.executeQuery(QueryString);					//Selects everything from database to QueryString
	
%>

<TABLE border="1" style="backgrouttnd-color: #ffffcc;">
<tr>
	<td>ID</td>													<!-- Creates the table header titles -->
	<td>First Name</td>
	<td>Second Name</td>
	<td>Email Address</td>	
	<td>Phone Number</td>
	
</tr>
<%
	
	while (rs.next()) {									//iterates through entire database
%>
	<TR>
		<TD><%=rs.getString(1)%></TD>				<!-- Puts in all the data from the data base -->
		<TD><%=rs.getString(2)%></TD>				<!-- ID, first name, last name, phone number, and email-->
		<TD><%=rs.getString(3)%></TD>				<!-- Puts in by increments of 5 because there are 5 types of data -->
		<TD><%=rs.getString(4)%></TD>
		<TD><%=rs.getString(5)%></TD>
		
	</TR>
<% } %>
<%
													// close all the connections.
	rs.close();	
	statement.close();
	connection.close();
	} catch (Exception ex) {
%>

<%
	out.println("Unable to connect to database.");
}
%>

	</table><table>
		<TR>
			<TD>												<!-- Creates the form to update any information -->
				<form action = "update" method = "POST"><br>
					ID: <input type="text" name="ID"></input><br>	
					First name: <input type="text" name="firstName"></input><br>
					Last name: <input type="text" name="lastName"></input><br>
					Email: <input type="text" name="email"></input><br>
					PhoneNumber: <input type="text" name="phoneNumber"></input><br>
					<input type = "submit" value = "update"></input>
				</form>
			</TD>
		</TR>


	<h3>
		Need to update information? <b>Enter the ID</b> and the information you want to update! Leave everything
		else blank.
	</h3>

</TABLE>
															<!-- Creates download button and back button.  -->
				<form action = "download" method = "POST">	
					<input type="submit" value=" download " />
				</form>
				
				<form ACTION="/myapp" method="get" >
					<button type="submit">back</button>
				</form>	

						
</body>
</html>
