package com.ibm;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddTrain extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        String trainName = request.getParameter("trainName");
        String trainNumber = request.getParameter("trainNumber");
        String source = request.getParameter("source");
        String destination = request.getParameter("destination");
        String departure = request.getParameter("departure");
        String arrival = request.getParameter("arrival");

        Driver d = new oracle.jdbc.driver.OracleDriver();
        try {
            DriverManager.registerDriver(d);
            System.out.println("Driver Registration is Done");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "JAVA", "JAVA");
            System.out.println("Got The Connection : " + con.getClass());
            PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO TRAIN (ID, NAME, SOURCE, DESTINATION, DEPATURE, ARRIVAL) " +
                "VALUES (?, ?, ?, ?, TO_TIMESTAMP(?, 'YYYY-MM-DD\"T\"HH24:MI'), TO_TIMESTAMP(?, 'YYYY-MM-DD\"T\"HH24:MI'))");
            pstmt.setString(1, trainNumber);
            pstmt.setString(2, trainName);
        
            pstmt.setString(3, source);
            pstmt.setString(4, destination);
            pstmt.setString(5, departure);
            pstmt.setString(6, arrival);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                pw.println("<html>");
                pw.println("<head>");
                pw.println("<meta charset=\"UTF-8\">");
                pw.println("<title>Train Added</title>");
                pw.println("<link rel=\"stylesheet\" href=\"style.css\">");
                pw.println("</head>");
                pw.println("<body>");
                pw.println("<div class=\"container\">");
                pw.println("<h2>Train Added Successfully</h2>");
                pw.println("<form action=\"Login\" method=\"post\">");
                pw.println("<input type=\"submit\" value=\"Go Back\" class=\"btn\">");
                pw.println("</form>");
                pw.println("</div>");
                pw.println("</body>");
                pw.println("</html>");
            } else {
                pw.println("Failed to add the train. Please try again.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
