package com.zeljic.dbexec.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import com.zeljic.dbexec.uil.Loader;
import com.zeljic.dbexec.utils.Holder;
import com.zeljic.dbexec.utils.R;

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

	State state;

	@Override
	public void initialize(URL url, ResourceBundle bundle)
	{
		wvMain.getEngine().load(R.get("/editor/index.html").toExternalForm());
	}

	@FXML
	public void onActionBtnBrowse()
	{
		FileChooser fc = new FileChooser();
		File f = fc.showOpenDialog(Loader.getInstance(Holder.BOOT).getStage());

		if (f != null)
			txtDBPath.setText(f.getPath().replace("\\", "/"));
	}

	@FXML
	public void onActionBtnClear()
	{
		wvMain.getEngine().executeScript("editor.setValue('');");
	}

	@FXML
	public void onActionBtnExecute()
	{
		String query = (String) wvMain.getEngine().executeScript("editor.getValue();");
	}
}
