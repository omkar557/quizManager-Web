<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
<!--     <link rel="stylesheet" type="text/css" href="CSS_Files/Style.css"> -->
</head>

<body>

<form class="form" method="post" action="qmodifyquestion">
  <div class="form-group">
    <label for="exampleInputEmail1">The Old Question</label>
    <input type="text" class="form-control" name="question", id="question" value="${que}" disabled>
    <small id="emailHelp" class="form-text text-muted">The old question, Please enter new question below:</small>
    
    <label for="exampleInputEmail1">Enter the new Question</label>
    <input type="text" class="form-control" name="question", id="question" placeholder="New Question">
  </div>

                      
  
  <button type="submit" class="btn btn-primary">Submit</button>
</form>

</body>

</html>