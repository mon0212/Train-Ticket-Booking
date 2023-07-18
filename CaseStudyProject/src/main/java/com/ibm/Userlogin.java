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
import javax.servlet.http.HttpSession;

public class Userlogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
       
        String u = request.getParameter("uname");
        System.out.println("Username from login form: " + u); 
        if (u != null && !u.isEmpty()) {
            // Set the username in the session
            HttpSession session = request.getSession();
            session.setAttribute("username", u);

        }
        String p = request.getParameter("pwd");
    	
		
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "JAVA", "JAVA");

            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM USRREG WHERE uname = ? AND password = ?");
            pstmt.setString(1, u);
            pstmt.setString(2, p);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String f = rs.getString("fname");
                String l = rs.getString("lname");
                String m = rs.getString("mob");
                String e = rs.getString("email");
                String u1 = rs.getString("uname");
                String p1 = rs.getString("password");

                pw.println("<html>");
                pw.println("<head>");
                pw.println("<meta charset=\"UTF-8\">");
                pw.println("<title>User Profile</title>");
                pw.println("<link rel=\"stylesheet\" href=\"style.css\">");
                pw.println("<style>");
                pw.println(".container {");
                pw.println("  width: 300px;");
                pw.println("  height: 500px;");
                pw.println("  display: flex;");
                pw.println("  flex-direction: column;");
                pw.println("  justify-content: center;");
                pw.println("  align-items: center;");
                pw.println("  padding: 20px;");
                pw.println("  background-color: rgba(255, 255, 255, 0.9);");
                pw.println("  border-radius: 10px;");
                pw.println("  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);");
                pw.println("  margin: auto;"); /* Center the container horizontally by using margin: 0 auto; */
                pw.println("  margin-left: 20px;");
                pw.println("}");
                pw.println("</style>");
                pw.println("</head>");
                pw.println("<body>");
                pw.println("<div class=\"container\">");
                pw.println("<h2>Welcome, " + f + "</h2>");
                pw.println("<div class=\"profile-info\">");
                pw.println("<p>First Name: " + f + "</p>");
                pw.println("<p>Last Name: " + l + "</p>");
                pw.println("<p>Mobile: " + m + "</p>");
                pw.println("<p>Email: " + e + "</p>");
                pw.println("<p>Username: " + u1 + "</p>");
                pw.println("<p>Password: " + p1 + "</p>");
                pw.println("</div>");
                pw.println("<form action='BookTicket' method='post'>");
                pw.println("<input type='submit' value='Book Ticket'>");
                pw.println("</form>");
                pw.println("</div>");
                pw.println("</body>");
                pw.println("</html>");

            } else {
                // User credentials are invalid
                pw.println("Invalid username or password");
                response.sendRedirect("AfterUReg.jsp");
            }

            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}

