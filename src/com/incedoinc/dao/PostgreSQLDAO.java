package com.incedoinc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class PostgreSQLDAO {

	public boolean insert(StringBuilder columnString, StringBuilder columnValue) {
		Connection c = null;
		Statement stmt = null;
		try {

			c = getConnection();
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "INSERT INTO Patient_Visit_Clinical_Data (" + columnString.toString() + ") "
					+ "VALUES ("+columnValue.toString()+");";
			
			System.out.println(sql);
			
			stmt.executeUpdate(sql);

						stmt.close();
			c.commit();
			c.close();
			return true;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Records created successfully");
		return false;
	}

	public static Connection getConnection() {
		Connection c = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "manish", "tomar");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
		return c;
	}
}