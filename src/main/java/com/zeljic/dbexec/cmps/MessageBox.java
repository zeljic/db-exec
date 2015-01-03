package com.zeljic.dbexec.cmps;

import java.io.InputStream;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import com.zeljic.dbexec.uil.Loader;
import com.zeljic.dbexec.utils.Holder;
import com.zeljic.dbexec.utils.R;

public class MessageBox
{
	private static MessageBox instance;
	private static Loader loader;

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
			InputStream is;

			switch (type)
			{
			default:
			case INFO:
				is = R.getAsStream("/gfx/icons/info.png");
				break;

			case ERROR:
				is = R.getAsStream("/gfx/icons/error.png");
				break;

			case WARNING:
				is = R.getAsStream("/gfx/icons/warning.png");
				break;
			}

			ivIcon.setImage(new Image(is));

			// stage.show();
		});
	}
}
