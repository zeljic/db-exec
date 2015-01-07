package com.zeljic.dbexec.db.connectors;

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
}