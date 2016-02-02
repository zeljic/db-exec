package com.zeljic.dbexec.controllers;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import com.zeljic.dbexec.db.connectors.IConnector;
import com.zeljic.dbexec.db.connectors.MySQLConnector;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class MySQLController implements Initializable, IConnectorController
{
	private IConnector connector = new MySQLConnector();

	@FXML
	private TextField txtHost, txtPort, txtUsername, txtPassword, txtDatabase;

	@Override
	public void initialize(URL url, ResourceBundle bundle)
	{

	}

	@Override
	public IConnector getConnector()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("jdbc:mysql://").append(txtHost.getText());

		if (txtPort.getText().trim().length() > 0)
			sb.append(":").append(txtPort.getText());

		sb.append("/").append(txtDatabase.getText());

		connector.setConnString(sb.toString());

		Properties props = new Properties();
		props.setProperty("user", txtUsername.getText());
		props.setProperty("password", txtPassword.getText());

		connector.setProperties(props);

		return connector;
	}
}