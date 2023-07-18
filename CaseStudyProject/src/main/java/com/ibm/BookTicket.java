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

public class BookTicket extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String DB_USER = "JAVA";
    private static final String DB_PASSWORD = "JAVA";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	String username = (String) session.getAttribute("username");
         System.out.println(username);

    	// Use the username to store the booking details along with the user's identity.

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "SELECT * FROM train";
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            pw.println("<html>");
            pw.println("<head>");
            pw.println("<title>Train Table</title>");
            pw.println("<link rel=\"stylesheet\" href=\"style.css\">");
            pw.println("<style>");
            pw.println("h2 {"); // Add the styles for the h2 element
            pw.println("  font-family: 'Arial', sans-serif;");
            pw.println("  font-size: 28px;");
            pw.println("  color: #fff;");
            pw.println("  margin: 0;");
            pw.println("  text-align: center;");
            pw.println("  position: absolute;");
           pw.println("  top: 40px;");
            pw.println("  left: 150px;");
            pw.println("  text-shadow: 2px 2px 4px rgba(0, 0, 0, 1);");
            pw.println("}");
            pw.println("table {");
            pw.println("  border-collapse: collapse;");
            pw.println("  width: 75%;");
            pw.println("  height: 75%;");
            pw.println("  background-color: #f2f2f2;");
            pw.println("  margin-left: 40px;");
            pw.println("  margin-right: 40px;");
            pw.println("  margin-top: 20px;");
            pw.println("  margin-bottom: 20px;");
            pw.println("}");
            pw.println("</style>");
           
            pw.println("</head>");
            pw.println("<body>");

            pw.println("<h2>Available Trains</h2>");
            pw.println("<table>");
            pw.println("<tr>");
            pw.println("<th>ID</th>");
            pw.println("<th>Name</th>");
            pw.println("<th>Source</th>");
            pw.println("<th>Destination</th>");
            pw.println("<th>Depature </th>");
            pw.println("<th>Arrival</th>");
            pw.println("<th> </th>");
            pw.println("</tr>");
             
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String source = rs.getString("SOURCE");
                String destination = rs.getString("DESTINATION");
                Timestamp depature=rs.getTimestamp("DEPATURE");
                Timestamp arrival=rs.getTimestamp("ARRIVAL");

                pw.println("<tr>");
                pw.println("<td>" + id + "</td>");
                pw.println("<td>" + name + "</td>");
                pw.println("<td>" + source + "</td>");
                pw.println("<td>" + destination + "</td>");
                pw.println("<td>" + depature +"</td>");
                pw.println("<td>" + arrival +"</td>");
              
                pw.println("<td><form action=\"BookConfirmation\" method=\"post\">");
                pw.println("<input type=\"hidden\" name=\"trainId\" value=\"" + id + "\">");
                pw.println("<input type=\"submit\" name=\"bookButton\" value=\"Book\">");

                pw.println("</form></td>");
           
                pw.println("</tr>");
            }

            pw.println("</table>");
            pw.println("</body>");
            pw.println("</html>");

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        pw.flush();
    }
}
