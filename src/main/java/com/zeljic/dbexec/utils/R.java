package com.zeljic.dbexec.utils;

import java.io.InputStream;
import java.net.URL;

import org.apache.log4j.Logger;

public class R
{
	public static InputStream getAsStream(String path)
	{
		InputStream tmp = R.class.getResourceAsStream(path);

		if (tmp == null)
			Logger.getLogger(R.class).warn("Resource doesn't exist: " + path);

		return tmp;
	}

	public static URL get(String path)
	{
		URL tmp = R.class.getResource(path);

		if (tmp == null)
			Logger.getLogger(R.class).warn("Resource doesn't exist: " + path);

		return tmp;
	}
}
