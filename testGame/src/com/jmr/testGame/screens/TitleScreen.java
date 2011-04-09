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
import com.badlogic.gdx.scenes.scene2d.actions.MoveBy;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleTo;
import com.badlogic.gdx.scenes.scene2d.actors.Image;

public class TitleScreen implements Screen {

	private Stage myStage;
	//logo texture
	private final Texture logoTexture;
	private final Texture start;
	private Image myImage1;
	private Image myImage2;
	
	private boolean isRunning = false;
	//private Music myMusic;
	
	public TitleScreen (Application app) {
		myStage = new Stage(480, 320, true);
		
		logoTexture = new Texture(Gdx.files.internal("data/title.png"));
		logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		start = new Texture(Gdx.files.internal("data/press1.png"));
		start.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		myImage1 = new Image("actor1", logoTexture);
		myImage2 = new Image("actor2", start);
		myImage2.touchable = true;
		
		myImage1.height = logoTexture.getHeight();
		myImage1.width = logoTexture.getWidth();
		myImage2.height = start.getHeight();
		myImage2.width = start.getWidth();
		
        myImage1.originX = myImage1.width/2;
        myImage1.originY = myImage1.height/2;
        myImage2.originX = myImage2.width/2;
        myImage2.originY = myImage2.height/2;
        
        myImage1.x = myStage.width()/2 - myImage1.width/2;
        myImage1.y = myStage.height()/2 - myImage1.height/2 + 50;
        myImage2.x = myStage.width()/2 - myImage2.width/2;
        myImage2.y = 0;
        
        myImage1.action(FadeIn.$(10));
        myImage1.action(ScaleTo.$(1.5f, 1.5f, 8));
        
        myImage2.action(Delay.$(FadeIn.$(8), 8));
        myImage2.action(Delay.$(MoveBy.$(0, 320 - 190, 3), 8));
        
        myStage.addActor(myImage1);
        myStage.addActor(myImage2);
		
		//myMusic = Gdx.audio.newMusic(Gdx.files.getFileHandle("data/intro.mp3", FileType.Internal));
		//myMusic.setLooping(true);
		//myMusic.play();
		
		Gdx.input.setInputProcessor(myStage);
	}
	
	@Override
	public void render(Application app) {
		app.getGraphics().getGL10().glClear(GL10.GL_COLOR_BUFFER_BIT);
		myStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		myStage.draw();
	}
	
	@Override
	public void update (Application app) {
		if (myStage.getLastTouchedChild() == myImage2) {
			isRunning = true;
		}
	}
	
	@Override
	public boolean isDone() {
		return isRunning;
	}
	
    @Override
    public void dispose () {
    	myStage.dispose();
        logoTexture.dispose();
        start.dispose();
        //myMusic.dispose();
}

}
