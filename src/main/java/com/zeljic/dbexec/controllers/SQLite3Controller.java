package com.zeljic.dbexec.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import com.zeljic.dbexec.db.connectors.IConnector;
import com.zeljic.dbexec.db.connectors.SQLite3Connector;
import com.zeljic.dbexec.uil.Loader;
import com.zeljic.dbexec.utils.Holder;

public class SQLite3Controller implements Initializable, IConnectorController
{
	@FXML
	public TextField txtDBPath;

	@FXML
	public Button btnBrowse;

	@Override
	public void initialize(URL url, ResourceBundle bundle)
	{

	}

	@FXML
	public void onActionBtnBrowse()
	{
		FileChooser fc = new FileChooser();
		File f = fc.showOpenDialog(Loader.getInstance(Holder.BOOT).getStage());

		if (f != null)
			txtDBPath.setText(f.getPath());
	}

	@Override
	public IConnector getConnector()
	{
		IConnector connector = new SQLite3Connector();
		connector.setConnString("jdbc:sqlite:" + txtDBPath.getText());

		return connector;
	}
}