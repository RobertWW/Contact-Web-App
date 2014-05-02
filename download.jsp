<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="application/octet-stream"%>
<%@page pageEncoding="UTF-8"%>
<%@ page language="java" import="java.io.*,java.net.*,java.util.*,javax.servlet.* "%>
<%@ page session="false"
%>
<html>
<head>
	<title>Validated!</title>
</head>
<body>
<%
													//Creates download link
BufferedInputStream filein = null;
BufferedOutputStream outputs = null;
try {
	File file = new File("/Users/Robert/Desktop/test.csv");//specify the file path
	byte b[] = new byte[2048];
	int len = 0;
	filein = new BufferedInputStream(new FileInputStream(file));
	outputs = new BufferedOutputStream(response.getOutputStream());
	response.setHeader("Content-Length", ""+file.length());
	response.setContentType("application/force-download");
	response.setHeader("Content-Disposition","attachment;filename=Backups.csv");		//creates download link name
	response.setHeader("Content-Transfer-Encoding", "binary");
	while ((len = filein.read(b)) > 0) {
		outputs.write(b, 0, len);
		outputs.flush();
	}
}
catch(Exception e){
	out.println(e);
}
%>


%>
</body>
</html>
