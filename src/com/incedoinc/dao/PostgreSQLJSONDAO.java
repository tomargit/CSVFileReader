package com.incedoinc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class PostgreSQLJSONDAO {

	public boolean insert(String data) {
		Connection conn = null;
		Statement stmt = null;
		conn = getConnection();

		System.out.println(data);
		try {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO Patient_Visit_Clinical_Data (Data) VALUES (?::JSON)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setObject(1, data);
			ps.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
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