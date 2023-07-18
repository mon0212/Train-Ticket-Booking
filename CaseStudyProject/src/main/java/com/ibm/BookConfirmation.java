package com.ibm;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BookConfirmation extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String DB_USER = "JAVA";
    private static final String DB_PASSWORD = "JAVA";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession(); 
    	String username = ""; // Default username (if not found)
      username = (String) session.getAttribute("username"); 
      System.out.println("confirm: "+username); 
        String trainId = request.getParameter("trainId");
       
        String trainName = ""; // Default train name
        String source = ""; // Default source station
        String destination = ""; // Default destination station
        Timestamp departureTime = Timestamp.valueOf("2023-07-14 06:00:00.000000"); // Default departure time
        Timestamp arrivalTime = Timestamp.valueOf("2023-07-14 08:00:00.000000"); // Default arrival time

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Enable auto-commit
            con.setAutoCommit(true);

            // Retrieve username from the database
            //String uname = request.getParameter("uname");
            String selectUsernameQuery = "SELECT UNAME FROM USRREG WHERE UNAME = ?";
            PreparedStatement selectUsernameStmt = con.prepareStatement(selectUsernameQuery);
            selectUsernameStmt.setString(1, username);
            ResultSet usernameResultSet = selectUsernameStmt.executeQuery();
            if (usernameResultSet.next()) {
                username = usernameResultSet.getString("UNAME");
            }
            usernameResultSet.close();
            selectUsernameStmt.close();

            // Retrieve train details from the database
            String selectTrainDetailsQuery = "SELECT NAME, SOURCE, DESTINATION, DEPATURE, ARRIVAL FROM TRAIN WHERE ID = ?";
            PreparedStatement selectTrainDetailsStmt = con.prepareStatement(selectTrainDetailsQuery);
            selectTrainDetailsStmt.setString(1, trainId);
            ResultSet trainDetailsResultSet = selectTrainDetailsStmt.executeQuery();
            if (trainDetailsResultSet.next()) {
                trainName = trainDetailsResultSet.getString("NAME");
                source = trainDetailsResultSet.getString("SOURCE");
                destination = trainDetailsResultSet.getString("DESTINATION");
                departureTime = trainDetailsResultSet.getTimestamp("DEPATURE");
                arrivalTime = trainDetailsResultSet.getTimestamp("ARRIVAL");
            }
            trainDetailsResultSet.close();
            selectTrainDetailsStmt.close();
            
            // Insert the booking details into the "BOOKINGS" table
            String insertBookingQuery = "INSERT INTO  BOOKINGS_MADE (BOOKING_ID, UNAME, TRAIN_ID, TRAIN_NAME, SOURCE, DESTINATION, DEPARTURE_TIME, ARRIVAL_TIME) VALUES (book_seq.NEXTVAL,?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertBookingStmt = con.prepareStatement(insertBookingQuery);
            insertBookingStmt.setString(1, username);
            insertBookingStmt.setString(2, trainId);
            insertBookingStmt.setString(3, trainName);
            insertBookingStmt.setString(4, source);
            insertBookingStmt.setString(5, destination);
            insertBookingStmt.setTimestamp(6, departureTime);
            insertBookingStmt.setTimestamp(7, arrivalTime);

            // Execute the insert statement to update the "BOOKINGS" table
            insertBookingStmt.executeUpdate();
            insertBookingStmt.close();

            // Close the connection
            con.close();


            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        // Send the response with the retrieved details
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        pw.println("<html>");
        pw.println("<head>");
        pw.println("<meta charset=\"UTF-8\">");
        pw.println("<title>Booking Confirmation</title>");
        pw.println("<link rel=\"stylesheet\" href=\"style.css\">");
        pw.println("<style>");
        pw.println("h2 {");
        pw.println("  text-align: center;");
        pw.println("}");
        pw.println(".container {");
        pw.println("  width: 400px;");
        pw.println("  padding: 20px;");
        pw.println("  height: 400px;");
        pw.println("  background-color: #fff;");
        pw.println("  border-radius: 10px;");
        pw.println("  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);");
        pw.println("  margin: auto;"); // Center the container horizontally and vertically
        pw.println("  margin-top: 50px;");
        pw.println("}");
        pw.println("p {");
        pw.println("  margin-bottom: 10px;");
        pw.println("}");
        pw.println("</style>");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<div class=\"container\">");
        pw.println("<h2>Booking Confirmation</h2>");
        if (!username.isEmpty()) {
            pw.println("<p>Name: " + username + "</p>");
        }
        pw.println("<p>Train Name: " + trainName + "</p>");
        pw.println("<p>Source: " + source + "</p>");
        pw.println("<p>Destination: " + destination + "</p>");
        pw.println("<p>Departure Time: " + departureTime + "</p>");
        pw.println("<p>Arrival Time: " + arrivalTime + "</p>");

        pw.println("<p style=\"text-align: center;\">You have successfully booked train with ID: " + trainId + "</p>");
        
        
        pw.println("<form action=\"LogoutPage\" method=\"post\">");
        pw.println("<input type=\"submit\" value=\"Logout\">");
        pw.println("</form>");
        pw.println("</div>");
        pw.println("</body>");
        pw.println("</html>");

        pw.flush();
    }
}
