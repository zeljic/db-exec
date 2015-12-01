package com.zeljic.dbexec.cmps;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zeljic.dbexec.uil.Loader;
import com.zeljic.dbexec.utils.Holder;
import com.zeljic.dbexec.utils.R;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MessageBox
{
	private static MessageBox instance;
	private static Loader loader;

	private static final Logger logger = LogManager.getLogger();

	public enum Type
	{
		INFO, WARNING, ERROR
	}

	protected MessageBox()
	{
		Platform.runLater(() -> {
			loader = Loader.setInstance(Holder.MESSAGEBOX, R.get("/fxml/MessageBox.fxml"));
		});
	}

	public static MessageBox getInstance()
	{
		if (instance == null)
			instance = new MessageBox();

		return instance;
	}

	public void show(String title, String message, Type type, Window owner)
	{
		Platform.runLater(() -> {
			Stage stage = new Stage();

			loader.setStage(stage);

			stage.setScene(loader.getScene());
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(owner);
			stage.setResizable(false);
			stage.setTitle(title);

			stage.show();

			loader.lookup("#lblTitle", Label.class).setText(title);
			loader.lookup("#lblMessage", Label.class).setText(message);

			ImageView ivIcon = loader.lookup("#ivIcon", ImageView.class);

			String path;

			switch (type)
			{
			default:
			case INFO:
				path = "/gfx/icons/info.png";
				break;

			case ERROR:
				path = "/gfx/icons/error.png";
				break;

			case WARNING:
				path = "/gfx/icons/warning.png";
				break;
			}

			try (InputStream is = R.getAsStream(path))
			{
				ivIcon.setImage(new Image(is));
			} catch (IOException e)
			{
				logger.error(e);
			}
		});
	}
}
