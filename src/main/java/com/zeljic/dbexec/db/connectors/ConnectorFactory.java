package com.zeljic.dbexec.db.connectors;

public class ConnectorFactory
{

	public enum ConnectionType
	{
		SQLite3, MySQL
	}

	public IConnector getConnector(ConnectionType type)
	{
		if (type == null)
			return null;

		switch (type)
		{
		case SQLite3:
			return new SQLite3Connector();
		case MySQL:
			return new SQLite3Connector();
		}

		return null;
	}
}
