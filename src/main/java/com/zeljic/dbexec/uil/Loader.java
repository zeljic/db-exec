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
	private Stage stage;
	private Scene scene;
	private FXMLLoader loader;
	private Node mainNode;

	private static Map<String, Loader> _loaders = new LinkedHashMap<>();

	private static Logger logger = LogManager.getLogger();

	private Loader(Stage stage, URL path)
	{
		this.stage = stage;

		loader = new FXMLLoader(path);

		try
		{
			mainNode = loader.load();
			scene = new Scene((Parent) mainNode);
		} catch (IOException e)
		{
			logger.error(e);
		}
	}

	public static Loader setInstance(String name, Stage stage, URL path)
	{
		Loader tmp = new Loader(stage, path);
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
		return clazz.cast(loader.getController());
	}

	public void setStage(Stage stage)
	{
		this.stage = stage;
	}

	public Stage getStage()
	{
		return stage;
	}

	public Scene getScene()
	{
		return scene;
	}

	public Node getNode()
	{
		return mainNode;
	}

	public Object getRawController()
	{
		return loader.getController();
	}

	public <T> T lookup(String selector, Class<T> clazz)
	{
		Node node = getScene().lookup(selector);
		return node == null ? null : clazz.cast(node);
	}
}