package com.zeljic.dbexec.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

public class BootController implements Initializable
{
	@FXML
	private WebView wvMain;

	@FXML
	private SplitPane spMain;

	@FXML
	private TableView<Object> tvMain;

	@FXML
	private TextField txtDBPath;

	@FXML
	private Button btnBrowse;

	@Override
	public void initialize(URL url, ResourceBundle bundle)
	{

	}
}
