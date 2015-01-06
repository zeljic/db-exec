package com.zeljic.dbexec.db.connectors;


public class SQLite3Connector extends Connector
{
	public SQLite3Connector()
	{
		setClassName("org.sqlite.JDBC");
	}

	public void setFilePath(String filePath)
	{
		this.setConnString("jdbc:sqlite:" + filePath);
	}
}
