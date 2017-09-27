package com.itstep.dbservices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	private static volatile Connection conn;

	private DbConnection() {
	}

	@SuppressWarnings("resource")
	public static Connection getConnection(String classname, String connectionURL, String login, String password) {
		Connection localConn = conn;
		if (localConn == null) {
			synchronized (DbConnection.class) {
				localConn = conn;
				if (localConn == null) {
					try {
						Class.forName(classname);
						conn = localConn = DriverManager.getConnection(connectionURL, login, password);
					} catch (ClassNotFoundException | SQLException ex) {
						ex.printStackTrace();
					}
				}
			}
		} else {
			synchronized(DbConnection.class) {
				try {
					if(!localConn.isValid(0)) {
						conn = localConn = DriverManager.getConnection(connectionURL, login, password);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return localConn;
	}
}
