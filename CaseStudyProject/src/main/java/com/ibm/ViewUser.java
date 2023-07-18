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

public class ViewUser extends HttpServlet {
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

            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM USRREG");
            ResultSet rs = pstmt.executeQuery();

            pw.println("<html>");
            pw.println("<head>");
            pw.println("<link rel=\"stylesheet\" href=\"style.css\">");
            pw.println("<title>View Users</title>");
            pw.println("<style>");
            pw.println("h2 {"); // Add the styles for the h2 element
            pw.println("  font-family: 'Arial', sans-serif;");
            pw.println("  font-size: 28px;");
            pw.println("  color: #fff;");
            pw.println("  margin-left: 280px;");
            pw.println("  text-align: center;");
            pw.println("  position: absolute;");
            pw.println(" top: 100px;");
            pw.println("  left: 75px;");
            pw.println("  text-shadow: 2px 2px 4px rgba(0, 0, 0, 1);");
            pw.println("}");
            pw.println("</style>");
            pw.println("</head>");
            pw.println("<body>");

            pw.println("<h2>Users</h2>");
            pw.println("<table>");
            pw.println("<tr>");
            pw.println("<th>First Name</th>");
            pw.println("<th>Last Name</th>");
            pw.println("<th>Email</th>");
            pw.println("<th>Mobile</th>");
            pw.println("<th>Username</th>");
            pw.println("<th>Password</th>");
     
            pw.println("</tr>");

            while (rs.next()) {
                String firstName = rs.getString("FNAME");
                String lastName = rs.getString("LNAME");
                String email = rs.getString("EMAIL");
                String mobile = rs.getString("MOB");
                String username = rs.getString("UNAME");
                String password = rs.getString("PASSWORD");
            
                pw.println("<tr>");
                pw.println("<td>" + firstName + "</td>");
                pw.println("<td>" + lastName + "</td>");
                pw.println("<td>" + email + "</td>");
                pw.println("<td>" + mobile + "</td>");
                pw.println("<td>" + username + "</td>");
                pw.println("<td>" + password + "</td>");
             
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
