<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration Form</title>
<link rel="stylesheet" href="style.css">
<style>
.container {
width: 300px; 
  height: 400px;
}

</style>
</head>
<body>
<div class="container">
  <form action="ARegister" method="post">
    <label for="name">Name:</label>
    <input type="text" name="name" id="name">
    
    <label for="email">EmailId:</label>
    <input type="email" name="email" id="email">
    
    <label for="uname">Username:</label>
    <input type="text" name="uname" id="uname">
    
    <label for="pwd">Password:</label>
    <input type="password" name="pwd" id="pwd">
    
    <input type="submit" value="Register">
  </form>
</div>
</body>
</html>
