package com.zeljic.dbexec.db.connectors;


public class SQLite3Connector extends Connector
{
	private String filePath = "";

	public SQLite3Connector()
	{
		setClassName("org.sqlite.JDBC");
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
		this.setConnString("jdbc:sqlite:" + this.filePath);
	}
}
