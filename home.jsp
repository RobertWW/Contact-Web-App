<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
				<!-- Creates a form to enter data into the database -->
<h2>
Please enter your data
</h2>
	<form method="post" action="validate"><br>
            First Name <input type="text" name="firstName"/><br>
            Last Name <input type="text" name="lastName"/><br>
            Phone Number (Ex: 9482849394 [10 digits]) <input type="text" name="phoneNumber"/><br>
            Email (Ex: something@something.com) <input type="text" name="email"/><br>

            
            
            <input type="submit"/>
    </form>
    <h3>Want to check or save?</h3>
    								<!-- Button to go to the Save/check page -->
	<form method = "POST" action = "save"><br>
            <input type ="submit" value = "Save/Check"/>

	</form>
</body>
</html>
