package com.zeljic.dbexec.db.connectors;

import java.util.List;
import java.util.Properties;

import com.zeljic.dbexec.db.Row;

public interface IConnector
{
	boolean executeQuery(String query);

	String getErrorMessage();

	int getErrorCode();

	List<String> getColumns();

	List<Row> getRows();

	String getClassName();

	void setClassName(String className);

	String getConnString();

	void setConnString(String connString);

	void setProperties(Properties properties);
}
