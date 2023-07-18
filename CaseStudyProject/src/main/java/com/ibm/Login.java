package com.ibm;



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		String u=request.getParameter("uname");
	
		String p=request.getParameter("pwd");
		// Set the username in the session
		HttpSession session = request.getSession();
		session.setAttribute("username", u); 
		Driver d= new oracle.jdbc.driver.OracleDriver();
		try {
			DriverManager.registerDriver(d);
		System.out.println("Driver Registraion is Done");
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","JAVA","JAVA");
		System.out.println("Got The Connection : " +con.getClass());
		PreparedStatement pstmt=con.prepareStatement("SELECT uname,password FROM ADMREG WHERE uname = ? AND password = ?");	
		pstmt.setString(1, u);
        pstmt.setString(2, p);
		ResultSet rs=pstmt.executeQuery();
		
		 if (rs.next()) {
			 pw.println("<html>");
             pw.println("<head>");
             pw.println("<meta charset=\"UTF-8\">");
             pw.println("<title>Login Successful</title>");
             pw.println("<link rel=\"stylesheet\" href=\"style.css\">");
             pw.println("<style>");
             pw.println(".container {");
             pw.println("  width: 300px;");
             pw.println("  height: 400px;");
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
             pw.println("<h2>Welcome, " + u + "</h2>");
             pw.println("<form action=\"ViewUser\" method=\"post\">");
             pw.println("<input type=\"submit\" value=\"View Users\">");
             pw.println("</form>");
             
             
             pw.println("<form action=\"ViewTrains\" method=\"post\">"); 
             pw.println("<input type=\"submit\" value=\"View Trains\" class=\"btn\">"); // 
             pw.println("</form>");
             
             
             pw.println("<form action=\"ViewBookings\" method=\"post\">");
             pw.println("<input type=\"submit\" value=\"View Bookings\">");
             pw.println("</form>");
             
             pw.println("<a href=\"AddTrain.jsp\"><button class=\"btn\">Add Train</button></a>");
             pw.println("<form action=\"LogoutPage\" method=\"post\">");
             pw.println("<input type=\"submit\" value=\"Logout\">");
             pw.println("</form>");
         
             
    
             pw.println("</div>");
             pw.println("</body>");
             pw.println("</html>");
         } else {
             // User credentials are invalid
             pw.println("Invalid username or password");
             response.sendRedirect("AfterAReg.html");
         }
		
		} catch (SQLException ex) {
			
			ex.printStackTrace();
		}
		
	}

}
