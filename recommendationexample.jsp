<html>



<%@ page import="java.io.*,java.util.*,net.sf.javaml.core.*,java.nio.file.*,net.sf.javaml.clustering.*,net.sf.javaml.classification.*,net.sf.javaml.distance.*" %>
<%@ page import="project.mlbean" %>

<body>


<form action="<% request.getRequestURL().toString(); %> " >
USER ID:
<input type=number required name="user"></input><br>
PRODUCT ID:
<input type=number required name="product"></input><br>
RATING : <br>
<input type=radio  name="rating" value="1">1</input><br>
<input type=radio  name="rating" value="2">2</input><br>
<input type=radio  name="rating" value="3">3</input><br>
<input type=radio  name="rating" value="4">4</input><br>
<input type=radio  name="rating" value="5">5</input><br>
<input type=submit name="submit" value="SUBMIT"></input><br>
</form>




<%	
	try
	{
	int USER_ID=Integer.parseInt(request.getParameter("user"));
	int PRODUCT_ID=Integer.parseInt(request.getParameter("product"));
	int RATING=Integer.parseInt(request.getParameter("rating"));
	mlbean ob=new mlbean();
	out.println(ob.train(USER_ID,PRODUCT_ID,RATING));
	}
	catch(Exception  e)
	{ //out.println(e);
	}

%>



</body>

</html>
