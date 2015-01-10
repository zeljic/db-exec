package com.zeljic.dbexec.export;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

import com.zeljic.dbexec.db.Row;


public class PDFExport implements IExport
{

	@Override
	public ByteArrayOutputStream export(List<String[]> list)
	{
		return null;
	}

	@Override
	public ByteArrayOutputStream export(ObservableList<TableColumn<Row, ?>> columns, ObservableList<Row> rows)
	{
		return null;
	}

}