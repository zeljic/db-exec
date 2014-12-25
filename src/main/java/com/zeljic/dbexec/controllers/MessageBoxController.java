package com.zeljic.dbexec.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import com.zeljic.dbexec.uil.Loader;
import com.zeljic.dbexec.utils.Holder;

public class MessageBoxController implements Initializable
{

	@FXML
	Label lblTitle, lblMessage;

	@FXML
	Button btnClose;

	@Override
	public void initialize(URL url, ResourceBundle bundle)
	{
	}

	@FXML
	public void onActionBtnClose()
	{
		Loader.getInstance(Holder.MESSAGEBOX).getStage().close();
	}
}