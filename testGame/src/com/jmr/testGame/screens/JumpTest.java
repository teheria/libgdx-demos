package com.jmr.testGame.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class JumpTest implements Screen{
	OrthographicCamera myCam;
	
	private boolean isRunning = false;
	
	private SpriteBatch sp;
	private final Texture myTexture;
	private final Texture bg;
	private Sprite sprite;
	
	private Vector2 position;
	private float gravity = 0.5f;
	private float velocityY;
	private boolean isJumping = false;
	private float ground;
	
	public static final float oVELOCITY = 250;
	private float PLAYFIELD_MIN_X;
	private float PLAYFIELD_MAX_X;
	
	/* CONSTRUCTOR */
	public JumpTest(Application app) {
		myCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		myCam.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
		
		sp = new SpriteBatch();
		myTexture = new Texture(Gdx.files.internal("data/blah.png"));
		myTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bg = new Texture(Gdx.files.internal("data/bg.png"));
		bg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprite = new Sprite(myTexture);
		sprite.setScale(0.8f, 0.8f);
		
		position = new Vector2(Gdx.graphics.getWidth()/2 , sprite.getHeight()/2);
		ground = sprite.getHeight()/2;
		
		PLAYFIELD_MIN_X = sprite.getWidth() / 2;
		PLAYFIELD_MAX_X = Gdx.graphics.getWidth() - sprite.getWidth() / 2;
	}

	@Override
	public void update(Application app) {
		if (Gdx.input.isKeyPressed(Keys.KEYCODE_DPAD_DOWN)) {
			isRunning = true;
		}
		
		/* if not in a jumping state and if the screen was touched, lets jump! */
		if (!isJumping && Gdx.input.justTouched()) {
			isJumping = true;
			velocityY = Gdx.graphics.getHeight() - 400;
		}
		
		
		if(isJumping) {
			if (position.y + 0.001f > ground) {
				position.y += (float) (velocityY * Gdx.graphics.getDeltaTime() - (0.5f * gravity *
						(Math.pow(Gdx.graphics.getDeltaTime(), 2.0f))));
				velocityY -= gravity;
				Gdx.app.log("Y: ", "" + position.y + " GROUND: " + ground + " VELOCITY: " + velocityY);
			}
			else {
				isJumping = false;
				position.y = ground;
				Gdx.app.log("Velocity: ", "" + velocityY + " isJumping: " + isJumping + " Y: " + position.y);
			}
		}
		
		/* give the object a upward velocity, then accelerate downward, bringing it back to the ground */
		/*if (isJumping) {
			if (position.y + velocityY > ground) {
				position.y += velocityY * (Gdx.graphics.getDeltaTime());
				velocityY -= gravity;
				Gdx.app.log("Y: ", "" + position.y + " GROUND: " + ground + " VELOCITY: " + velocityY);
			}
			else {
				isJumping = false;
				position.y = ground;
				Gdx.app.log("Velocity: ", "" + velocityY + " isJumping: " + isJumping + " Y: " + position.y);
			}
		}*/
		
		/* LEFT & RIGHT ACCELEROMETER MOVEMENT */
		if (Gdx.input.getAccelerometerY() < 0) {
			moveLeft(app.getGraphics().getDeltaTime(), Math.abs(Gdx.input.getAccelerometerY()) / 10);
		}
		else {
			moveRight(app.getGraphics().getDeltaTime(), Math.abs(Gdx.input.getAccelerometerY()) / 10);
		}
		if (Gdx.input.isKeyPressed(Keys.KEYCODE_DPAD_LEFT)) {
			moveLeft(Gdx.graphics.getDeltaTime(), 0.5f);
		}
		else if (Gdx.input.isKeyPressed(Keys.KEYCODE_DPAD_RIGHT)) {
			moveRight(Gdx.graphics.getDeltaTime(), 0.5f);
		}
	}

	@Override
	public void render(Application app) {		
		app.getGraphics().getGL10().glClear(GL10.GL_COLOR_BUFFER_BIT);		
		sp.begin();
		sp.disableBlending();
		myCam.update();
		myCam.apply(Gdx.gl10);
		sp.setColor(Color.WHITE);
		sp.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, 1024, 512, false, false);
		sp.enableBlending();
		sprite.setPosition(position.x - sprite.getWidth()/2, position.y - (sprite.getHeight()/2));
		sprite.draw(sp);
		sp.end();
	}

	@Override
	public boolean isDone() {
		return isRunning;
	}

	@Override
	public void dispose() {
		sp.dispose();
		bg.dispose();
		myTexture.dispose();
	}
	
	/* METHODS FOR MOVING VIA ACCELEROMETER */
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

}
