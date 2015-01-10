package com.zeljic.dbexec.export;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ExportItem
{
	public enum Type
	{
		CSV, PDF
	}

	private String display = "";
	private Type type;
	private IExport exporter;
	private static ObservableList<ExportItem> register = FXCollections.observableArrayList();

	static
	{
		register.add(new ExportItem(Type.CSV, "CSV", new CSVExport()));
		register.add(new ExportItem(Type.PDF, "PDF", new PDFExport()));
	}

	public ExportItem(Type type, String display, IExport exporter)
	{
		this.type = type;
		this.display = display;
		this.exporter = exporter;
	}

	public String getDisplay()
	{
		return display;
	}

	public void setDisplay(String display)
	{
		this.display = display;
	}

	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}

	public IExport getExporter()
	{
		return exporter;
	}

	@Override
	public String toString()
	{
		return display;
	}

	public static ObservableList<ExportItem> getExporterList()
	{
		return register;
	}
}
