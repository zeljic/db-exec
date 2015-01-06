package com.zeljic.dbexec.db.connectors;

public class SQLite3Connector extends Connector
{
	public SQLite3Connector()
	{
		setClassName("org.sqlite.JDBC");
	}
}
