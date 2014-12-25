package com.zeljic.dbexec.uil;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.apache.log4j.Logger;

public class Loader
{

	private URL _path;
	private Stage _stage;
	private Scene _scene;
	private FXMLLoader _loader;

	private static HashMap<String, Loader> _loaders = new HashMap<String, Loader>();

	protected Loader(String name, Stage stage, URL path)
	{
		_path = path;
		_stage = stage;

		_loader = new FXMLLoader(_path);

		try
		{
			_scene = new Scene((Parent) _loader.load());
		} catch (IOException e)
		{
			Logger.getLogger(getClass()).warn(e);
		}
	}

	public static Loader setInstance(String name, Stage stage, URL path)
	{
		Loader tmp = new Loader(name, stage, path);
		_loaders.put(name, tmp);

		return tmp;
	}

	public static Loader setInstance(String name, URL path)
	{
		return setInstance(name, null, path);
	}

	public static Loader getInstance(String name)
	{
		return _loaders.get(name);
	}

	public <T> T getController(Class<T> clazz)
	{
		return clazz.cast(_loader.getController());
	}

	public void setStage(Stage stage)
	{
		_stage = stage;
	}

	public Stage getStage()
	{
		return _stage;
	}

	public Scene getScene()
	{
		return _scene;
	}

	public <T> T lookup(String selector, Class<T> clazz)
	{
		Node node = getScene().lookup(selector);
		return node == null ? null : clazz.cast(node);
	}
}