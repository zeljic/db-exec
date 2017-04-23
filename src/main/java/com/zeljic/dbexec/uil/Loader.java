package com.zeljic.dbexec.uil;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Loader
{
	private URL _path;
	private Stage _stage;
	private Scene _scene;
	private FXMLLoader _loader;
	private Node _mainNode;

	private static Map<String, Loader> _loaders = new LinkedHashMap<>();

	private static Logger logger = LogManager.getLogger();

	protected Loader(String name, Stage stage, URL path)
	{
		_path = path;
		_stage = stage;

		_loader = new FXMLLoader(_path);

		try
		{
			_mainNode = _loader.load();
			_scene = new Scene((Parent) _mainNode);
		} catch (IOException e)
		{
			logger.error(e);
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

	public Node getNode()
	{
		return _mainNode;
	}

	public Object getRawController()
	{
		return _loader.getController();
	}

	public <T> T lookup(String selector, Class<T> clazz)
	{
		Node node = getScene().lookup(selector);
		return node == null ? null : clazz.cast(node);
	}
}