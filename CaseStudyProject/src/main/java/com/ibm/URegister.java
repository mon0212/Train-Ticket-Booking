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

public class URegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		String f=request.getParameter("fname");
		String l=request.getParameter("lname");
		String e=request.getParameter("email");
		String u=request.getParameter("uname");	
		String p=request.getParameter("pwd");
		String mb =request.getParameter("mob");
		long m=Long.parseLong(mb);
		
		Driver d= new oracle.jdbc.driver.OracleDriver();
		try {
			DriverManager.registerDriver(d);
		System.out.println("Driver Registraion is Done");
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","JAVA","JAVA");
		System.out.println("Got The Connection : " +con.getClass());
		PreparedStatement pstmt=con.prepareStatement("INSERT INTO USRREG (fname, lname, email, mob, uname, password) VALUES (?, ?, ?, ?, ?, ?)");	
		pstmt.setString(1,f);
		pstmt.setString(2, l);
		pstmt.setString(3,e);
		pstmt.setLong(4,m);
		pstmt.setString(5,u);		
		pstmt.setString(6,p);
	
		pstmt.executeUpdate();
		System.out.println("Registration done");
		response.sendRedirect("AfterUReg.jsp");
	
		} catch (SQLException ex) {
			
			ex.printStackTrace();
		}}}
