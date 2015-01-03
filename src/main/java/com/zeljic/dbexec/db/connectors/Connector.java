package com.zeljic.dbexec.db.connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.zeljic.dbexec.db.Row;

public abstract class Connector
{
	private String className = "";
	private String connString = "";
	private String errorMessage = "";
	private int errorCode = 0;
	private ArrayList<String> columns = new ArrayList<String>();
	private ObservableList<Row> rows = FXCollections.observableArrayList();

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;

		try
		{
			Class.forName(className).newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}

	public String getConnString()
	{
		return connString;
	}

	public void setConnString(String connString)
	{
		this.connString = connString;
	}

	public ArrayList<String> getColumns()
	{
		return columns;
	}

	public void setColumns(ArrayList<String> columns)
	{
		this.columns = columns;
	}

	public ObservableList<Row> getRows()
	{
		return rows;
	}

	public void setRows(ObservableList<Row> rows)
	{
		this.rows = rows;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public int getErrorCode()
	{
		return errorCode;
	}

	public boolean executeQuery(String query)
	{
		setColumns(new ArrayList<String>());

		try (Connection conn = DriverManager.getConnection(getConnString()); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query);)
		{
			ResultSetMetaData rmd = rs.getMetaData();
			int size = rmd.getColumnCount();

			for (int i = 0; i < size; i++)
				getColumns().add(rmd.getColumnLabel(i + 1));

			while (rs.next())
			{
				Row row = new Row();

				for (int i = 0; i < size; i++)
					row.setData(i, rs.getString(i + 1));

				getRows().add(row);
			}
		} catch (SQLException e)
		{
			errorMessage = e.getMessage() + System.lineSeparator() + e.getStackTrace();
			errorCode = e.getErrorCode();

			return false;
		}

		return true;
	}
}
