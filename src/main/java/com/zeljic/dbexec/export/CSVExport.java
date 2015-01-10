package com.zeljic.dbexec.export;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import au.com.bytecode.opencsv.CSVWriter;

import com.zeljic.dbexec.db.Row;

public class CSVExport implements IExport
{
	@Override
	public ByteArrayOutputStream export(List<String[]> list)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try(CSVWriter writter = new CSVWriter(new OutputStreamWriter(baos), ','))
		{
			writter.writeAll(list);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return baos;
	}

	@Override
	public ByteArrayOutputStream export(ObservableList<TableColumn<Row, ?>> columns, ObservableList<Row> rows)
	{
		List<String[]> list = new ArrayList<String[]>();
		List<String> cList = new ArrayList<String>();

		columns.forEach(c -> {
			cList.add(c.getText());
		});

		list.add(cList.toArray(new String[columns.size()]));

		rows.parallelStream().forEachOrdered(r -> {

			List<String> l = new ArrayList<>();

			for(int i = 0, size = r.getSize(); i < size; i++)
				l.add(r.getData(i).getValue());

			list.add(l.toArray(new String[l.size()]));

		});

		return export(list);
	}
}