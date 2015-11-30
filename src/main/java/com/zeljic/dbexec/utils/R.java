package com.zeljic.dbexec.utils;

import java.io.InputStream;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class R
{
	private static Logger logger = LogManager.getLogger();

	public static InputStream getAsStream(String path)
	{
		InputStream tmp = R.class.getResourceAsStream(path);

		if (tmp == null)
			logger.warn("Resource doesn't exist: " + path);

		return tmp;
	}

	public static URL get(String path)
	{
		URL tmp = R.class.getResource(path);

		if (tmp == null)
			logger.warn("Resource doesn't exist: " + path);

		return tmp;
	}
}
