package com.jmr.testGame.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class SmiulationTest implements Screen {
	
	private boolean isRunning = false;
	
	private SpriteBatch sp;
	private final Texture myTexture;
	
	//private Music myMusic;
	
	private Sprite sprite;
    
    private Vector2 position;
    private Vector2 direction;
	
	private float mRot = 10;
	
	public SmiulationTest (Application app) {
		sp = new SpriteBatch();
		
		position = new Vector2(50,50);
		direction = new Vector2(1,1);
		
		//myMusic = Gdx.audio.newMusic(Gdx.files.getFileHandle("data/final.mp3", FileType.Internal));
		//myMusic.setLooping(true);
		//myMusic.play();
		
		myTexture = new Texture(Gdx.files.internal("data/blah.png"));
		myTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		sprite = new Sprite(myTexture);
		sprite.setScale(0.8f, 0.8f);
		direction.x = position.x;
		direction.y = position.y;
	}
	
	public void lerp() {
		//LERP (linear interpolation) test
		
		float beta = Gdx.graphics.getDeltaTime();
		
		position.x = ((1 - beta) * position.x) + (beta * direction.x);
		position.y = ((1 - beta) * position.y) + (beta * direction.y);
		
		/* Used to fix position to set to dx/dy values */
		if (position.x > direction.x && position.x < (direction.x + 1.5f)) {
			position.x = direction.x;
		}
		else if (position.x < direction.x && position.x > (direction.x - 1.5f)) {
			position.x = direction.x;
		}
		
		if (position.y > direction.y && position.y < (direction.y + 1.5f)) {
			position.y = direction.y;
		}
		else if (position.y < direction.y && position.y > (direction.y - 1.5f)) {
			position.y = direction.y;
		}
		Gdx.app.log("hello", "x: " + position.x + " dx: " + direction.x + " y: " + position.y + " dy: " + direction.y);
	}

	@Override
	public void update(Application app) {
		
		if (Gdx.input.isKeyPressed(Keys.KEYCODE_DPAD_DOWN)) {
			isRunning = true;
		}
		
		if(Gdx.input.isTouched()) {
			direction.x = (float)Gdx.input.getX();
			direction.y = (float)Gdx.graphics.getHeight() - Gdx.input.getY();
		}
		
		if (position.x != direction.x || position.y != direction.y) {
			lerp();
		}
	}

	@Override
	public void render(Application app) {
		app.getGraphics().getGL10().glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		sp.begin();
		sprite.setPosition(position.x - sprite.getWidth()/2, position.y - sprite.getHeight()/2);
		sprite.draw(sp);
		sp.end();
	}

	@Override
	public boolean isDone() {
		return isRunning;
	}

	@Override
	public void dispose() {
		//myStage.dispose();
		myTexture.dispose();
		//myButton.dispose();
		sp.dispose();
		//myMusic.dispose();
	}

}
