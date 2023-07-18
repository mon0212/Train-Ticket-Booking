<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Train</title>
   <link rel="stylesheet" href="style.css">
   <style>


.container {
  width: 300px; 
  height: 600px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  margin: auto; /* Center the container horizontally by using margin: 0 auto; */
    margin-left: 40px;
}
   </style>
</head>
<body>
    
    <div class="container">
    <form action="AddTrain" method="post">
        <label for="trainName">Train Name:</label>
        <input type="text" id="trainName" name="trainName" required>
        <br>
        <label for="trainNumber">Train Number:</label>
        <input type="text" id="trainNumber" name="trainNumber" required>
        <br>
        <label for="source">Source:</label>
        <input type="text" id="source" name="source" required>
        <br>
        <label for="destination">Destination:</label>
        <input type="text" id="destination" name="destination" required>
         <br>
            <label for="departure">Departure:</label>
            <input type="datetime-local" id="departure" name="departure" required>
            <br>
            <label for="arrival">Arrival:</label>
            <input type="datetime-local" id="arrival" name="arrival" required>
            <br>
        <input type="submit" value="Add Train">
    </form>
    </div>
</body>
</html>