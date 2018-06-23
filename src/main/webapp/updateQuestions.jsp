<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
  <h2>List Of Questions</h2>
  <p>Click on update to update the question from the list</p>            
  <table class="table table-condensed">
    <thead>
      <tr>
        <th>Question{</th>
        <th>To Update</th>
      </tr>
    </thead>
    <tbody>
      
<!--  The mighty loop -->
${myVal}

    <c:forEach items = "${allQuestions}" var = "myQuestions" >
    <tr>
		<td>${myVal}</td>
		<td>
		
		<form action="questions?allQuestions=${questions.Question}"	method="POST">
			<button class="btn btn-sm btn-primary">UPDATE</button>
        </form>
		
		</td>
		</tr>
    
    </c:forEach>
      
    </tbody>
  </table>
</div>

</body>
</html>
