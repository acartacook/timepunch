package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;
import java.io.*;
import java.util.*;

public class DBTrial {
	
	private  final String url = "jdbc:mysql://localhost:3306/timepunch";
	private final String user = "root";
	private final String password = "root";
	
	private  Connection con = null;
	private  Statement stmt = null;
	private  PreparedStatement pstmt= null;
	private static ResultSet rs;
	private Scanner input = new Scanner(System.in);
//	private ArrayList<String> movieGenre = new ArrayList<String>();
	
	public void getId()
	{
		try
		{
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();
			
			String sql = "SELECT ID FROM department";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				System.out.println(rs.getInt("ID"));
				
			}
		}
		catch(SQLException se)
		{
			 se.printStackTrace(); 
		}
	}

	public static void main(String[] args)
	{
		DBTrial db = new DBTrial();
		db.getId();
	}
}
