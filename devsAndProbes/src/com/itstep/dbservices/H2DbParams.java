package com.itstep.dbservices;

public class H2DbParams implements DbService.DatabaseParameters {
	private final String DRIVER_CLASS = "org.h2.Driver";
	private final String DB_PROTOCOL = "jdbc:h2:~/";
	private final String DB_DATABASE = "devsandprobes";
	private final String DB_URL = DB_PROTOCOL + DB_DATABASE;
	private final String DB_USER = "sa";
	private final String DB_PASS = "";
	
	@Override
	public String getDbClass() { return DRIVER_CLASS; }

	@Override
	public String getDbUrlStart() { return DB_PROTOCOL; }

	@Override
	public String getDbDatabaseName() { return DB_DATABASE; }

	@Override
	public String getDbURL() { return DB_URL; }
	
	@Override
	public String getDbUser() { return DB_USER; }

	@Override
	public String getDbPass() { return DB_PASS; }
}
