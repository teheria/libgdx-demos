package com.jmr.testGame.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Delay;
import com.badlogic.gdx.scenes.scene2d.actions.FadeIn;
import com.badlogic.gdx.scenes.scene2d.actions.FadeOut;
import com.badlogic.gdx.scenes.scene2d.actions.MoveTo;
import com.badlogic.gdx.scenes.scene2d.actions.Parallel;
import com.badlogic.gdx.scenes.scene2d.actions.RotateTo;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleTo;
import com.badlogic.gdx.scenes.scene2d.actions.Sequence;
import com.badlogic.gdx.scenes.scene2d.actors.Image;

public class StageTest implements Screen {
	
	private Stage stage;
	private Texture t;
	private Music myMusic;
	private boolean isRunning;
	private Image img;
	
	public StageTest (Application app) {
		stage = new Stage(480, 320, true);
		myMusic = Gdx.audio.newMusic(Gdx.files.getFileHandle("data/item.mp3", FileType.Internal));
		myMusic.play();
		t = new Texture(Gdx.files.internal("data/metroidMorphBall.png"), false);
		t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        img = new Image("actor", t);
        img.touchable = true;
        img.width = img.height = 100;
        img.originX = 50;
        img.originY = 50;
        img.x = img.y = 100;
        
        img.action(Sequence.$(FadeOut.$(1), 
                FadeIn.$(1), 
                Delay.$(MoveTo.$(100, 100, 1), 2), 
                ScaleTo.$(0.5f, 0.5f, 1),
FadeOut.$(0.5f), 
Delay.$(Parallel.$( RotateTo.$(360, 1), 
                                       FadeIn.$(1), 
                                       ScaleTo.$(1, 1, 1)), 1)));

        stage.addActor(img);
        Gdx.input.setInputProcessor(stage);
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
		myMusic.dispose();
		t.dispose();
		stage.dispose();
	}

}
