package com.zeljic.dbexec.db.connectors;

public class MySQLConnector extends Connector
{
	public MySQLConnector()
	{
		setClassName("com.mysql.cj.jdbc.Driver");
	}
}
