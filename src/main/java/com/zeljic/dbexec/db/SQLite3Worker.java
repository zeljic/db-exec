package com.zeljic.dbexec.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SQLite3Worker
{
	private String path;
	private List<String> columns;
	private ObservableList<Row> rows = FXCollections.observableArrayList();

	public SQLite3Worker()
	{
		try
		{
			Class.forName("org.sqlite.JDBC").newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}

	public void setDBPath(String path)
	{
		this.path = path;
	}

	public List<String> getColumns()
	{
		return columns;
	}

	public ObservableList<Row> getRows()
	{
		return rows;
	}

	public boolean executeQuery(String query)
	{
		columns = new ArrayList<String>();

		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query);)
		{
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
			e.printStackTrace();
		}

		return true;
	}

}
