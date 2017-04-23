package com.zeljic.dbexec.export;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

import com.zeljic.dbexec.db.Row;

public interface IExport
{
	ByteArrayOutputStream export(List<String[]> list);
	ByteArrayOutputStream export(ObservableList<TableColumn<Row, ?>> columns, ObservableList<Row> rows);
}