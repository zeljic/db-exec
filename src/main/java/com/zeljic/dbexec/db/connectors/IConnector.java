package com.zeljic.dbexec.db.connectors;

import java.util.List;

import javafx.collections.ObservableList;

import com.zeljic.dbexec.db.Row;

public interface IConnector
{
	public boolean executeQuery(String query);

	public String getErrorMessage();

	public int getErrorCode();

	public List<String> getColumns();

	public ObservableList<Row> getRows();

	public String getClassName();

	public void setClassName(String className);

	public String getConnString();

	public void setConnString(String connString);

	public void setColumns(List<String> columns);

	public void setRows(ObservableList<Row> rows);
}
