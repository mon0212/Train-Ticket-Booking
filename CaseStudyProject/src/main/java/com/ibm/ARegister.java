package com.ibm;
import oracle.jdbc.driver.OracleDriver;
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

public class ARegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		String n=request.getParameter("name");
		String e=request.getParameter("email");
		String u=request.getParameter("uname");	
		String p=request.getParameter("pwd");
		
		Driver d= new oracle.jdbc.driver.OracleDriver();
		try {
			DriverManager.registerDriver(d);
		System.out.println("Driver Registraion is Done");
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","JAVA","JAVA");
		System.out.println("Got The Connection : " +con.getClass());
		PreparedStatement pstmt=con.prepareStatement("INSERT INTO ADMREG VALUES(?,?,?,?)");	
		pstmt.setString(1,n);
		pstmt.setString(2,e);
		pstmt.setString(3,u);		
		pstmt.setString(4,p);
	
		pstmt.executeUpdate();
		System.out.println("Registration done");
		response.sendRedirect("AfterAReg.html");
	
		} catch (SQLException ex) {
			
			ex.printStackTrace();
		}
		
	}

}
