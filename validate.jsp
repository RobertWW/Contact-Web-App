<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false"
%>
<html>
<head>
	<title>Validated!</title>
</head>
<body>
	<h3>																<!-- Simple validation page -->
		Success! Submit another one or go back to check or submission!
	</h3>
	<form method="GET" action="/myapp"><br>
            
            <input type="submit" name = "Submit Another">
    </form>

    
</body>
</html>
