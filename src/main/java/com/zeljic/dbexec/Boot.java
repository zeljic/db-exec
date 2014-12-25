package com.zeljic.dbexec;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import com.zeljic.dbexec.uil.Loader;
import com.zeljic.dbexec.utils.Holder;
import com.zeljic.dbexec.utils.R;

public class Boot extends Application
{

	@Override
	public void start(Stage stage) throws Exception
	{
		Loader loader = Loader.setInstance(Holder.BOOT, stage, R.get("/fxml/Boot.fxml"));
		stage.setScene(loader.getScene());
		stage.setTitle("Database Exec");
		stage.setMinWidth(800);
		stage.setMinHeight(500);

		stage.getIcons().add(new Image(R.getAsStream("/gfx/icon.png")));

		stage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}

}
