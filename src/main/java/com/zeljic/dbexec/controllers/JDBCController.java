package com.zeljic.dbexec.controllers;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import com.zeljic.dbexec.db.connectors.IConnector;
import com.zeljic.dbexec.db.connectors.JDBCConnector;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class JDBCController implements Initializable, IConnectorController
{
	private IConnector connector = new JDBCConnector();

	@FXML
	private TextField txtJdbcUrl, txtUsername, txtPassword;

	@Override
	public void initialize(URL url, ResourceBundle bundle)
	{

	}

	@Override
	public IConnector getConnector()
	{
		connector.setConnString(txtJdbcUrl.getText());

		Properties props = new Properties();
		
		if (txtUsername.getText() != null && !txtUsername.getText().trim().isEmpty()) {
			props.setProperty("user", txtUsername.getText());
		}
		
		if (txtPassword.getText() != null && !txtPassword.getText().trim().isEmpty()) {
			props.setProperty("password", txtPassword.getText());
		}

		connector.setProperties(props);

		return connector;
	}
}