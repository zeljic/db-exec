package com.zeljic.dbexec.controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import com.zeljic.dbexec.db.Row;
import com.zeljic.dbexec.db.SQLite3Worker;
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
	private TableView<Row> tvMain;

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
		tvMain.getColumns().clear();

		String query = (String) wvMain.getEngine().executeScript("editor.getValue();");
		final SQLite3Worker worker = new SQLite3Worker();
		worker.setDBPath(txtDBPath.getText());

		new Thread(() -> {

			worker.executeQuery(query);
			List<String> columns = worker.getColumns();
			List<TableColumn<Row, String>> storage = new ArrayList<TableColumn<Row, String>>();

			for (int i = 0, size = columns.size(); i < size; i++)
			{
				final int fi = i;
				TableColumn<Row, String> column = new TableColumn<Row, String>(columns.get(i));

				column.setCellValueFactory(data -> data.getValue().getData(fi));
				storage.add(column);
			}

			Platform.runLater(() -> {
				tvMain.getItems().clear();
				tvMain.getColumns().addAll(storage);
				tvMain.setItems(worker.getRows());
			});
		}).start();

	}
}
