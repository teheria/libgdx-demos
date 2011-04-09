package com.jmr.testGame.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class AccelerometerTest implements Screen {
	/* boolean check for if this screen is currently running */
	private boolean isRunning = false;
	
	private SpriteBatch mySpriteBatch;
	private final Texture myTexture;
	private Sprite sprite;
	
	private Vector2 position;
	public static final float oVELOCITY = 200;
	private float PLAYFIELD_MIN_X;
	private float PLAYFIELD_MAX_X;
	private float PLAYFIELD_MIN_Y;
	private float PLAYFIELD_MAX_Y;
	
	/* CONSTRUCTOR */
	public AccelerometerTest(Application app) {
		mySpriteBatch = new SpriteBatch();
		
		myTexture = new Texture(Gdx.files.internal("data/blah.png"));
		myTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		sprite = new Sprite(myTexture);
		sprite.setScale(0.8f, 0.8f);
		
		PLAYFIELD_MIN_X = sprite.getWidth() / 2;
		PLAYFIELD_MAX_X = Gdx.graphics.getWidth() - sprite.getWidth() / 2;
		
		PLAYFIELD_MIN_Y = sprite.getHeight() / 2;
		PLAYFIELD_MAX_Y = Gdx.graphics.getHeight() - sprite.getHeight() / 2;
		
		position = new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
	}

	@Override
	public void update(Application app) {	
		if (Gdx.input.isKeyPressed(Keys.KEYCODE_DPAD_DOWN)) {
			isRunning = true;
		}
		
		if (Gdx.input.getAccelerometerY() < 0) {
			moveLeft(app.getGraphics().getDeltaTime(), Math.abs(Gdx.input.getAccelerometerY()) / 10);
		}
		else {
			moveRight(app.getGraphics().getDeltaTime(), Math.abs(Gdx.input.getAccelerometerY()) / 10);
		}
		
		if (Gdx.input.getAccelerometerX() < 0) {
			moveDown(app.getGraphics().getDeltaTime(), Math.abs(Gdx.input.getAccelerometerX()) / 10);
		}
		else {
			moveUp(app.getGraphics().getDeltaTime(), Math.abs(Gdx.input.getAccelerometerX()) / 10);
		}
	}

	@Override
	public void render(Application app) {
		app.getGraphics().getGL10().glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		mySpriteBatch.begin();
		sprite.setPosition(position.x - sprite.getWidth()/2, position.y - sprite.getHeight()/2);
		sprite.draw(mySpriteBatch);
		mySpriteBatch.end();
	}

	@Override
	public boolean isDone() {
		return isRunning;
	}

	@Override
	public void dispose() {
		myTexture.dispose();
		mySpriteBatch.dispose();
	}
	
	/* METHODS FOR MOVING VIA ACCELEROMETER */
	//need to move to own class at some point
	public void moveLeft(float delta, float scale) {
		position.x -= delta * oVELOCITY * scale;
		if (position.x < PLAYFIELD_MIN_X) {
			position.x = PLAYFIELD_MIN_X;
		}
	}
	
	public void moveRight(float delta, float scale) {
		position.x += delta * oVELOCITY * scale;
		if (position.x > PLAYFIELD_MAX_X) {
			position.x = PLAYFIELD_MAX_X;
		}
	}
	
	public void moveUp(float delta, float scale) {
		position.y -= delta * oVELOCITY * scale;
		if (position.y < PLAYFIELD_MIN_Y) {
			position.y = PLAYFIELD_MIN_Y;
		}
	}
	
	public void moveDown(float delta, float scale) {
		position.y += delta * oVELOCITY * scale;
		if (position.y > PLAYFIELD_MAX_Y) {
			position.y = PLAYFIELD_MAX_Y;
		}
	}

}
