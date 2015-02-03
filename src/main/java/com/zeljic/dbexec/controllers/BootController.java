package com.zeljic.dbexec.controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import com.zeljic.dbexec.cmps.MessageBox;
import com.zeljic.dbexec.cmps.MessageBox.Type;
import com.zeljic.dbexec.db.Row;
import com.zeljic.dbexec.db.connectors.ConnectorItem;
import com.zeljic.dbexec.db.connectors.IConnector;
import com.zeljic.dbexec.export.ExportItem;
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
	private ComboBox<ConnectorItem> cmbConnector;

	@FXML
	private ComboBox<ExportItem> cmbExport;

	@FXML
	private VBox vbHolder;

	@FXML
	private Button btnExecute, btnCancel;

	@FXML
	private Label lblStatus, lblColumns, lblRows, lblExecutionTime;

	private SimpleBooleanProperty isRunning = new SimpleBooleanProperty(false);
	private SimpleLongProperty executionTime = new SimpleLongProperty(0);

	@Override
	public void initialize(URL url, ResourceBundle bundle)
	{
		wvMain.getEngine().load(R.get("/editor/index.html").toExternalForm());

		// initialize cmbConnector
		cmbConnector.itemsProperty().set(ConnectorItem.getConnectorList());

		cmbConnector.valueProperty().addListener((value, oldv, newv) -> {
			ObservableList<Node> children = vbHolder.getChildren();

			if (children.size() > 1)
				children.remove(1);

			Node node = cmbConnector.getValue().getLoader().getNode();
			VBox.setVgrow(node, Priority.ALWAYS);

			children.add(1, node);
		});

		cmbConnector.setValue(cmbConnector.getItems().get(0));

		// initialize cmbExport
		cmbExport.itemsProperty().set(ExportItem.getExporterList());
		cmbExport.setValue(cmbExport.getItems().get(0));

		isRunning.addListener((value, oldv, newv) -> {
			Platform.runLater(() -> {
				btnCancel.disableProperty().set(!newv);
				btnExecute.disableProperty().set(newv);

				lblStatus.setText(newv ? "Working" : "Ready");
			});
		});

		// add listeners for change columns and rows of table
		tvMain.getColumns().addListener(new ListChangeListener<TableColumn<?, ?>>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends TableColumn<?, ?>> c)
			{
				lblColumns.setText(String.valueOf(tvMain.getColumns().size()));
			}
		});

		tvMain.getItems().addListener(new ListChangeListener<Row>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Row> c)
			{
				lblRows.setText(String.valueOf(tvMain.getItems().size()));
			}
		});

		// add listener for execution time
		executionTime.addListener((value, oldv, newv) -> {
			Platform.runLater(() -> {
				lblExecutionTime.setText(String.valueOf(newv));
			});
		});
	}

	@FXML
	public void onActionBtnClear()
	{
		wvMain.getEngine().executeScript("editor.setValue('');");
	}

	@FXML
	public void onActionBtnExecute()
	{
		final long time = System.currentTimeMillis();

		tvMain.getColumns().clear();
		tvMain.getItems().clear();

		ConnectorItem connectorType = cmbConnector.getValue();
		IConnector connector = connectorType.getControllerClass().getConnector();
		String query = getQuery();

		isRunning.set(true);

		new Thread(() -> {

			if (!connector.executeQuery(query))
			{
				MessageBox.getInstance().show("SQL ERROR: Code " + connector.getErrorCode(), connector.getErrorMessage(), Type.ERROR, Loader.getInstance(Holder.BOOT).getStage());
				isRunning.set(false);
				return;
			}

			List<String> columns = connector.getColumns();
			List<TableColumn<Row, String>> tableColumns = new ArrayList<TableColumn<Row, String>>();

			for (int i = 0, size = columns.size(); i < size; i++)
			{
				final int fi = i;
				TableColumn<Row, String> column = new TableColumn<Row, String>(columns.get(i));

				column.setCellValueFactory(data -> data.getValue().getData(fi));
				tableColumns.add(column);
			}

			if (isRunning.get())
				Platform.runLater(() -> {
					tvMain.getColumns().addAll(tableColumns);
					tvMain.getItems().addAll(connector.getRows());
					isRunning.set(false);
					executionTime.set((System.currentTimeMillis() - time));
				});
		}).start();
	}

	@FXML
	public void onActionBtnCancel()
	{
		isRunning.set(false);
	}

	@FXML
	public void onActionBtnExport()
	{
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("CSV File", "*.csv"));
		File f = fc.showSaveDialog(Loader.getInstance(Holder.BOOT).getStage());

		if (f != null)
			new Thread(() -> {
				ByteArrayOutputStream baos = cmbExport.getValue().getExporter().export(tvMain.getColumns(), tvMain.getItems());

				try (FileOutputStream fos = new FileOutputStream(f))
				{
					baos.writeTo(fos);
				} catch (Exception e)
				{

				}

			}).start();
	}

	@FXML
	public void onActionBtnScriptOpen()
	{
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("SQL", "*.sql"), new ExtensionFilter("All Files", "*.*"));

		File f = fc.showOpenDialog(Loader.getInstance(Holder.BOOT).getStage());

		if (f != null)
			new Thread(() -> {
				String fileContent = "";

				try
				{
					fileContent = FileUtils.readFileToString(f, Charset.forName("UTF-8"));
				} catch (IOException e)
				{
					e.printStackTrace();
				}

				setQuery(fileContent);
			}).start();
	}

	@FXML
	public void onActionBtnScriptSave()
	{
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("SQL", "*.sql"), new ExtensionFilter("All Files", "*.*"));

		File f = fc.showSaveDialog(Loader.getInstance(Holder.BOOT).getStage());

		if (f != null)
		{
			final String query = getQuery().trim();

			new Thread(() -> {
				try
				{
					FileUtils.writeStringToFile(f, query, Charset.forName("UTF-8"));
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}).start();
		}
	}

	private String getQuery()
	{
		return (String) wvMain.getEngine().executeScript("editor.getValue();");
	}

	private void setQuery(String query)
	{
		Platform.runLater(() -> {
			wvMain.getEngine().executeScript("editor.setValue('" + StringEscapeUtils.escapeEcmaScript(query) + "');");
		});
	}
}