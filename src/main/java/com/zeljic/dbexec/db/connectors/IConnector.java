package com.zeljic.dbexec.db.connectors;

import java.util.List;
import java.util.Properties;

import com.zeljic.dbexec.db.Row;

public interface IConnector
{
	public boolean executeQuery(String query);

	public String getErrorMessage();

	public int getErrorCode();

	public List<String> getColumns();

	public List<Row> getRows();

	public String getClassName();

	public void setClassName(String className);

	public String getConnString();

	public void setConnString(String connString);

	public void setProperties(Properties properties);
}
