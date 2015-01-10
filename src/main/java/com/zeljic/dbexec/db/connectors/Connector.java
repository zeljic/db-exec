package com.zeljic.dbexec.db.connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.zeljic.dbexec.db.Row;

public abstract class Connector implements IConnector
{
	private String className = "";
	private String connString = "";
	private String errorMessage = "";
	private int errorCode = 0;
	private List<String> columns = new ArrayList<String>();
	private ObservableList<Row> rows = FXCollections.observableArrayList();
	private Properties properties = new Properties();

	@Override
	public String getClassName()
	{
		return className;
	}

	@Override
	public void setClassName(String className)
	{
		this.className = className;

		try
		{
			Class.forName(className);
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public String getConnString()
	{
		return connString;
	}

	@Override
	public void setConnString(String connString)
	{
		this.connString = connString;
	}

	@Override
	public List<String> getColumns()
	{
		return columns;
	}

	@Override
	public ObservableList<Row> getRows()
	{
		return rows;
	}

	@Override
	public String getErrorMessage()
	{
		return errorMessage;
	}

	@Override
	public int getErrorCode()
	{
		return errorCode;
	}

	@Override
	public void setProperties(Properties properties)
	{
		this.properties = properties;
	};

	@Override
	public boolean executeQuery(String query)
	{
		columns.clear();
		rows.clear();

		Statement stmt = null;
		ResultSet rs = null;

		try (Connection conn = DriverManager.getConnection(getConnString(), properties))
		{
			// hack for read-only query
			conn.setAutoCommit(false);

			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			ResultSetMetaData rmd = rs.getMetaData();
			int size = rmd.getColumnCount();

			for (int i = 0; i < size; i++)
				columns.add(rmd.getColumnLabel(i + 1));

			while (rs.next())
			{
				Row row = new Row();

				for (int i = 0; i < size; i++)
					row.setData(i, rs.getString(i + 1));

				rows.add(row);
			}
		} catch (SQLException e)
		{
			errorMessage = e.getMessage();
			errorCode = e.getErrorCode();

			return false;
		} finally
		{
			if (stmt != null)
				try
				{
					stmt.close();
				} catch (SQLException e)
				{

				}
		}

		return true;
	}
}