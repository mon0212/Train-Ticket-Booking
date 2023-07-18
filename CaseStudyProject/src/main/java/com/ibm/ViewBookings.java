package com.ibm;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewBookings extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String DB_USER = "JAVA";
    private static final String DB_PASSWORD = "JAVA";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");

    
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM BOOKINGS_MADE");
            ResultSet rs = pstmt.executeQuery();

            pw.println("<html>");
            pw.println("<head>");
            pw.println("<title>View Bookings</title>");
            pw.println("<link rel=\"stylesheet\" href=\"style.css\">");
            pw.println("<style>");
            pw.println("h2 {");
            pw.println("  text-align: center;");
 
            pw.println("}");

            pw.println("</style>");
            pw.println("</head>");
            pw.println("<body>");

           
            pw.println("<table>");
            pw.println("<tr>");
            pw.println("<th>Booking ID</th>");
            pw.println("<th>Train ID</th>");
            pw.println("<th>USERNAME</th>");
            pw.println("<th>Train Name</th>");
            // Add more table headers for additional columns in the Booking table
            pw.println("</tr>");

            while (rs.next()) {
                int bookingId = rs.getInt("BOOKING_ID");
                String trainId = rs.getString("TRAIN_ID");
                String uname=rs.getString("UNAME");
                String trainName=rs.getString("TRAIN_NAME");
                //String bookingTime = rs.getString("BOOKING_TIME");
                // Add more table data for additional columns in the Booking table
                pw.println("<tr>");
                pw.println("<td>" + bookingId + "</td>");
                pw.println("<td>" + trainId + "</td>");
                pw.println("<td>" + uname + "</td>");
                pw.println("<td>" + trainName + "</td>");
                //pw.println("<td>" + bookingTime + "</td>");
                // Add more table data for additional columns in the Booking table
                pw.println("</tr>");
            }

            pw.println("</table>");
            pw.println("</body>");
            pw.println("</html>");

            rs.close();
            pstmt.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        
    }
}
