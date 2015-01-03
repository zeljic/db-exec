package com.zeljic.dbexec.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLite3Worker
{
	private String path;

	public SQLite3Worker(String path)
	{
		this.path = path;

		try
		{
			Class.forName("org.sqlite.JDBC").newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}

	public ResultSet executeQuery(String query)
	{
		ResultSet result = null;

		try
		{
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
			Statement stmt = conn.createStatement();
			result = stmt.executeQuery(query);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	public void getList(String query) throws Exception
	{
		ResultSet rs = executeQuery(query);
		ResultSetMetaData rmd = rs.getMetaData();
		int size = rmd.getColumnCount();

		while (rs.next())
		{
			for (int i = 0; i < size; i++)
			{
				System.out.println(rmd.getColumnLabel(i + 1) + ":" + rs.getString(i + 1));
			}
		}
	}
}
