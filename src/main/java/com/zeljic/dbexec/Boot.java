package com.zeljic.dbexec;

import java.io.InputStream;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zeljic.dbexec.uil.Loader;
import com.zeljic.dbexec.utils.Holder;
import com.zeljic.dbexec.utils.R;

public class Boot extends Application
{
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void start(Stage stage)
	{
		Loader loader = Loader.setInstance(Holder.BOOT, stage, R.get("/fxml/Boot.fxml"));
		stage.setScene(loader.getScene());
		stage.setTitle("Database Exec");
		stage.setMinWidth(800);
		stage.setMinHeight(500);

		try (final InputStream icon = R.getAsStream("/gfx/icon.png"))
		{
			stage.getIcons().add(new Image(icon));
		} catch (Exception e)
		{
			logger.error(e);
		}

		stage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}