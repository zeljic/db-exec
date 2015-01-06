package com.zeljic.dbexec.db;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Row
{
	private Map<Integer, String> store = new HashMap<>();

	public void setData(int columnIdx, String value)
	{
		store.put(columnIdx, value);
	}

	public StringProperty getData(int columnIdx)
	{
		StringProperty s = new SimpleStringProperty();
		s.set(store.get(columnIdx));

		return s;
	}
}