package com.zeljic.dbexec.db.connectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.zeljic.dbexec.controllers.IConnectorController;
import com.zeljic.dbexec.uil.Loader;
import com.zeljic.dbexec.utils.R;

public class ConnectorItem
{
	public enum Type
	{
		SQLite3, MySQL
	}

	private String display;
	private Type type;
	private String fxmlPath;
	private Loader loader;
	private IConnectorController controller;
	private static ObservableList<ConnectorItem> register = FXCollections.observableArrayList();

	static
	{
		register.add(new ConnectorItem(ConnectorItem.Type.SQLite3, "SQLite 3", "/fxml/ConnectorSQLite3.fxml"));
		register.add(new ConnectorItem(ConnectorItem.Type.MySQL, "MySQL", "/fxml/ConnectorMySQL.fxml"));
	}

	public ConnectorItem(Type type, String display, String fxmlPath)
	{
		this.type = type;
		this.display = display;
		this.fxmlPath = fxmlPath;

		loader = Loader.setInstance(display, R.get(fxmlPath));
		controller = (IConnectorController) loader.getRawController();
	}

	public String getDisplay()
	{
		return display;
	}

	public void setDisplay(String display)
	{
		this.display = display;
	}

	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}

	public String getFxmlPath()
	{
		return fxmlPath;
	}

	public void setFxmlPath(String fxmlPath)
	{
		this.fxmlPath = fxmlPath;
	}

	public Loader getLoader()
	{
		return loader;
	}

	public IConnectorController getControllerClass()
	{
		return controller;
	}

	@Override
	public String toString()
	{
		return getDisplay();
	}

	public static ObservableList<ConnectorItem> getConnectorList()
	{
		return register;
	}
}