package com.zeljic.dbexec.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import com.zeljic.dbexec.cmps.MessageBox;
import com.zeljic.dbexec.cmps.MessageBox.Type;
import com.zeljic.dbexec.db.Row;
import com.zeljic.dbexec.db.connectors.IConnector;
import com.zeljic.dbexec.uil.Loader;
import com.zeljic.dbexec.utils.ConnectorType;
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
	private ComboBox<ConnectorType> cmbConnector;

	@FXML
	private VBox vbHolder;

	@Override
	public void initialize(URL url, ResourceBundle bundle)
	{
		wvMain.getEngine().load(R.get("/editor/index.html").toExternalForm());

		cmbConnector.getItems().addAll(new ConnectorType(ConnectorType.Type.SQLite3, "SQLite 3", "/fxml/ConnectorSQLite3.fxml"));
		cmbConnector.getItems().addAll(new ConnectorType(ConnectorType.Type.MySQL, "MySQL", "/fxml/ConnectorMySQL.fxml"));

		cmbConnector.setValue(cmbConnector.getItems().get(0));
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

		ConnectorType connectorType = cmbConnector.getValue();
		IConnector connector = connectorType.getControllerClass().getConnector();

		new Thread(() -> {

			if (!connector.executeQuery(query))
			{
				MessageBox.getInstance().show("SQL ERROR: Code " + connector.getErrorCode(), connector.getErrorMessage(), Type.ERROR, Loader.getInstance(Holder.BOOT).getStage());
				return;
			}

			List<String> columns = connector.getColumns();
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
				tvMain.setItems(connector.getRows());
			});
		}).start();
	}

	@FXML
	public void onActionCmbConnector()
	{
		ObservableList<Node> children = vbHolder.getChildren();

		if (children.size() > 1)
			children.remove(1);

		Node node = cmbConnector.getValue().getLoader().getNode();
		VBox.setVgrow(node, Priority.ALWAYS);

		children.add(1, node);
	}
}
