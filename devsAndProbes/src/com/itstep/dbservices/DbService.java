package com.itstep.dbservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbService {
	private static Connection conn;
	private static DbService.DatabaseParameters dbParams;

	public static void setDbParams(DbService.DatabaseParameters dbp) {
		dbParams = dbp;
	}

	public static void connectTo(String classname, String URL, String user, String passw) throws SQLException {
		conn = DbConnection.getConnection(classname, URL, user, passw);
	}

	public static void connect() throws SQLException {
		if (dbParams == null) {
			throw new SQLException("No database parameters set!");
		}
		conn = DbConnection.getConnection(dbParams.getDbClass(), dbParams.getDbURL(), dbParams.getDbUser(),
				dbParams.getDbPass());
	}

	public static void firstJdbcRun() {
		try {
			if (!isConnected()) {
				connect();
			}
			String CREATE_TABLES = "CREATE TABLE DEVICES(ID INT PRIMARY KEY, "
					+ "NAME VARCHAR(255) NOT NULL, PORT INT NOT NULL); "
					+ "CREATE TABLE PROBES(ID INT PRIMARY KEY, APP VARCHAR(255) NOT NULL, "
					+ "TYPE VARCHAR(255) NOT NULL); "
					+ "CREATE TABLE PROBES_RELATIONS(ID INT PRIMARY KEY, PROBE_ID INT NOT NULL, "
					+ "SOURCE_ID INT NOT NULL, DESTINATION_ID INT NOT NULL); "
					+ "INSERT INTO DEVICES VALUES(1, 'Cisco', 8080); "
					+ "INSERT INTO DEVICES VALUES(11, 'D-Link', 8081); "
					+ "INSERT INTO DEVICES VALUES(23, 'TP-Link', 8082); "
					+ "INSERT INTO DEVICES VALUES(4, 'ASUS', 8888); "
					+ "INSERT INTO DEVICES VALUES(43, 'Xiaomi', 7777); "
					+ "INSERT INTO DEVICES VALUES(48, 'Virtual router', 3128);";
			Statement st = conn.createStatement();
			st.executeUpdate(CREATE_TABLES);
		} catch (Exception ex) {
		}
	}

	public static ResultSet executeSelect(String query) throws SQLException {
		if (!isConnected()) {
			connect();
		}
		Statement st = null;
		ResultSet res = null;
		st = conn.createStatement();
		res = st.executeQuery(query);
		return res;
	}

	public static ResultSet executeSelect(String query, String... args) throws SQLException {
		if (!isConnected()) {
			connect();
		}
		PreparedStatement pst = null;
		ResultSet res = null;
		pst = conn.prepareStatement(query);
		insertQueryParams(pst, args);
		res = pst.executeQuery();
		return res;
	}

	public static int executeUpdate(String query) throws SQLException {
		if (!isConnected()) {
			connect();
		}
		if (!isUpdateQuery(query)) {
			throw new SQLException("Query\n'" + query + "'\n is not INSERT, UPDATE or DELETE query!");
		}
		Statement st = null;
		st = conn.createStatement();
		return st.executeUpdate(query);
	}

	public static int executeUpdate(String query, String... args) throws SQLException {
		if (!isConnected()) {
			connect();
		}
		if (!isUpdateQuery(query)) {
			throw new SQLException("Query\n'" + query + "'\n is not INSERT, UPDATE or DELETE query!");
		}
		PreparedStatement pst = null;
		pst = conn.prepareStatement(query);
		insertQueryParams(pst, args);
		return pst.executeUpdate();
	}

	private static void insertQueryParams(PreparedStatement pst, String[] args) {
		try {
			for (int i = 0; i < args.length; i++) {
				if (isLong(args[i])) {
					pst.setLong(i + 1, Long.parseLong(args[i]));
				} else if (isDouble(args[i])) {
					pst.setDouble(i + 1, Double.parseDouble(args[i]));
				} else {
					pst.setString(i + 1, args[i]);
				}
			}
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
	}

	private static boolean isConnected() throws SQLException {
		return !(conn == null || conn.isClosed());
	}

	private static boolean isUpdateQuery(String query) {
		return query.toUpperCase().trim().startsWith("INSERT") || query.toUpperCase().trim().startsWith("UPDATE")
				|| query.toUpperCase().trim().startsWith("DELETE");
	}

	private static boolean isLong(String str) {
		boolean result = true;
		try {
			Long.valueOf(str);
		} catch (NumberFormatException nfe) {
			result = false;
		}
		return result;
	}

	private static boolean isDouble(String str) {
		boolean result = true;
		try {
			Double.valueOf(str);
		} catch (NumberFormatException nfe) {
			result = false;
		}
		return result;
	}

	public interface DatabaseParameters {
		String getDbClass();

		String getDbUrlStart();

		String getDbDatabaseName();

		String getDbURL();

		String getDbUser();

		String getDbPass();
	}

	/*
	 * Startup test database script: =============================================
	 * CREATE TABLE DEVICES(ID INT PRIMARY KEY, NAME VARCHAR(255) NOT NULL, PORT INT
	 * NOT NULL); CREATE TABLE PROBES(ID INT PRIMARY KEY, APP VARCHAR(255) NOT NULL,
	 * TYPE VARCHAR(255) NOT NULL); CREATE TABLE PROBES_RELATIONS(ID INT PRIMARY
	 * KEY, PROBE_ID INT NOT NULL, SOURCE_ID INT NOT NULL, DESTINATION_ID INT NOT
	 * NULL); INSERT INTO DEVICES VALUES(1, 'Cisco', 8080); INSERT INTO DEVICES
	 * VALUES(11, 'D-Link', 8081); INSERT INTO DEVICES VALUES(23, 'TP-Link', 8082);
	 * INSERT INTO DEVICES VALUES(4, 'ASUS', 8888); INSERT INTO DEVICES VALUES(43,
	 * 'Xiaomi', 7777); INSERT INTO DEVICES VALUES(48, 'Virtual router', 3128);
	 */
}
