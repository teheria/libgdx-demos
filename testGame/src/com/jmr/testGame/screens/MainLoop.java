package com.jmr.testGame.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.FadeIn;
import com.badlogic.gdx.scenes.scene2d.actions.FadeOut;
import com.badlogic.gdx.scenes.scene2d.actions.MoveBy;
import com.badlogic.gdx.scenes.scene2d.actions.Repeat;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleTo;
import com.badlogic.gdx.scenes.scene2d.actions.Sequence;
import com.badlogic.gdx.scenes.scene2d.actors.Image;

public class MainLoop implements Screen {
	
	private Stage stage;
	private final Texture logo1;
	private final BitmapFont myFont;
	private boolean isRunning = false;
	private Music myMusic;
	private Image image;
	
	public MainLoop (Application app) {
		stage = new Stage(480, 320, true);
		
		logo1 = new Texture(Gdx.files.internal("data/ZeroSuitSamus.png"));
		logo1.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		image = new Image("actor", logo1);
		
		image.width = image.height = 100;
        image.originX = 50;
        image.originY = 50;
        image.x = image.y = 100;
		
		image.action(Repeat.$(Sequence.$(FadeIn.$(2), ScaleTo.$(1.5f, 1.5f, 1), MoveBy.$(50, 0, 2), MoveBy.$(-50, 0, 2),
							ScaleTo.$(0.5f, 0.5f, 1), FadeOut.$(2)), 2));
		
		stage.addActor(image);
		
		myFont = new BitmapFont();
		myFont.setColor(Color.RED);
		myFont.setScale(1.2f, 1.2f);
		
		myMusic = Gdx.audio.newMusic(Gdx.files.getFileHandle("data/Samus.mp3", FileType.Internal));
		myMusic.setLooping(true);
		myMusic.play();
	}

	@Override
	public void update(Application app) {
		isRunning = app.getInput().isTouched();		
	}

	@Override
	public void render(Application app) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
	}

	@Override
	public boolean isDone() {
		return isRunning;
	}

	@Override
	public void dispose() {
		stage.dispose();
		logo1.dispose();
        myFont.dispose();
        myMusic.dispose();
	}

}
