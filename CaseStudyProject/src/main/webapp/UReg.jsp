<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration Form</title>
<link rel="stylesheet" href="style.css">
<style>
.container {
  width: 300px; 
  height: 500px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  margin: auto; /* Center the container horizontally by using margin: 0 auto; */
    margin-left: 20px;
}
</style>
</head>
<body>
<div class="container">
  <form action="URegister" method="post">
    <label for="fname">First Name:</label>
    <input type="text" name="fname" id="fname">

    <label for="lname">Last Name:</label>
    <input type="text" name="lname" id="lname">

    <label for="email">EmailId:</label>
    <input type="email" name="email" id="email">

    <label for="mob">Mobile:</label>
    <input type="number" name="mob" id="mob">

    <label for="uname">Username:</label>
    <input type="text" name="uname" id="uname">

    <label for="pwd">Password:</label>
    <input type="password" name="pwd" id="pwd">

    <input type="submit" value="Register">
  </form>
</div>
</body>
</html>

