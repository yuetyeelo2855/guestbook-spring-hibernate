<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body>
<table style="border: 1px solid black">
<tbody>
<tr>
<th style="background-color: #CCFFCC; margin: 5px">ID</th>
<th style="background-color: #CCFFCC; margin: 5px">Name</th>
<th style="background-color: #CCFFCC; margin: 5px">Message</th>
</tr>
<c:forEach var="entry" items="${entries}">
	<tr>
	<td>${entry.id}</td>
	<td>${entry.guest.firstName} ${entry.guest.lastName} (${entry.guest.id})</td>
	<td>${entry.content}</td>
	</tr>
</c:forEach>
</tbody>
</table>
<br />

<p><strong>Sign the guestbook!</strong></p>
<form action="/guestbook" method="post">
    <div>first name: <input type="text" name="first_name" /><br />
    last name: <input type="text" name="last_name" /></div>
    <div>Message:
    <br /><textarea name="content" rows="3" cols="60"></textarea>
    </div>
    <div><input type="submit" value="Post Greeting" /></div>
    <input type="hidden" name="guestbookName" />
  </form>
  
  <br />
  <br />
  <table border="1" cellspacing="0">
	<th style="background-color: #CCFFCC; margin: 5px">guest.id</th>
	<th style="background-color: #CCFFCC; margin: 5px">guest.firstName</th>
	<th style="background-color: #CCFFCC; margin: 5px">guest.entries</th>

	<c:forEach var="guest" items="${guests}">
	<tr>
		<td align="center">${guest.id}</td>
		<td align="center">${guest.firstName} ${lastName}</td>
		<td><table>
			<c:forEach var="entry" items="${guest.entries}">
				<tr>
					<td>${entry.id}</td>
					<td>${entry.content}</td>
				</tr>
			</c:forEach>
			</table>
		</td>
	</tr>
	</c:forEach>
  </table>
  </body>
</html>